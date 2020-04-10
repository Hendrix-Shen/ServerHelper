package io.gitee.mc_shd1.mixins;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(method = "onPlayerConnect", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "net/minecraft/server/network/ServerPlayerEntity.method_14235 ()V"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info){
        //Message.LOG.info(player.getName().getString());// 获取玩家名
    }
}