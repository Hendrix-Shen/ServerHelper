package io.gitee.mc_shd1.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.gitee.mc_shd1.utils.Time;

import java.util.Map;
import java.util.TreeMap;

public class JoinMotd {
    public __General General = new __General();
    public __JoinMessage JoinMessage = new __JoinMessage();
    public class __General {
        public String FirstRunTime = Time.GetTime();
        public String ServerName = "My Server";
        public String ServerDisplayName = "我的服务器";
        public Map<String, String> ServerList = new TreeMap<String, String>(Maps.newHashMap(ImmutableMap.of("Survival", "生存", "Creative", "创造", "Mirror", "镜像")));
        public boolean ShowMessage = true;
        public boolean ShowTitle = true;
    }
    public class __JoinMessage {
        public __Message Message = new __Message();
        public __Title Title = new __Title();
        public class __Message {
            public boolean EnableMultiServer = true;
            public String Message = "{\"text\": \"§7======= §f欢迎回到 %Server_DisplayName% §7=======\n今天是§e%Server_Name%§r开服的第§e%StartTime_Days%§r天\"}";
            public String ServerListTitle = "{\"text\": \"§7-------§f 服务器列表 §7-------§f\"}";
            public String ServerListContent = "{\"text\":\"[%ServerList_Name%]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/server %ServerList_Name%\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§a点击传送至 %ServerList_DisplayName%\"}}, {\"text\":\" \"}";
        }
        public class __Title {
            public String Title = "{\"text\": \"§a欢迎回到 §6%Server_DisplayName%\"}";
            public String SubTitle = "{\"text\": \"§b今天是§e%Server_Name%§b开服的第§e%StartTime_Days%§v§b天\"}";
        }
    }
}
