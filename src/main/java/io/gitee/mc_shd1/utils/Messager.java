package io.gitee.mc_shd1.utils;

import com.google.gson.internal.$Gson$Types;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.network.packet.TitleS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Messager {
    public static final Logger LOG = LogManager.getLogger();
    public static void sendMessage(ServerCommandSource Source, String message)
    {
        if(Source.getEntity() == null) {
            Source.getMinecraftServer().sendMessage(Text.Serializer.fromJson(message));
        } else {
            Source.getEntity().sendMessage(Text.Serializer.fromJson(message));
        }
    }
}
