package io.gitee.mc_shd1.config;

import com.google.common.collect.Lists;

import java.util.List;

public final class Config {
    public __Commands Commands = new __Commands();
    public static class __Commands {
        public __Here Here = new __Here();
        public __JoinMotd JoinMotd = new __JoinMotd();
        public __KillEntity KillEntity = new __KillEntity();
        public __OpGet OpGet = new __OpGet();
        public __RestartServer RestartServer = new __RestartServer();
        public __Stats Stats = new __Stats();
        public static class __Here {
            public List<String> Chat_Valid = Lists.newArrayList("!!here");
            public int Glowing_Time = 10;
        }
        public static class __KillEntity {
            public __Item Item = new __Item();
            public static class __Item {
                public List<String> Chat_Valid = Lists.newArrayList("!!ci");
            }
        }
        public static class __JoinMotd {
            public List<String> Chat_Valid = Lists.newArrayList( "!!joinMOTD");
        }
        public static class __OpGet {
            public List<String> Chat_Valid = Lists.newArrayList( "!!op");
        }
        public static class __Stats {
            public int Rank_Count = 10;
        }
        public static class __RestartServer {
            public List<String> Chat_Valid = Lists.newArrayList("!!restart");
        }
    }
}
