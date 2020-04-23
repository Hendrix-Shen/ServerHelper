package io.gitee.mc_shd1.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.gitee.mc_shd1.config.ConfigManager;
import io.gitee.mc_shd1.utils.CommandManager;
import io.gitee.mc_shd1.Core;
import io.gitee.mc_shd1.utils.Messager;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.text.ParseException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.*;
import static net.minecraft.server.command.CommandSource.suggestMatching;

public class coreCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> core = literal("core")
                .executes((c) -> CoreHelpCommand(c.getSource(), 1))
                .then(literal("backup")
                    .requires((player) -> CommandManager.checkPermission(player, "ops"))
                    .then(argument("备份名称", greedyString())
                    .executes((c) -> backupCommand.backup(c.getSource(), getString(c,"备份名称")))))
                .then(literal("help")
                        .executes((c) -> CoreHelpCommand(c.getSource(), 1))
                        .then(argument("页面", integer())
                                .executes((c) -> CoreHelpCommand(c.getSource(), getInteger(c, "页面")))))
                .then(literal("here")
                    .executes((c) -> hereCommand.here(c.getSource(), c.getSource().getPlayer()))
                        .then(argument("玩家", EntityArgumentType.player())
                            .requires((player) -> CommandManager.checkPermission(player, "ops"))
                            .executes((c) -> hereCommand.here(c.getSource(), EntityArgumentType.getPlayer(c, "玩家")))))
                .then(literal("joinMotd")
                        .executes((c) -> {try {return joinMotdCommand.JoinMotd(c.getSource());} catch (ParseException e) {e.printStackTrace();}return 0;})
                        .then(literal("title")
                                .executes((c) -> {try {return joinMotdCommand.Title(c.getSource());} catch (ParseException e) {e.printStackTrace();}return 0;}))
                        .then(literal("message")
                                .executes((c) -> {try {return joinMotdCommand.Message(c.getSource());} catch (ParseException e) {e.printStackTrace();}return 0;})))
                .then(literal("reload")
                    .requires((player) -> CommandManager.checkPermission(player, "ops"))
                    .then(literal("all")
                        .executes((c) -> CoreReloadAll(c.getSource())))
                    .then(literal("config")
                        .executes((c) -> CoreReloadConfig(c.getSource())))
                    .then(literal("message")
                        .executes((c) -> CoreReloadMessage(c.getSource()))))
                .then(literal("stats")
                    .then(literal("refresh")
                            .requires((c) -> CommandManager.checkPermission(c, "ops"))
                            .executes((c) -> statsCommand.refresh(c.getSource())))
                    .then(literal("check")
                            .then(argument("玩家", StringArgumentType.word())
                                    .suggests((c, b) -> suggestMatching(CommandManager.getPlayers(c.getSource()), b))
                                    .then(argument("统计类别", StringArgumentType.word())
                                            .suggests(CommandManager.suggestClassification())
                                            .then(argument("统计内容", StringArgumentType.word())
                                                    .executes((c) -> statsCommand.check(c.getSource(), getString(c, "玩家"), getString(c, "统计类别"), getString(c, "统计内容"), false, true))
                                                    .then(argument("是UUID", BoolArgumentType.bool())
                                                            .executes((c) -> statsCommand.check(c.getSource(), getString(c, "玩家"), getString(c, "统计类别"), getString(c, "统计内容"), BoolArgumentType.getBool(c,"是UUID"), true))
                                                            .then(argument("仅自己可见", BoolArgumentType.bool())
                                                                    .executes((c) -> statsCommand.check(c.getSource(), getString(c, "玩家"), getString(c, "统计类别"), getString(c, "统计内容"), BoolArgumentType.getBool(c,"是UUID"), BoolArgumentType.getBool(c, "仅自己可见")))))))))
                    .then(literal("rank")
                            .then(argument("统计类别", StringArgumentType.word())
                                    .suggests(CommandManager.suggestClassification())
                                    .then(argument("统计内容", StringArgumentType.word())
                                            .executes((c) -> statsCommand.rank(c.getSource(), getString(c, "统计类别"), getString(c, "统计内容"), true))
                                            .then(argument("仅自己可见", BoolArgumentType.bool())
                                                    .executes((c) -> statsCommand.rank(c.getSource(), getString(c, "统计类别"), getString(c, "统计内容"), BoolArgumentType.getBool(c, "仅自己可见")))))))
                    .then(literal("scoreboard")
                        .then(literal("set")
                                .then(argument("统计类别", StringArgumentType.word())
                                        .suggests(CommandManager.suggestClassification())
                                        .then(argument("统计内容", StringArgumentType.word())
                                                .executes((c) -> statsCommand.scoreboard_Set(c.getSource(), getString(c, "统计类别"), getString(c, "统计内容"))))))
                        .then(literal("show")
                            .executes((c) -> statsCommand.scoreboard_show(c.getSource())))
                        .then(literal("hide")
                            .executes((c) -> statsCommand.scoreboard_hide(c.getSource())))))
                .then(literal("wiki")
                    .then(literal("official")
                        .then(argument("搜索内容", greedyString()).executes((c) -> wikiCommand.wiki(c.getSource(), getString(c, "搜索内容"), "Official"))))
                    .then(literal("bilibili")
                        .then(argument("搜索内容", greedyString()).executes((c) -> wikiCommand.wiki(c.getSource(), getString(c, "搜索内容"), "Bilibili")))));
        dispatcher.register(core);
    }

    public static int CoreReloadAll(ServerCommandSource source) {
        Messager.LOG.info("[ServerHelper]正在重新加载配置文件(完全模式)");
        Messager.LOG.info("[ServerHelper]==========检查配置文件==========");
        if (ConfigManager.SetupMainConfig() && ConfigManager.SetupMessage() && ConfigManager.SetupJoinMotdConfig()) {
            Messager.sendMessage(source,
                    ConfigManager.Messages.Commands.Core.SubCommands.Reload.FeedbackMessage.All_Succeed
                    .replaceAll("%reload_mode%", ConfigManager.Messages.Commands.Core.SubCommands.Reload.ReloadMod.FullMode));
            Messager.LOG.info("[ServerHelper]重载完成!");
        } else {
            Messager.sendMessage(source,
                    ConfigManager.Messages.Commands.Core.SubCommands.Reload.FeedbackMessage.All_Failed
                    .replaceAll("%reload_mode%", ConfigManager.Messages.Commands.Core.SubCommands.Reload.ReloadMod.FullMode));
            Messager.LOG.error("[ServerHelper]重载失败!");
            Messager.LOG.error("[ServerHelper]请检查配置文件!");
        }
        return 1;
    }
    public static int CoreReloadConfig(ServerCommandSource source) {
        Messager.LOG.info("[ServerHelper]正在重新加载配置文件(简单模式)");
        Messager.LOG.info("[ServerHelper]==========检查配置文件==========");
        if (ConfigManager.SetupMainConfig()) {
            Messager.sendMessage(source,
                    ConfigManager.Messages.Commands.Core.SubCommands.Reload.FeedbackMessage.Config_Succeed
                    .replaceAll("%reload_mode%", ConfigManager.Messages.Commands.Core.SubCommands.Reload.ReloadMod.Config));
            Messager.LOG.info("[ServerHelper]重载完成!");
        } else {
            Messager.sendMessage(source,
                    ConfigManager.Messages.Commands.Core.SubCommands.Reload.FeedbackMessage.Config_Failed
                    .replaceAll("%reload_mode%", ConfigManager.Messages.Commands.Core.SubCommands.Reload.ReloadMod.Config));
            Messager.LOG.error("[ServerHelper]重载失败!");
            Messager.LOG.error("[ServerHelper]请检查配置文件!");
        }
        return 1;
    }
    public static int CoreReloadMessage(ServerCommandSource source) {
        Messager.LOG.info("[ServerHelper]正在重新加载配置文件(简单模式)");
        Messager.LOG.info("[ServerHelper]==========检查配置文件==========");
        if (ConfigManager.SetupMessage()) {
            Messager.sendMessage(source,
                    ConfigManager.Messages.Commands.Core.SubCommands.Reload.FeedbackMessage.Message_Succeed
                    .replaceAll("%reload_mode%", ConfigManager.Messages.Commands.Core.SubCommands.Reload.ReloadMod.Message));
            Messager.LOG.info("[ServerHelper]重载完成!");
        } else {
            Messager.sendMessage(source,
                    ConfigManager.Messages.Commands.Core.SubCommands.Reload.FeedbackMessage.Message_Failed
                    .replaceAll("%reload_mode%", ConfigManager.Messages.Commands.Core.SubCommands.Reload.ReloadMod.Message));
            Messager.LOG.error("[ServerHelper]重载失败!");
            Messager.LOG.error("[ServerHelper]请检查配置文件!");
        }
        return 1;
    }
    public static int CoreHelpCommand(ServerCommandSource source, int Page) {
        int MaxPage = 2;
        if (Page == 1) {
            Messager.sendMessage(source, "[" +
                    "{\"text\": \"§e==================== §eCore指令帮助 §e====================\n\"}," +
                    "{\"text\": \"§a/core backup <存档> §7- §b备份存档文件\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/backup\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core backup \"}}," + //1
                    "{\"text\": \"§a/core help [页码] §7- §b显示帮助菜单\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/core\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core\"}}," +             //2
                    "{\"text\": \"§a/core here [玩家] §7- §b高亮玩家位置\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/here\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core here \"}}," +       //3
                    "{\"text\": \"§a/core joinMotd [模式] §7- §b显示欢迎标语\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/joinMotd\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core joinMotd  \"}}," +       //4
                    "{\"text\": \"§a/core reload <模式> §7- §b重载配置文件\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core reload \"}}," +                          //5
                    "{\"text\": \"§a/core stats check <玩家/UUID> <统计类别> <统计内容> [是UUID] [仅自己可见] §7- §b查询玩家统计信息\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/stats check\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core stats check \"}}," + //6
                    "{\"text\": \"§a/core stats rank <统计类别> <统计内容> [仅自己可见] §7- §b查看统计信息排行\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/stats rank\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core stats rank \"}}," + //7
                    "{\"text\": \"§e================ 第 §2" + String.format("%2d", Page) +"§e 页 || 共 §2" + String.format("%2d", MaxPage) + "§e 页 ================\n\"}," +
                    "{\"text\": \"§e============== \"}," +
                    "{\"text\": \"§7<<< 上一页\"}," +
                    "{\"text\": \"§e || \"}," +
                    "{\"text\": \"§2下一页 >>>\", \"clickEvent\": {\"action\": \"run_command\", \"value\": \"/core help " + (Page + 1) + "\"}}," +
                    "{\"text\": \" §e==============\"}]");
        } else if (Page == 2) {
            Messager.sendMessage(source, "[" +
                    "{\"text\": \"§e==================== §eCore指令帮助 §e====================\n\"}," +
                    "{\"text\": \"§a/core stats refresh §7- §b刷新UUID缓存\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/stats refresh\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core stats refresh\"}}," + //1
                    "{\"text\": \"§a/core stats scoreboard hide §7- §b隐藏统计信息计分板\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/stats scoreboard hide\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core stats scoreboard hide\"}}," + //2
                    "{\"text\": \"§a/core stats scoreboard set <统计类别> <统计内容> §7- §b设置统计信息计分板\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/stats scoreboard set\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core stats scoreboard set \"}}," + //3
                    "{\"text\": \"§a/core stats scoreboard show §7- §b显示统计信息计分板\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/stats scoreboard show\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core stats scoreboard show\"}}," + //4
                    "{\"text\": \"§a/core wiki <源> <搜索信息> §7- §b从百科上搜索信息\n\", \"hoverEvent\": {\"action\": \"show_text\", \"value\": \"§e别称\n§f- §7/wiki\n§a点击复制到聊天窗\"}, \"clickEvent\": {\"action\": \"suggest_command\", \"value\": \"/core wiki \"}}," + //5
                    "{\"text\": \"§e================ 第 §2" + String.format("%2d", Page) +"§e 页 || 共 §2" + String.format("%2d", MaxPage) + "§e 页 ================\n\"}," +
                    "{\"text\": \"§e============== \"}," +
                    "{\"text\": \"§2<<< 上一页\", \"clickEvent\": {\"action\": \"run_command\", \"value\": \"/core help " + (Page - 1) + "\"}}," +
                    "{\"text\": \"§e || \"}," +
                    "{\"text\": \"§7下一页 >>>\"}," +
                    "{\"text\": \" §e==============\"}]");
        }
        return 1;
    }
}