package io.gitee.mc_shd1.utils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import io.gitee.mc_shd1.Core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UUIDInfo {
    public static Map<String, String> UUIDPluginInfo = Maps.newHashMap();
    public static class UUIDServerCache {
        String name;
        String uuid;
    }
    public static int UUIDCacheRefresh() {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream("usercache.json"), "UTF-8"));
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            while (reader.hasNext()) {
                UUIDServerCache UUIDServerCache = gson.fromJson(reader, UUIDServerCache.class);
                //System.out.println("Stream mode: " + UUIDServerCache.name + ": " + UUIDServerCache.uuid);
                UUIDPluginInfo.put(UUIDServerCache.name, UUIDServerCache.uuid);
            }
            reader.close();
        } catch (UnsupportedEncodingException ex) {
            return 0;
        } catch (IOException ex) {
            return 0;
        }
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        try {
            File UUIDPluginCacheFile = new File("config/SH_UUIDCache.json");
            if (UUIDPluginCacheFile.exists()) {
                UUIDPluginCacheFile.delete();
            }
            UUIDPluginCacheFile.getParentFile().mkdir();
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(UUIDPluginCacheFile), StandardCharsets.UTF_8))
            {
                Message.LOG.info("["+ Core.Mod_Name + "]写入 - UUID缓存文件 SH_UUIDCache.json");
                writer.write(gson1.toJson(UUIDPluginInfo));
            }
        } catch (Exception e) {
            Message.LOG.info("["+ Core.Mod_Name + "]读写 - UUID缓存文件 SH_UUIDCache 失败");
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public static String NameToUUID(String playerName) {
        if (!FileManager.fileExist("config/SH_UUIDCache.json")) {
            return "0";
        } else {
            try {
                File statsFile = new File("config/SH_UUIDCache.json");
                try {
                    Reader reader = new InputStreamReader(new FileInputStream(statsFile), StandardCharsets.UTF_8);
                    Gson gson = new Gson();
                    Map<String, String> rtMap = gson.fromJson(reader, Map.class);
                    try {
                        return rtMap.get(playerName);
                    } catch (NullPointerException e) {
                        return "1";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "2";
    }
}
