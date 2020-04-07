package io.gitee.mc_shd1.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public final class Messages {
    public __General General = new __General();
    public class __General {
        public Map<String, String> dimensions = Maps.newHashMap(ImmutableMap.of(
                "overworld", "§a主世界", "the_nether", "§c地狱", "the_end", "§e末地"));

    }
    public __Commands Commands = new __Commands();
    public class __Commands {
        public __Core Core = new __Core();
        public __Here Here = new __Here();
        public __Stats Stats = new __Stats();
        public class __Core {
            public __SubCommands SubCommands = new __SubCommands();
            public class __SubCommands {
                public __Reload Reload = new __Reload();
                public class __Reload {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public __ReloadMod ReloadMod = new __ReloadMod();
                    public class __FeedbackMessage {
                        public String All_Succeed = "[{\"text\":\"§acore §7>> §b配置文件已重载(%reload_mode%§b).\"}]";
                        public String All_Failed = "[{\"text\":\"§acore §7>> §c配置文件重载失败(%reload_mode%§c).\"}]";
                        public String Config_Succeed = "[{\"text\":\"§acore §7>> §b配置文件已重载(%reload_mode%§b).\"}]";
                        public String Config_Failed = "[{\"text\":\"§acore §7>> §c配置文件重载失败(%reload_mode%§c).\"}]";
                        public String Message_Succeed = "[{\"text\":\"§acore §7>> §b配置文件已重载(%reload_mode%§b).\"}]";
                        public String Message_Failed = "[{\"text\":\"§acore §7>> §c配置文件重载失败(%reload_mode%§c).\"}]";
                    }
                    public class __ReloadMod {
                        public String FullMode = "完全模式";
                        public String Config = "配置文件";
                        public String Message = "语言文件";
                    }
                }
            }
        }
        public class __Here {
            public String TargetMessage = "[{\"text\":\"%player_name% 在 %player_dimension% §r@§b\"},{\"text\":\"[x:%player_x%, y:%player_y%, z:%player_z%]\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"%player_x% %player_y% %player_z%\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"§a点击复制坐标\"]}},{\"text\":\"\",\"insertion\":\" §r向各位打招呼\"}]";
            public String FeedbackMessage = "[{\"text\":\"§a你将会被高亮§e %glowing_time% §a秒\"}]";
        }
        public class __Stats {
            public __SubCommands SubCommands = new __SubCommands();
            public class __SubCommands {
                public __Check Check = new __Check();
                public __Rank Rank = new __Rank();
                public __Refresh Refresh = new __Refresh();
                public __Set Set = new __Set();
                public __Show Show = new __Show();
                public __Hide Hide = new __Hide();
                public class __Check {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public class __FeedbackMessage {
                        public String UUIDCacheLoadFailed = "{\"text\": \"§c错误: 无法读取 UUID 缓存, 请尝试刷新UUID缓存.\"}";
                        public String CantFindPlayerFromUUIDCache = "{\"text\": \"§c错误: 无法找到从UUID缓存中找到该玩家, 请尝试刷新UUID缓存.\"}";
                        public String UnknownErrorByUUIDCache = "{\"text\": \"§c错误: 未知错误.\"}";
                        public String cantFindPlayerStatsFile = "{\"text\": \"§c错误: 您所查找的玩家不存在.\"}";
                        public String cantFindStatsFromFile = "{\"text\": \"§c错误: 您所查找的统计信息不存在.\"}";
                        public String UnknownErrorByStatsFinder = "{\"text\": \"§c错误: 未知错误.\"}";
                        public String FeedbackMessage = "{\"text\": \"玩家§b%target_name%§f的统计信息[§6%stats_classification%§f.§e%stats_target%§f]的值为§a%stats_data%\"}";
                        public String TargetMessage = "{\"text\": \"玩家§a%player_name%§f查询了玩家§b%target_name%§f的统计信息[§6%stats_classification%§f.§e%stats_target%§f], 其值为§a%stats_data%\"}";
                    }
                }
                public class __Rank {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public __RankColor RankColor = new __RankColor();
                    public class __RankColor {
                        public String First = "§b";
                        public String Second = "§d";
                        public String Third = "§e";
                        public String Other = "§a";

                    }
                    public class __FeedbackMessage {
                        public String UUIDCacheLoadFailed = "{\"text\": \"§c错误: 无法读取 UUID 缓存, 请尝试刷新UUID缓存.\"}";
                        public String StatsNull = "{\"text\": \"未找到该统计项或该统计项全空\"}";
                        public String FeedbackMessageContent = "§a%rank_count% §b%target_name% §e%stats_data%";
                        public String FeedbackMessage = "{\"text\": \"统计信息[§6%stats_classification%§f.§e%stats_target%§f]的总数为§c%rank_total%§f前§b%rank_count%§f名为\"}";
                        public String TargetMessage = "{\"text\": \"玩家§a%player_name%§f列出了统计信息[§6%stats_classification%§f.§e%stats_target%§f]的总数为§c%rank_total%§f前§b%rank_count%§f名为\"}";
                    }
                }
                public class __Refresh {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public class __FeedbackMessage {
                        public String Refresh_Succeed = "[{\"text\":\"§a玩家UUID缓存更新更新成功.\"}]";
                        public String Refresh_Failed = "[{\"text\":\"§c玩家UUID缓存更新更新失败.\"}]";
                    }
                }
                public class __Set {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public class __FeedbackMessage {
                        public String UUIDCacheLoadFailed = "{\"text\": \"§c错误: 无法读取 UUID 缓存, 请尝试刷新UUID缓存.\"}";
                        public String FeedbackMessage = "{\"text\": \"§a成功设置计分板.\"}";
                    }
                }
                public class __Show {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public class __FeedbackMessage {
                        public String Show_Succeed = "{\"text\": \"§a打开了计分板\"}";
                    }
                }
                public class __Hide {
                    public __FeedbackMessage FeedbackMessage = new __FeedbackMessage();
                    public class __FeedbackMessage {
                        public String Hide_Succeed = "{\"text\": \"§a关闭了计分板\"}";
                    }
                }
            }
        }
    }
}
