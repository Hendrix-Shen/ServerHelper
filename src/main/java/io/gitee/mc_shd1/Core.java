package io.gitee.mc_shd1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import io.gitee.mc_shd1.config.Messages;
import io.gitee.mc_shd1.config.Main;
import io.gitee.mc_shd1.commands.coreCommand;
import io.gitee.mc_shd1.commands.statsCommand;
import io.gitee.mc_shd1.commands.backupCommand;
import io.gitee.mc_shd1.commands.hereCommand;
import io.gitee.mc_shd1.utils.Message;
import io.gitee.mc_shd1.utils.UUIDInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Core {
    public static Messages Messages = new Messages();
    public static Main Config = new Main();

    public static String Mod_Name = "ServerHelper";
    public static String Mod_Ver = "0.0.4";

    public static MinecraftServer minecraft_server;
    private static CommandDispatcher<ServerCommandSource> currentCommandDispatcher;

    public static void onStart(MinecraftServer server) {
        Message.LOG.info("["+ Mod_Name + "]==========检查配置文件==========");
        if (CheckMainConfig() && CheckMessage()) {
            Message.LOG.info("["+ Mod_Name + "]模组载入成功!");
            Message.LOG.info("["+ Mod_Name + "]模组版本: " + Mod_Ver);
        } else {
            Message.LOG.error("["+ Mod_Name + "]模组载入失败!");
            Message.LOG.error("["+ Mod_Name + "]请检查配置文件!");
            Message.LOG.info("["+ Mod_Name + "]模组版本: " + Mod_Ver);
        }
        UUIDInfo.UUIDCacheRefresh();
    }

    public static void onStop(MinecraftServer server) {
        Message.LOG.info("["+ Mod_Name + "]模组卸载!");
        Message.LOG.info("["+ Mod_Name + "]模组版本: "+ Mod_Ver);
    }

    public static void registerCoreCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        backupCommand.register(dispatcher);
        hereCommand.register(dispatcher);
        coreCommand.register(dispatcher);
        statsCommand.register(dispatcher);
        currentCommandDispatcher = dispatcher;
    }

    public static boolean CheckMainConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/SH_Config.json");
            if (configFile.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Message.LOG.info("["+ Mod_Name + "]读取 - 主配置文件 SH_Config.json");
                    Config = gson.fromJson(reader, Main.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                Config = new Main();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Message.LOG.info("["+ Mod_Name + "]写入 - 信息文本文件 SH_Config.json");
                    writer.write(gson.toJson(Config));
                }
            }
            return true;
        } catch (Exception e) {
            Message.LOG.info("["+ Mod_Name + "]读写 - 信息文本文件 SH_Config 失败");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean CheckMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/SH_Messages.json");
            if (configFile.exists()) {
                try (
                    Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Message.LOG.info("["+ Mod_Name + "]读取 - 信息文本文件 SH_Messages.json");
                    Messages = gson.fromJson(reader, Messages.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                configFile.getParentFile().mkdir();
                Messages = new Messages();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Message.LOG.info("["+ Mod_Name + "]写入 - 信息文本文件 SH_Messages.json");
                    writer.write(gson.toJson(Messages));
                }
            }
            return true;
        } catch (Exception e) {
            Message.LOG.info("["+ Mod_Name + "]读写 - 信息文本文件 SH_Messages 失败");
            e.printStackTrace();
            return false;
        }
    }
}

