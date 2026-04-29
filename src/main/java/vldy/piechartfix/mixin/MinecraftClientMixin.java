package vldy.piechartfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vldy.piechartfix.PieFixConfig;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Redirect(
        method = "drawProfilerResults",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getFramebufferWidth()I", ordinal = 0)
    )
    private int orthoWidth(Window window) {
        return (int)(window.getFramebufferWidth() / PieFixConfig.getScale());
    }

    @Redirect(
        method = "drawProfilerResults",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getFramebufferHeight()I", ordinal = 0)
    )
    private int orthoHeight(Window window) {
        return (int)(window.getFramebufferHeight() / PieFixConfig.getScale());
    }

    @Redirect(
        method = "drawProfilerResults",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getFramebufferWidth()I", ordinal = 1)
    )
    private int positionWidth(Window window) {
        return (int)(window.getFramebufferWidth() / PieFixConfig.getScale()) - PieFixConfig.getOffsetX();
    }

    @Redirect(
        method = "drawProfilerResults",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getFramebufferHeight()I", ordinal = 1)
    )
    private int positionHeight(Window window) {
        return (int)(window.getFramebufferHeight() / PieFixConfig.getScale()) - PieFixConfig.getOffsetY();
    }
}