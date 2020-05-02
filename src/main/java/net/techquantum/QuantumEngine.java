package net.techquantum;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.techquantum.commands.PingCommand;
import net.techquantum.commands.TickReportCommand;

public class QuantumEngine{
	public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher){
		PingCommand.register(dispatcher);
		TickReportCommand.register(dispatcher);
	}
}
