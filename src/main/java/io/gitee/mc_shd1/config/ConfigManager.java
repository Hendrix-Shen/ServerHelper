package io.gitee.mc_shd1.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gitee.mc_shd1.utils.Messager;
import io.gitee.mc_shd1.Core;
import io.gitee.mc_shd1.config.JoinMotd;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    public static JoinMotd JoinMotd = new JoinMotd();
    public static Config Config = new Config();
    public static Messages Messages = new Messages();


    public static boolean SetupMainConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/ServerHelper/Config.json");
            if (configFile.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Core.Mod_Name + "]读取 - 主配置文件 Config.json");
                    Config = gson.fromJson(reader, io.gitee.mc_shd1.config.Config.class);
                }
            } else {
                configFile.getParentFile().mkdirs();
                Config = new Config();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Core.Mod_Name + "]写入 - 主配置文件 Config.json");
                    writer.write(gson.toJson(Config));
                }
            }
            return true;
        } catch (Exception e) {
            Messager.LOG.info("["+ Core.Mod_Name + "]读写 - 主配置文件 Config 失败");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SetupMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/ServerHelper/Messages.json");
            if (configFile.exists()) {
                try (
                        Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Core.Mod_Name + "]读取 - 信息文本文件 Messages.json");
                    Messages = gson.fromJson(reader, Messages.class);
                }
            } else {
                configFile.getParentFile().mkdirs();
                Messages = new Messages();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Core.Mod_Name + "]写入 - 信息文本文件 Messages.json");
                    writer.write(gson.toJson(Messages));
                }
            }
            return true;
        } catch (Exception e) {
            Messager.LOG.info("["+ Core.Mod_Name + "]读写 - 信息文本文件 Messages 失败");
            e.printStackTrace();
            return false;
        }
    }
    public static boolean SetupJoinMotdConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/ServerHelper/JoinMotd.json");
            if (configFile.exists()) {
                try (
                        Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Core.Mod_Name + "]读取 - 游戏标语文件 JoinMotd.json");
                    JoinMotd = gson.fromJson(reader, JoinMotd.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                JoinMotd = new JoinMotd();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    Messager.LOG.info("["+ Core.Mod_Name + "]写入 - 游戏标语文件 JoinMotd.json");
                    writer.write(gson.toJson(JoinMotd));
                }
            }
            return true;
        } catch (Exception e) {
            Messager.LOG.info("["+ Core.Mod_Name + "]读写 - 游戏标语文件 JoinMotd.json 失败");
            e.printStackTrace();
            return false;
        }
    }
}
