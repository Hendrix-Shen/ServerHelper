package io.gitee.mc_shd1.libs;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.network.packet.TitleS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/*
* 该 API 源自 https://github.com/hanbings/TitleAPI/
* 此 API 仅为 Python 转写 Java
* 唯一修改部分为 Python 执行服务端指令 而此API从底层实现
*
* */
public class TitleAPI {
    // title <玩家> clear（移除屏幕标题）
    public static void clear_title(ServerPlayerEntity player) {
        TitleS2CPacket titleS2CPacket = new TitleS2CPacket(TitleS2CPacket.Action.CLEAR, (Text)null);
        player.networkHandler.sendPacket(titleS2CPacket);
    }

    // title <玩家> reset（将各选项复位至默认值）
    public static void reset_title(ServerPlayerEntity player) {
        TitleS2CPacket titleS2CPacket = new TitleS2CPacket(TitleS2CPacket.Action.RESET, (Text)null);
        player.networkHandler.sendPacket(titleS2CPacket);
    }

    // title <玩家> title <JSON文本标题>（将文字显示为主标题）
    public static void title(ServerPlayerEntity player, String message) {
        player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.TITLE, Text.Serializer.fromJson(message)));
    }
    public static void title(ServerCommandSource source, String message) throws CommandSyntaxException {
        if(source.getEntity() == null) {
            source.getMinecraftServer().sendMessage(Text.Serializer.fromJson(message));
        } else {
            source.getPlayer().networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.TITLE, Text.Serializer.fromJson(message)));
        }
    }

    // title <玩家> subtitle <JSON文本标题>（将文字显示为副标题）
    public static void subtitle(ServerPlayerEntity player, String message) {
        player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.SUBTITLE, Text.Serializer.fromJson(message)));
    }
    public static void subtitle(ServerCommandSource source, String message) throws CommandSyntaxException {
        if(source.getEntity() == null) {
            source.getMinecraftServer().sendMessage(Text.Serializer.fromJson(message));
        } else {
            source.getPlayer().networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.SUBTITLE, Text.Serializer.fromJson(message)));
        }
    }

    // title <玩家> actionbar <JSON文本标题>（在快捷栏上方显示的标题）
    public static void actionbar(ServerPlayerEntity player, String message) {
        player.networkHandler.sendPacket(new TitleS2CPacket(TitleS2CPacket.Action.ACTIONBAR, Text.Serializer.fromJson(message)));
    }

    // title <玩家> times <渐入> <保持> <渐出>（设置渐入、保持和渐出的持续时间）
    public static void times(ServerPlayerEntity player, String message, int fadeIn, int stay, int fadeOut) {
        TitleS2CPacket titleS2CPacket = new TitleS2CPacket(fadeIn, stay, fadeOut);
        player.networkHandler.sendPacket(titleS2CPacket);
    }

    // 封装带子标题的标题（在屏幕中间显示）
    public static void times(ServerPlayerEntity player, String Title, String SubTitle) {
        title(player, Title);
        subtitle(player, SubTitle);
    }
}
