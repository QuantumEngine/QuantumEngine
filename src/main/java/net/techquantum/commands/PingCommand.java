package net.techquantum.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.literal;

public class PingCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        LiteralArgumentBuilder<ServerCommandSource> command = literal("ping")
                .executes(c ->
                {
                    ServerPlayerEntity playerEntity = c.getSource().getPlayer();
                    int ping = playerEntity.pingMilliseconds;
                    playerEntity.sendSystemMessage(new LiteralText(ping + "ms"));
                    return 1;
                });
        dispatcher.register(command);
    }
}
