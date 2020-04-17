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
    public static Config Config = new Config();
    public static Messages Messages = new Messages();

    public static String Mod_Name = "ServerHelper";
    public static String Mod_Ver = "0.0.6";

    public static MinecraftServer minecraft_server;
    private static CommandDispatcher<ServerCommandSource> currentCommandDispatcher;

    public static void onStart(MinecraftServer server) {
        Messager.LOG.info("["+ Mod_Name + "]==========检查配置文件==========");
        if (CheckMainConfig() && CheckMessage() && ConfigManager.SetupJoinMotdConfig()) {
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
        statsCommand.register(dispatcher);
        joinMotdCommand.register(dispatcher);
        currentCommandDispatcher = dispatcher;
    }

    public static boolean CheckMainConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/ServerHelper/Config.json");
            if (configFile.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Mod_Name + "]读取 - 主配置文件 Config.json");
                    Config = gson.fromJson(reader, io.gitee.mc_shd1.config.Config.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                Config = new Config();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Mod_Name + "]写入 - 主配置文件 Config.json");
                    writer.write(gson.toJson(Config));
                }
            }
            return true;
        } catch (Exception e) {
            Messager.LOG.info("["+ Mod_Name + "]读写 - 主配置文件 Config 失败");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean CheckMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/ServerHelper/Messages.json");
            if (configFile.exists()) {
                try (
                    Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Mod_Name + "]读取 - 信息文本文件 Messages.json");
                    Messages = gson.fromJson(reader, Messages.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                Messages = new Messages();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Mod_Name + "]写入 - 信息文本文件 Messages.json");
                    writer.write(gson.toJson(Messages));
                }
            }
            return true;
        } catch (Exception e) {
            Messager.LOG.info("["+ Mod_Name + "]读写 - 信息文本文件 Messages 失败");
            e.printStackTrace();
            return false;
        }
    }

}

