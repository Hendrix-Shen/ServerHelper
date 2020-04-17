package io.gitee.mc_shd1.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.gitee.mc_shd1.Core;
import io.gitee.mc_shd1.utils.CommandManager;
import io.gitee.mc_shd1.utils.FileManager;
import io.gitee.mc_shd1.utils.Messager;
import net.minecraft.server.command.ServerCommandSource;

import java.io.File;
import java.io.IOException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class backupCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("backup")
                .requires((player) -> CommandManager.checkPermission(player, "ops"))
                .then(argument("备份名称", greedyString()).executes((c) -> backup(c.getSource(), getString(c, "备份名称"))));

        dispatcher.register(command);
    }

    public static int backup(ServerCommandSource source, String backupName) {
        String levelName = source.getMinecraftServer().getLevelName();
        source.getMinecraftServer().getCommandManager().execute(source, "/save-all");
        if (!FileManager.dirExist("backup")) {
            File file = new File("backup");
            file.mkdir();
        }
        if (!FileManager.dirExist("backup/" + backupName)) {
            try {
                FileManager.copyDir(levelName, "backup/" + levelName);

            } catch (IOException e) {
                Messager.sendMessage(source, Core.Messages.Commands.Backup.FeedbackMessage.Failed_Unknown
                    .replaceAll("%backup_name%", backupName));
                e.printStackTrace();
                return 0;
            }
            FileManager.renameFileAndDir("backup/" + levelName, "backup/" + backupName);
            Messager.sendMessage(source, Core.Messages.Commands.Backup.FeedbackMessage.Succeed
                .replaceAll("%backup_name%", backupName));
        } else {
            Messager.sendMessage(source, Core.Messages.Commands.Backup.FeedbackMessage.Failed_AlreadyExist
                .replaceAll("%backup_name%", backupName));
        }
        return 1;
    }
}
