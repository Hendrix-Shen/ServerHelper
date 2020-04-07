package io.gitee.mc_shd1.utils;

import net.minecraft.server.command.ServerCommandSource;

public class CommandManager {
    public static boolean checkPermission(ServerCommandSource source, String commandLevel) {
        switch (commandLevel) {
            case "true":
                return true;
            case "false":
                return false;
            case "ops":
                return source.hasPermissionLevel(2); // typical for other cheaty commands
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
                return source.hasPermissionLevel(Integer.parseInt(commandLevel));
        }
        return false;
    }
}
