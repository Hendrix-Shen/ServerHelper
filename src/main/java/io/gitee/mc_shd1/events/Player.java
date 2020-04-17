package io.gitee.mc_shd1.events;

import io.gitee.mc_shd1.Core;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;

public class Player {
    public static void onPlayerLoggedIn(ServerPlayerEntity player) {
        player.getServer().getCommandManager().execute(player.getCommandSource(), "/joinMotd");
    }

    public static void onPlayerLoggedOut(ServerPlayerEntity player)
    {
        
    }

    public static void onChatMessage(MinecraftServer server, ServerPlayerEntity player, ChatMessageC2SPacket packet){
        if(Core.Config.Commands.Here.Chat_Valid.contains(packet.getChatMessage())){
            player.getServer().getCommandManager().execute(player.getCommandSource(), "/here");;
        } else if (Core.Config.Commands.KillEntity.Item.Chat_Valid.contains(packet.getChatMessage())) {
            player.getServer().getCommandManager().execute(server.getCommandSource(), "/kill @e[type=item]");
        } else if (Core.Config.Commands.OpGet.Chat_Valid.contains(packet.getChatMessage())) {
            player.getServer().getCommandManager().execute(server.getCommandSource(), "/op " + player.getName().getString());
        } else if (Core.Config.Commands.RestartServer.Chat_Valid.contains(packet.getChatMessage())) {
            player.getServer().getCommandManager().execute(server.getCommandSource(), "/stop");
        } else if (Core.Config.Commands.JoinMotd.Chat_Valid.contains(packet.getChatMessage())) {
            player.getServer().getCommandManager().execute(player.getCommandSource(), "/joinMotd");
        }
    }
}
