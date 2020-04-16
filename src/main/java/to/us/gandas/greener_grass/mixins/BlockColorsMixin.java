package to.us.gandas.greener_grass.mixins;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import net.minecraft.world.BlockRenderView;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockColorProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockColors.class)
public class BlockColorsMixin {
    private static final boolean ALPHA_GRASS = true;
    private static final Set<Block> AFFECTED_BLOCKS = new HashSet<>(Arrays.asList(
        Blocks.LARGE_FERN,
        Blocks.TALL_GRASS,
        Blocks.GRASS_BLOCK,
        Blocks.FERN,
        Blocks.GRASS,
        Blocks.POTTED_FERN,
        Blocks.POTTED_BAMBOO,
        Blocks.SUGAR_CANE,
        Blocks.BAMBOO_SAPLING,
        Blocks.BAMBOO,
	    Blocks.OAK_LEAVES,
	    Blocks.BIRCH_LEAVES,
	    Blocks.JUNGLE_LEAVES,
	    Blocks.ACACIA_LEAVES,
	    Blocks.DARK_OAK_LEAVES,
        Blocks.VINE
    ));

    @Inject(
        method = "Lnet/minecraft/client/color/block/BlockColors;getColor(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/util/math/BlockPos;I)I",
        at = @At("RETURN"),
        cancellable = true
    )
    public void onGetColor(BlockState state, BlockRenderView world, BlockPos pos, int tint, CallbackInfoReturnable<Integer> cir) {
        if (!AFFECTED_BLOCKS.contains(state.getBlock())) {
            return;
        }
        int originalColor = cir.getReturnValue();
        cir.setReturnValue(getGreenerColor(originalColor));
    }

    // stolen from quark
    private int getGreenerColor(int originalColor) {
        int r = originalColor >> 16 & 0xFF;
        int g = originalColor >> 8 & 0xFF;
        int b = originalColor & 0xFF;

        final int shiftRed = ALPHA_GRASS ? 30 : -30;
        final int shiftGreen = ALPHA_GRASS ? 120 : 30;
        final int shiftBlue = ALPHA_GRASS ? 30 : -30;

        r = (Math.max(0, Math.min(0xFF, r + shiftRed)) << 16);
        g = Math.max(0, Math.min(0xFF, g + shiftGreen) << 8);
        b = Math.max(0, Math.min(0xFF, b + shiftBlue));

        return r | g | b;
    }
}
