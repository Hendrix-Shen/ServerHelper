package io.gitee.mc_shd1.mixins;

import io.gitee.mc_shd1.Core;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onGameMessage", at = @At(value = "INVOKE",shift = At.Shift.AFTER, target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Z)V"))
    private void onChat(ChatMessageC2SPacket packet, CallbackInfo info){
        if(Core.getConfig().command_here_valid_list.contains(packet.getChatMessage())){
            player.getServer().getCommandManager().execute(player.getCommandSource(), "/here");
        }
    }
}