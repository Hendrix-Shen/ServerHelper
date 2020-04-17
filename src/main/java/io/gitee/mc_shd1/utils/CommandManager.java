package io.gitee.mc_shd1.utils;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class CommandManager {
    public static boolean checkPermission(ServerCommandSource source, String commandLevel) {
        switch (commandLevel) {
            case "true":
                return true;
            case "false":
                return false;
            case "ops":
                return source.hasPermissionLevel(2); // typical for other cheaty commands
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
                return source.hasPermissionLevel(Integer.parseInt(commandLevel));
        }
        return false;
    }
    public static CompletableFuture<Suggestions> getSuggestionsBuilder(SuggestionsBuilder builder, List<String> list) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);

        if(list.isEmpty()) { // If the list is empty then return no suggestions
            return Suggestions.empty(); // No suggestions
        }

        for (String str : list) { // Iterate through the supplied list
            if (str.toLowerCase(Locale.ROOT).startsWith(remaining)) {
                builder.suggest(str); // Add every single entry to suggestions list.
            }
        }
        return builder.buildFuture(); // Create the CompletableFuture containing all the suggestions
    }
    public static SuggestionProvider<ServerCommandSource> suggestClassification(){
        List<String> suggestList= Lists.newArrayList("custom", "crafted", "used", "broken", "mined", "killed", "picked_up", "dropped", "killed_by");
        return (context, builder) -> getSuggestionsBuilder(builder, suggestList);
    }

    public static Collection<String> getPlayers(ServerCommandSource source){
        List<String> suggestList = Lists.newArrayList();
        suggestList.addAll(source.getPlayerNames());
        return suggestList;
    }

    private static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context)
    {
        String playerName = StringArgumentType.getString(context, "player");
        MinecraftServer server = context.getSource().getMinecraftServer();
        return server.getPlayerManager().getPlayer(playerName);
    }
}
