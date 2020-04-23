package io.gitee.mc_shd1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import io.gitee.mc_shd1.commands.*;
import io.gitee.mc_shd1.config.ConfigManager;
import io.gitee.mc_shd1.config.Messages;
import io.gitee.mc_shd1.config.Config;
import io.gitee.mc_shd1.utils.Messager;
import io.gitee.mc_shd1.utils.UUIDInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Core {

    public static String Mod_Name = "ServerHelper";
    public static String Mod_Ver = "0.0.7";

    public static MinecraftServer minecraft_server;
    private static CommandDispatcher<ServerCommandSource> currentCommandDispatcher;

    public static void onStart(MinecraftServer server) {
        Messager.LOG.info("["+ Mod_Name + "]==========检查配置文件==========");
        if (ConfigManager.SetupMainConfig() && ConfigManager.SetupMessage() && ConfigManager.SetupJoinMotdConfig()) {
            Messager.LOG.info("["+ Mod_Name + "]模组载入成功!");
            Messager.LOG.info("["+ Mod_Name + "]模组版本: " + Mod_Ver);
        } else {
            Messager.LOG.error("["+ Mod_Name + "]模组载入失败!");
            Messager.LOG.error("["+ Mod_Name + "]请检查配置文件!");
            Messager.LOG.info("["+ Mod_Name + "]模组版本: " + Mod_Ver);
        }
        UUIDInfo.UUIDCacheRefresh();
    }

    public static void onStop(MinecraftServer server) {
        Messager.LOG.info("["+ Mod_Name + "]模组卸载!");
        Messager.LOG.info("["+ Mod_Name + "]模组版本: "+ Mod_Ver);
    }

    public static void registerCoreCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        backupCommand.register(dispatcher);
        coreCommand.register(dispatcher);
        hereCommand.register(dispatcher);
        joinMotdCommand.register(dispatcher);
        statsCommand.register(dispatcher);
        wikiCommand.register(dispatcher);
        currentCommandDispatcher = dispatcher;
    }
}

