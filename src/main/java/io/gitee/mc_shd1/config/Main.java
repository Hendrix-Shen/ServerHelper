package io.gitee.mc_shd1.config;

import com.google.common.collect.Lists;

import java.util.List;

public final class Main {
    public __Commands Commands = new __Commands();
    public static class __Commands {
        public __Here Here = new __Here();
        public __Stats Stats = new __Stats();
        public __KillEntity KillEntity = new __KillEntity();
        public __OpGet OpGet = new __OpGet();
        public static class __Here {
            public List<String> Chat_Valid = Lists.newArrayList("!here", "!!here", "！here", "！！here");
            public int Glowing_Time = 10;
        }
        public static class __Stats {
            public int Rank_Count = 10;
        }
        public static class __KillEntity {
            public __Item Item = new __Item();
            public static class __Item {
                public List<String> Chat_Valid = Lists.newArrayList("!ci", "!!ci", "！ci", "！！ci");
            }
        }
        public static class __OpGet {
            public List<String> Chat_Valid = Lists.newArrayList("!op", "!!op", "！op", "！！op");
        }
    }
}
