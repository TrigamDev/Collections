package dev.trigam.collections.screen;

import dev.trigam.collections.Collections;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ModEnchantmentScreen extends HandledScreen<ModEnchantmentScreenHandler> {
    // Elements
    private static final Identifier TEXTURE = Collections.id("textures/gui/container/enchanting_table.png");
    private static final Identifier SCROLLER_TEXTURE = Identifier.ofVanilla("container/stonecutter/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = Collections.id("container/enchanting_table/scroller_disabled");

    // Tooltips
    private static final Text GEAR_SLOT_TOOLTIP = Text.translatable(
        Util.createTranslationKey("container", Identifier.ofVanilla("enchanting_table.base_slot_description"))
    ).formatted(Formatting.WHITE);
    private static final Text CATALYST_SLOT_TOOLTIP = Text.translatable(
        Util.createTranslationKey("container", Identifier.ofVanilla("enchanting_table.catalyst_slot_description"))
    ).formatted(Formatting.WHITE);

    // Book
    private static final Identifier BOOK_TEXTURE = Identifier.ofVanilla("textures/entity/enchanting_table_book.png");
    private BookModel BOOK_MODEL;
    public int ticks;
    public float nextPageAngle;
    public float pageAngle;
    public float approximatePageAngle;
    public float pageRotationSpeed;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;

    private final Random random = Random.create();
    private ItemStack stack = ItemStack.EMPTY;

    //
    //  Initialization
    //
    public ModEnchantmentScreen(ModEnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 207;
        this.backgroundHeight = 174;
        this.playerInventoryTitleX = 24;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        if (this.client != null)
            this.BOOK_MODEL = new BookModel(this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BOOK));
    }

    //
    //  Rendering
    //
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
        this.renderSlotTooltip(context, mouseX, mouseY);
        //boolean bl = this.client.player.getAbilities().creativeMode;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int[] origin = getOrigin();
        context.drawTexture(TEXTURE, origin[0], origin[1], 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.drawBook(context, origin[0], origin[1], delta);
        this.drawEnchantmentsText(context, origin[0], origin[1]);
        this.drawScroller(context, origin[0], origin[1], 0);
    }

    private void drawEnchantmentsText(DrawContext context, int originX, int originY) {
        List<Enchantment> enchantments = this.handler.getAvailableEnchantments();
        for (int i = 0; i < enchantments.size(); i++) {
            Enchantment enchantment = enchantments.get(i);
            context.drawTextWithShadow(this.textRenderer, enchantment.description(), originX + 72,originY + 15 + (i * 9),8453920);
        }
    }

    private void drawBook(DrawContext context, int originX, int originY, float delta) {
        float pageTurnSpeed = MathHelper.lerp(delta, this.pageTurningSpeed, this.nextPageTurningSpeed);
        float pageTurnAngle = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle);

        // Setup?
        DiffuseLighting.method_34742();
        context.getMatrices().push();

        // Translate to GUI position
        context.getMatrices().translate((float) originX + 37, (float) originY + 31, 100.0F);
        // Scale
        float bookScale = 40.0F;
        context.getMatrices().scale(-bookScale, bookScale, bookScale);
        // Rotate up a bit
        context.getMatrices().multiply(RotationAxis.POSITIVE_X.rotationDegrees(25.0F));

        context.getMatrices().translate(
            (1.0F - pageTurnSpeed) * 0.2F,
            (1.0F - pageTurnSpeed) * 0.1F,
            (1.0F - pageTurnSpeed) * 0.25F
        );
        float i = -(1.0F - pageTurnSpeed) * 90.0F - 90.0F;
        context.getMatrices().multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i));
        context.getMatrices().multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
        float j = MathHelper.clamp(MathHelper.fractionalPart(pageTurnAngle + 0.25F) * 1.6F - 0.3F, 0.0F, 1.0F);
        float k = MathHelper.clamp(MathHelper.fractionalPart(pageTurnAngle + 0.75F) * 1.6F - 0.3F, 0.0F, 1.0F);
        this.BOOK_MODEL.setPageAngles(0.0F, j, k, pageTurnSpeed);
        VertexConsumer vertexConsumer = context.getVertexConsumers().getBuffer(this.BOOK_MODEL.getLayer(BOOK_TEXTURE));
        this.BOOK_MODEL.render(context.getMatrices(), vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV);
        context.draw();
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }

    private void drawScroller (DrawContext context, int originX, int originY, int yOffset) {
        context.drawGuiTexture(SCROLLER_DISABLED_TEXTURE, originX + 186, originY + 14 + yOffset, 12, 15);
    }

    private void renderSlotTooltip(DrawContext context, int mouseX, int mouseY) {
        Optional<Text> tooltipText = Optional.empty();
        // Get tooltip of focused slot
        if (this.focusedSlot != null) {
            if (this.focusedSlot.hasStack()) return;
            tooltipText = switch (this.focusedSlot.id) {
                case 0 -> Optional.of(GEAR_SLOT_TOOLTIP);
                case 1 -> Optional.of(CATALYST_SLOT_TOOLTIP);
                default -> tooltipText;
            };
        }
        // Render
        tooltipText.ifPresent(text -> context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(text, 115), mouseX, mouseY));
    }

    private int[] getOrigin() {
        int originX = (this.width - this.backgroundWidth) / 2;
        int originY = (this.height - this.backgroundHeight) / 2;
        return new int[]{originX, originY};
    }

    //
    //  Ticking
    //
    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.tickBook();
    }

    public void tickBook() {
        ItemStack gearItem = this.handler.getSlot(0).getStack();

        // If there's a gear item, flip through pages
        if (!ItemStack.areEqual(gearItem, this.stack)) {
            this.stack = gearItem;
            do {
                int rand = this.random.nextInt(4) - this.random.nextInt(4);
                this.approximatePageAngle += (float) rand;
            } while (
                this.nextPageAngle <= this.approximatePageAngle + 1.0F
                && this.nextPageAngle >= this.approximatePageAngle - 1.0F
            );
        }

        ++this.ticks;
        this.pageAngle = this.nextPageAngle;
        this.pageTurningSpeed = this.nextPageTurningSpeed;

        // Should open
        boolean shouldOpen = !gearItem.isEmpty();

        // Either open or close the book
        if (shouldOpen) this.nextPageTurningSpeed += 0.2F;
        else this.nextPageTurningSpeed -= 0.2F;

        this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
        float angleDiff = MathHelper.clamp((this.approximatePageAngle - this.nextPageAngle) * 0.4F, -0.2F, 0.2F);
        this.pageRotationSpeed += (angleDiff - this.pageRotationSpeed) * 0.9F;
        this.nextPageAngle += this.pageRotationSpeed;
    }
}
