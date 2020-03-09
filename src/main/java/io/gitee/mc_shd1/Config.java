package io.gitee.mc_shd1;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Config {
	public static class MainConfig {
	    public List<String> command_here_valid_list = Lists.newArrayList("!here", "!!here", "！here", "！！here");
	    public int command_here_glowing_time = 10;
	    //public boolean backup = true;
	    public List<String> command_backup_valid_list[];
	}
	public static class MessageConfig {
	    public Map<String, String> command_here_dimensions = Maps.newHashMap(ImmutableMap.of(
	            "overworld", "§a主世界", "the_nether", "§c地狱", "the_end", "§e末地"));
	    public String command_here_broadcast_message = "%s §r在 %s§r@§b[x:%s, y:%s, z:%s] §r向各位打招呼";
	    public String command_here_glowing_message = "§a你将会被高亮§e%s§a秒";
	    public String command_backup_broadcast_message = "§a你将会被高亮§e%s§a秒";
	    public String command_feedback_message_successful = "§a存档备份成功";
	    public String command_feedback_message_failed = "§a存档备份失败, 请检查控制台输出.";
	    public String command_feedback_message_alreadyExist = "§a存档备份失败, 该备份已存在.";
	}
}
