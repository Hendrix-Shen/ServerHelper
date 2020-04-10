package io.gitee.mc_shd1.utils;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Messager {
    public static final Logger LOG = LogManager.getLogger();
    public static void Message(ServerCommandSource Source, String message)
    {
        if(Source.getEntity() == null) {
            Source.getMinecraftServer().sendMessage(Text.Serializer.fromJson(message));
        } else {
            Source.getEntity().sendMessage(Text.Serializer.fromJson(message));
        }
    }
}
