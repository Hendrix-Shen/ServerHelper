package io.gitee.mc_shd1.mixins;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MinecraftDedicatedServer.class)
public abstract class MinecraftDedicatedServerMixin {
    @Inject(method = "setupServer", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "net/minecraft/server/dedicated/MinecraftDedicatedServer.getServerIp ()Ljava/lang/String;", ordinal = 2))

    private void BeforeBindPort(CallbackInfoReturnable<Boolean> cir) {
        //Message.LOG.info("Inject point test!");
    }
}
