package io.gitee.mc_shd1.commands;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.gitee.mc_shd1.Core;
import io.gitee.mc_shd1.utils.CommandManager;
import io.gitee.mc_shd1.utils.FileManager;
import io.gitee.mc_shd1.utils.Messager;
import io.gitee.mc_shd1.utils.UUIDInfo;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandSource.suggestMatching;

public class statsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> stats = literal("stats")
            .then(literal("refresh")
                .requires((c) -> CommandManager.checkPermission(c, "ops"))
                .executes((c) -> refresh(c.getSource())))
            .then(literal("check")
                .then(argument("玩家", StringArgumentType.word())
                    .suggests((c, b) -> suggestMatching(CommandManager.getPlayers(c.getSource()), b))
                    .then(argument("统计类别", StringArgumentType.word())
                        .suggests(CommandManager.suggestClassification())
                        .then(argument("统计内容", StringArgumentType.word())
                            .executes((c) -> check(c.getSource(), getString(c, "玩家"), getString(c, "统计类别"), getString(c, "统计内容"), false, true))
                            .then(argument("是UUID", BoolArgumentType.bool())
                                .executes((c) -> check(c.getSource(), getString(c, "玩家"), getString(c, "统计类别"), getString(c, "统计内容"), BoolArgumentType.getBool(c,"是UUID"), true))
                                .then(argument("仅自己可见", BoolArgumentType.bool())
                                    .executes((c) -> check(c.getSource(), getString(c, "玩家"), getString(c, "统计类别"), getString(c, "统计内容"), BoolArgumentType.getBool(c,"是UUID"), BoolArgumentType.getBool(c, "仅自己可见")))))))))
            .then(literal("rank")
                .then(argument("统计类别", StringArgumentType.word())
                    .suggests(CommandManager.suggestClassification())
                    .then(argument("统计内容", StringArgumentType.word())
                        .executes((c) -> rank(c.getSource(), getString(c, "统计类别"), getString(c, "统计内容"), true))
                        .then(argument("仅自己可见", BoolArgumentType.bool())
                            .executes((c) -> rank(c.getSource(), getString(c, "统计类别"), getString(c, "统计内容"), BoolArgumentType.getBool(c, "仅自己可见")))))))
            .then(literal("scoreboard")
                .then(literal("set")
                        .then(argument("统计类别", StringArgumentType.word())
                            .suggests(CommandManager.suggestClassification())
                            .then(argument("统计内容", StringArgumentType.word())
                                .executes((c) -> scoreboard_Set(c.getSource(), getString(c, "统计类别"), getString(c, "统计内容"))))))
                .then(literal("show")
                    .executes((c) -> scoreboard_show(c.getSource())))
                .then(literal("hide")
                    .executes((c) -> scoreboard_hide(c.getSource()))));
        dispatcher.register(stats);
    }


    public static int getStatsData(ServerCommandSource source, String uuid, String classification, String target) {
        if (!FileManager.fileExist(source.getMinecraftServer().getLevelName() + "/stats/" + uuid + ".json")) {
            return -1;
        }
        try {
            File statsFile = new File(source.getMinecraftServer().getLevelName() + "/stats/" + uuid + ".json");
            try {
                Reader reader = new InputStreamReader(new FileInputStream(statsFile), StandardCharsets.UTF_8);
                Gson gson = new Gson();
                Map<String, Map<String, Map<String, Double>>> rtMap = gson.fromJson(reader, Map.class);
                try {
                    return rtMap.get("stats").get("minecraft:" + classification).get("minecraft:" + target).intValue();
                } catch (NullPointerException e) {
                    return -2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -3;
    }
    public static int check(ServerCommandSource source, String playerName, String classification, String target, boolean isUUID, boolean istell) {
        String player_uuid;
        if (isUUID == true) {
            player_uuid = playerName;
        } else {
            player_uuid = UUIDInfo.NameToUUID(playerName);
        }
        if (player_uuid == "0") {
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.UUIDCacheLoadFailed);
        } else if(player_uuid == "1") {
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.CantFindPlayerFromUUIDCache);
        } else if(player_uuid == "2") {
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.UnknownErrorByUUIDCache);
        } else {
            int statsData = getStatsData(source, player_uuid, classification, target);
            if (statsData == -1) {
                Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.cantFindPlayerStatsFile);
            } else if (statsData == -2) {
                Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.cantFindStatsFromFile);
            } else if(statsData == -3) {
                Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.UnknownErrorByStatsFinder);
            } else {
                if (istell) {
                    Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.FeedbackMessage
                            .replaceAll("%target_name%", playerName)
                            .replaceAll("%stats_classification%", classification)
                            .replaceAll("%stats_target%", target)
                            .replaceAll("%stats_data%", String.valueOf(statsData)));
                } else {
                    source.getMinecraftServer().getPlayerManager().broadcastChatMessage(Text.Serializer.fromJson(Core.Messages.Commands.Stats.SubCommands.Check.FeedbackMessage.TargetMessage
                            .replaceAll("%player_name%", source.getDisplayName().getString())
                            .replaceAll("%target_name%", playerName)
                            .replaceAll("%stats_classification%", classification)
                            .replaceAll("%stats_target%", target)
                            .replaceAll("%stats_data%", String.valueOf(statsData))), true);
                }
                return 1;
            }
        }
        return 0;
    }
    public static int rank(ServerCommandSource source, String classification, String target, boolean istell) {
        Map<String, String> rtMap = new HashMap<>();
        if (!FileManager.fileExist("config/ServerHelper/UUIDCache.json")) {
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.UUIDCacheLoadFailed);
            return 1;
        } else {
            try {
                File statsFile = new File("config/ServerHelper/SH_UUIDCache.json");
                try {
                    Reader reader = new InputStreamReader(new FileInputStream(statsFile), StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    rtMap = gson.fromJson(reader, Map.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            TreeMap<Integer, String> StatsList = new TreeMap(new Comparator<Integer>(){
                public int compare(Integer a,Integer b){
                    return b-a;
                }
            });
            for (String key : rtMap.keySet()){
                if(getStatsData(source, rtMap.get(key), classification, target) >= 0) {
                    StatsList.put(getStatsData(source, rtMap.get(key), classification, target), key);
                }
            }
            int StatsCount = 0;
            int TotalCount = 0;
            if(StatsList.isEmpty()){
                Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.StatsNull);
            } else {
                String StatsInfo = "";
                for (Integer key : StatsList.keySet()) {
                    StatsCount++;
                    if (StatsCount == 1) {
                        StatsInfo = StatsInfo + Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.FeedbackMessageContent
                        .replaceAll("%rank_count%" , Core.Messages.Commands.Stats.SubCommands.Rank.RankColor.First + String.format("#%-2d", StatsCount))
                        .replaceAll("%target_name%", String.format("%-16s", StatsList.get(key)))
                        .replaceAll("%stats_data%", String.valueOf(key));
                    } else if(StatsCount == 2) {
                        StatsInfo = StatsInfo + "\n" + Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.FeedbackMessageContent
                        .replaceAll("%rank_count%" , Core.Messages.Commands.Stats.SubCommands.Rank.RankColor.Second + String.format("#%-2d", StatsCount))
                        .replaceAll("%target_name%", String.format("%-16s", StatsList.get(key)))
                        .replaceAll("%stats_data%", String.valueOf(key));
                    } else if(StatsCount == 3){
                        StatsInfo = StatsInfo + "\n" + Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.FeedbackMessageContent
                        .replaceAll("%rank_count%" , Core.Messages.Commands.Stats.SubCommands.Rank.RankColor.Third + String.format("#%-2d", StatsCount))
                        .replaceAll("%target_name%", String.format("%-16s", StatsList.get(key)))
                        .replaceAll("%stats_data%", String.valueOf(key));
                    } else {
                        StatsInfo = StatsInfo + "\n" + Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.FeedbackMessageContent
                        .replaceAll("%rank_count%" , Core.Messages.Commands.Stats.SubCommands.Rank.RankColor.Other + String.format("#%-2d", StatsCount))
                        .replaceAll("%target_name%", String.format("%-16s", StatsList.get(key)))
                        .replaceAll("%stats_data%", String.valueOf(key));
                    }
                    TotalCount += key;
                    if (StatsCount == Core.Config.Commands.Stats.Rank_Count) {
                        break;
                    }
                }
                if(istell) {
                    Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.FeedbackMessage
                    .replaceAll("%stats_classification%", classification)
                    .replaceAll("%stats_target%", target)
                    .replaceAll("%rank_count%", String.valueOf(StatsCount))
                    .replaceAll("%rank_total%", String.valueOf(TotalCount)));
                    Messager.sendMessage(source, ("{\"text\": \"" + StatsInfo + "\"}"));
                } else {
                    source.getMinecraftServer().getPlayerManager().broadcastChatMessage(Text.Serializer.fromJson(Core.Messages.Commands.Stats.SubCommands.Rank.FeedbackMessage.TargetMessage
                            .replaceAll("%player_name%", source.getDisplayName().getString())
                            .replaceAll("%stats_classification%", classification)
                            .replaceAll("%stats_target%", target)
                            .replaceAll("%rank_count%", String.valueOf(StatsCount))
                            .replaceAll("%rank_total%", String.valueOf(TotalCount))), true);
                    source.getMinecraftServer().getPlayerManager().broadcastChatMessage(Text.Serializer.fromJson("{\"text\": \"" + StatsInfo + "\"}"), true);
                }
            }
        }
        return 1;
    }
    public static int refresh(ServerCommandSource source) {
        if(UUIDInfo.UUIDCacheRefresh() == 1){
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Refresh.FeedbackMessage.Refresh_Succeed);
        } else {
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Refresh.FeedbackMessage.Refresh_Failed);
        }
        return 1;
    }
    public static int scoreboard_Set(ServerCommandSource source, String classification, String target){
        Map<String, String> rtMap = new HashMap<>();
        if (!FileManager.fileExist("config/ServerHelper/UUIDCache.json")) {
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Set.FeedbackMessage.UUIDCacheLoadFailed);
            return 1;
        } else {
            try {
                File statsFile = new File("config/ServerHelper/UUIDCache.json");
                try {
                    Reader reader = new InputStreamReader(new FileInputStream(statsFile), StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    rtMap = gson.fromJson(reader, Map.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            source.getMinecraftServer().getCommandManager().execute(source.getMinecraftServer().getCommandSource(), "/save-all");
            source.getMinecraftServer().getCommandManager().execute(source.getMinecraftServer().getCommandSource(), "/scoreboard objectives remove StatsHelper");
            source.getMinecraftServer().getCommandManager().execute(source.getMinecraftServer().getCommandSource(), "/scoreboard objectives add StatsHelper minecraft."+ classification + ":minecraft." + target + " {\"text\": \"§6" + classification + "§f.§e" + target +"\"}");
            for (String key : rtMap.keySet()){
                if(getStatsData(source, rtMap.get(key), classification, target) >= 0) {
                    source.getMinecraftServer().getCommandManager().execute(source.getMinecraftServer().getCommandSource(), "scoreboard players set " + key + " StatsHelper " + getStatsData(source, rtMap.get(key), classification, target));
                }
            }
            Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Set.FeedbackMessage.FeedbackMessage);
            scoreboard_show(source);
        }
        return 1;
    }
    public static int scoreboard_show(ServerCommandSource source) {
        source.getMinecraftServer().getCommandManager().execute(source.getMinecraftServer().getCommandSource(), "/scoreboard objectives setdisplay sidebar StatsHelper");
        Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Show.FeedbackMessage.Show_Succeed);
        return 1;
    }
    public static int scoreboard_hide(ServerCommandSource source) {
        source.getMinecraftServer().getCommandManager().execute(source.getMinecraftServer().getCommandSource(), "/scoreboard objectives setdisplay sidebar");
        Messager.sendMessage(source, Core.Messages.Commands.Stats.SubCommands.Hide.FeedbackMessage.Hide_Succeed);
        return 1;
    }
}
