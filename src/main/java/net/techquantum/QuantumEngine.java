package net.techquantum;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.techquantum.commands.PingCommand;

public class QuantumEngine{

	public static MinecraftServer minecraftServer;

	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher){
		PingCommand.register(dispatcher);
	}

	public static void onServerLoaded(MinecraftServer server) {
		QuantumEngine.minecraftServer = server;
	}

	public static void onServerClosed(MinecraftServer server) {
		minecraftServer = null;
	}

}
