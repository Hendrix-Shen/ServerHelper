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
