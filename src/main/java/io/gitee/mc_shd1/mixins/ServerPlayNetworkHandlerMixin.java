package io.gitee.mc_shd1.mixins;

import io.gitee.mc_shd1.Core;
import io.gitee.mc_shd1.events.Player;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onPlayerConnect(MinecraftServer server, ClientConnection client, ServerPlayerEntity player, CallbackInfo ci) {
        Player.onPlayerLoggedIn(player);
    }

    @Inject(method = "onDisconnected", at = @At("HEAD"))
    private void onPlayerDisconnect(Text reason, CallbackInfo ci) {
        Player.onPlayerLoggedOut(this.player);
    }

    @Shadow protected abstract void executeCommand(String string);

    @Inject(method = "onChatMessage", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Z)V"))
    private void onChat(ChatMessageC2SPacket packet, CallbackInfo info){
        Player.onChatMessage(server, player, packet);
    }
}