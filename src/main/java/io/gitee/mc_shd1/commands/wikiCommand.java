package io.gitee.mc_shd1.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.gitee.mc_shd1.config.ConfigManager;
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

public class wikiCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("wiki")
            .then(literal("official")
                .then(argument("搜索内容", greedyString()).executes((c) -> wiki(c.getSource(), getString(c, "搜索内容"), "Official"))))
            .then(literal("bilibili")
                .then(argument("搜索内容", greedyString()).executes((c) -> wiki(c.getSource(), getString(c, "搜索内容"), "Bilibili"))));

        dispatcher.register(command);
    }
    public static int wiki(ServerCommandSource source, String content, String WebPage) {
        if(WebPage == "Official") {
            Messager.sendMessage(source, ConfigManager.Messages.Commands.Wiki.OfficalWiki
                .replaceAll("%search_content%", content)
                .replaceAll("%search_url_content%", content.replaceAll(" ", "%20")));
        } else if(WebPage == "Bilibili") {
            Messager.sendMessage(source, ConfigManager.Messages.Commands.Wiki.BilibiliWiki
                .replaceAll("%search_content%", content)
                .replaceAll("%search_url_content%", content.replaceAll(" ", "%20")));
        }
        return 1;
    }
}
