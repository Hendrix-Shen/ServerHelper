package io.gitee.mc_shd1.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.gitee.mc_shd1.config.ConfigManager;
import io.gitee.mc_shd1.libs.TitleAPI;
import io.gitee.mc_shd1.utils.Messager;
import io.gitee.mc_shd1.utils.Time;
import net.minecraft.server.command.ServerCommandSource;

import java.text.ParseException;

import static net.minecraft.server.command.CommandManager.literal;

public class joinMotdCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> joinMotd = literal("joinMotd")
            .executes((c) -> {try {return JoinMotd(c.getSource());} catch (ParseException e) {e.printStackTrace();}return 0;})
            .then(literal("title")
                .executes((c) -> {try {return Title(c.getSource());} catch (ParseException e) {e.printStackTrace();}return 0;}))
            .then(literal("message")
                .executes((c) -> {try {return Message(c.getSource());} catch (ParseException e) {e.printStackTrace();}return 0;}));

        dispatcher.register(joinMotd);
    }

    public static int JoinMotd(ServerCommandSource source) throws ParseException, CommandSyntaxException {
        if(ConfigManager.JoinMotd.General.ShowMessage) {
            Message(source);
        }
        if(ConfigManager.JoinMotd.General.ShowTitle){
            Title(source);
        }

        return 1;
    }
    public static int Message(ServerCommandSource source) throws ParseException {
        Messager.sendMessage(source, ConfigManager.JoinMotd.JoinMessage.Message.Message
            .replaceAll("%Server_DisplayName%", ConfigManager.JoinMotd.General.ServerDisplayName)
            .replaceAll("%Server_Name%", ConfigManager.JoinMotd.General.ServerName)
            .replaceAll("%StartTime_Days%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Days")))
            .replaceAll("%StartTime_Hours%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Hours")))
            .replaceAll("%StartTime_Minutes%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Minutes")))
        );
        if(ConfigManager.JoinMotd.JoinMessage.Message.EnableMultiServer) {
            Messager.sendMessage(source, ConfigManager.JoinMotd.JoinMessage.Message.ServerListTitle);
            String ServerList = "";
            for (String key : ConfigManager.JoinMotd.General.ServerList.keySet()) {
                ServerList = ServerList + ConfigManager.JoinMotd.JoinMessage.Message.ServerListContent
                    .replaceAll("%ServerList_Name%", key)
                    .replaceAll("%ServerList_DisplayName%", ConfigManager.JoinMotd.General.ServerList.get(key)) + ",";
            }
            Messager.sendMessage(source, "[" + ServerList.substring(0, ServerList.length() - 1) + "]");
        }
        return 1;
    }
    public static int Title(ServerCommandSource source) throws CommandSyntaxException, ParseException {
        TitleAPI.title(source, ConfigManager.JoinMotd.JoinMessage.Title.Title.replaceAll("%Server_DisplayName%", ConfigManager.JoinMotd.General.ServerDisplayName)
                .replaceAll("%Server_Name%", ConfigManager.JoinMotd.General.ServerName)
                .replaceAll("%StartTime_Days%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Days")))
                .replaceAll("%StartTime_Hours%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Hours")))
                .replaceAll("%StartTime_Minutes%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Minutes"))));
        TitleAPI.subtitle(source, ConfigManager.JoinMotd.JoinMessage.Title.SubTitle.replaceAll("%Server_DisplayName%", ConfigManager.JoinMotd.General.ServerDisplayName)
                .replaceAll("%Server_Name%", ConfigManager.JoinMotd.General.ServerName)
                .replaceAll("%StartTime_Days%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Days")))
                .replaceAll("%StartTime_Hours%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Hours")))
                .replaceAll("%StartTime_Minutes%", String.valueOf(Time.Difftime(ConfigManager.JoinMotd.General.FirstRunTime, Time.GetTime()).get("Total_Minutes"))));
        return 1;
    }
}
