package io.gitee.mc_shd1;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;

import io.gitee.mc_shd1.commands.hereCommand;
import io.gitee.mc_shd1.utils.Message;
import io.gitee.mc_shd1.Config;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

public class Core
{
    private static Config.MainConfig config;
    private static Config.MessageConfig message;
    public static Config.MainConfig getConfig() {
        return config;
    }
    public static Config.MessageConfig getMessage() {
        return message;
    }
    public static MinecraftServer minecraft_server;
    private static CommandDispatcher<ServerCommandSource> currentCommandDispatcher;

	public static void onStart(MinecraftServer server)
    {
		Message.LOG.info("[HSCore]==========检查配置文件==========");
		if(CheckMainConfig() && CheckMessage()) {
			Message.LOG.info("[HSCore]==========检查配置文件==========");
	    	Message.LOG.info("[HSCore]模组载入成功!");
	    	Message.LOG.info("[HSCore]模组作者Hendrix Shen");
	    	Message.LOG.info("[HSCore]模组版本: 0.0.2!");
		} else {
			Message.LOG.info("[HSCore]==========检查配置文件==========");
	    	Message.LOG.error("[HSCore]模组载入失败!");
	    	Message.LOG.error("[HSCore]请检查配置文件!");
	    	Message.LOG.info("[HSCore]模组作者Hendrix Shen");
	    	Message.LOG.info("[HSCore]模组版本: 0.0.2!");
		}
    }
    public static void onStop(MinecraftServer server)
    {
    	Message.LOG.info("[HSCore]模组卸载!");
    	Message.LOG.info("[HSCore]模组作者Hendrix Shen");
    	Message.LOG.info("[HSCore]模组版本: 0.0.2");
    }
    public static void registerCoreCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        hereCommand.register(dispatcher);
        currentCommandDispatcher = dispatcher;
    }
    public static boolean CheckMainConfig() {
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/HS_Config.json");
            if (configFile.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                	Message.LOG.info("[HSCore]读取 - 主配置文件 HS_Config.json");
                    config = gson.fromJson(reader, Config.MainConfig.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                config = new Config.MainConfig();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                	Message.LOG.info("[HSCore]写入 - 信息文本文件 HS_Config.json");
                    writer.write(gson.toJson(config));
                }
            }
            return true;
        }catch (Exception e){
        	Message.LOG.info("[HSCore]读写 - 信息文本文件 HS_Config 失败");
            e.printStackTrace();
            return false;
        }
    }
    public static boolean CheckMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File configFile = new File("config/HS_Message.json");
            if (configFile.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                	Message.LOG.info("[HSCore]读取 - 信息文本文件 HS_Message.json");
                	message = gson.fromJson(reader, Config.MessageConfig.class);
                }
            } else {
                configFile.getParentFile().mkdir();
                message = new Config.MessageConfig();
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                	Message.LOG.info("[HSCore]写入 - 信息文本文件 HS_Message.json");
                    writer.write(gson.toJson(message));
                }
            }
            return true;
        }catch (Exception e){
        	Message.LOG.info("[HSCore]读写 - 信息文本文件 HS_Message 失败");
            e.printStackTrace();
            return false;
        }
    }
		
}

