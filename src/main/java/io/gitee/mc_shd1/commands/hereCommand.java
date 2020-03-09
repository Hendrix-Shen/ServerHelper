package io.gitee.mc_shd1.commands;

import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import io.gitee.mc_shd1.Core;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
public class hereCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        LiteralArgumentBuilder<ServerCommandSource> here = literal("here").
                executes((c) -> here(c.getSource(), c.getSource().getPlayer())).
                then(argument("player", EntityArgumentType.player()).
                        executes( (c) -> here(c.getSource(), EntityArgumentType.getPlayer(c, "player"))));

        dispatcher.register(here);
    }

    private static int here(ServerCommandSource source, ServerPlayerEntity player)
    {
    	Identifier dimensionId = DimensionType.getId(player.getServerWorld().getDimension().getType());
    	String dimensionName = Core.getMessage().command_here_dimensions.get(dimensionId.getPath());
        if (dimensionName == null) {
            dimensionName = dimensionId.getPath();
        }
        BlockPos blockPos = new BlockPos(player);
        player.getServer().getPlayerManager().broadcastChatMessage(
        		new TranslatableText(Core.getMessage().command_here_broadcast_message, player.getDisplayName(), dimensionName,
                blockPos.getX(), blockPos.getY(), blockPos.getZ()), true);
        if(Core.getConfig().command_here_glowing_time > 0) {
            //player.addPotionEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 1, false, false));
        	player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, Core.getConfig().command_here_glowing_time * 20, 1, false,false));
            player.addMessage(new TranslatableText(Core.getMessage().command_here_glowing_message.replace("&", "§"), Core.getConfig().command_here_glowing_time), true);
        	
        }
        //player.server.getPlayerManager().sendToAll(new LiteralText("").append("玩家 §d" + player.getEntityName().toString() + " §f在 " + Core.getMessage().dimensions_name.get(player.dimension.getId(player.getServerWorld().dimension.getType())) + "§f@§b[X:"+ (int)player.prevX + ", Y:" + (int)player.prevY + ", Z:" + (int)player.prevZ + "] §f向大家招手!"));
        return 1;
    }
}
