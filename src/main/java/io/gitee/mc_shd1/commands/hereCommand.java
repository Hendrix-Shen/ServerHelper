package io.gitee.mc_shd1.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.gitee.mc_shd1.Core;
import io.gitee.mc_shd1.config.ConfigManager;
import io.gitee.mc_shd1.utils.CommandManager;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class hereCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> here = literal("here")
                .executes((c) -> here(c.getSource(), c.getSource().getPlayer()))
                .then(argument("玩家", EntityArgumentType.player())
                    .requires((player) -> CommandManager.checkPermission(player, "ops"))
                    .executes((c) -> here(c.getSource(), EntityArgumentType.getPlayer(c, "玩家"))));

        dispatcher.register(here);
    }

    public static int here(ServerCommandSource source, ServerPlayerEntity player) {
        Identifier dimensionId = DimensionType.getId(player.getServerWorld().getDimension().getType());
        String dimensionName = ConfigManager.Messages.General.dimensions.get(dimensionId.getPath());
        if (dimensionName == null) {
            dimensionName = dimensionId.getPath();
        }
        BlockPos blockPos = new BlockPos(player);

        player.getServer().getPlayerManager().broadcastChatMessage(Text.Serializer.fromJson(ConfigManager.Messages.Commands.Here.TargetMessage
                .replaceAll("%player_name%", player.getDisplayName().getString())
                .replaceAll("%player_dimension%", dimensionName)
                .replaceAll("%player_x%", String.valueOf(blockPos.getX()))
                .replaceAll("%player_y%", String.valueOf(blockPos.getY()))
                .replaceAll("%player_z%", String.valueOf(blockPos.getZ()))), true);
        if (ConfigManager.Config.Commands.Here.Glowing_Time > 0) {
            player.addPotionEffect(new StatusEffectInstance(StatusEffects.GLOWING, ConfigManager.Config.Commands.Here.Glowing_Time * 20, 0, false, false));
            player.addChatMessage(Text.Serializer.fromJson(ConfigManager.Messages.Commands.Here.FeedbackMessage
                    .replaceAll("%glowing_time%", String.valueOf(ConfigManager.Config.Commands.Here.Glowing_Time))), true);
        }
        return 1;
    }
}
