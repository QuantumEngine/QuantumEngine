package net.techquantum.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class TickReportCommand {

    private static boolean shouldReport;
    private static PlayerManager PM;
    private static long timestamp;
    private static String prevPhase;


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("report").executes(cmd -> f0(cmd.getSource())));
    }


    /* report */
    private static int f0(ServerCommandSource src) throws CommandSyntaxException {
        shouldReport = true;
        PM = src.getMinecraftServer().getPlayerManager();
        return 1;
    }


    public static void startTick(int ticks) {
        if (!shouldReport || PM == null) return;

        PM.sendToAll(new LiteralText(String.format("§7== §rTICK REPORT §a%06d§7 ===============", ticks)));

        timestamp = System.nanoTime();
    }

    public static void setPhase(String phase) {
        if (!shouldReport || PM == null) return;

        float duration = (System.nanoTime() - timestamp) / 1000000F;

        if (prevPhase != null) PM.sendToAll(new LiteralText(String.format("§7- %s: %.02fms", prevPhase, duration)));
        prevPhase = phase;

        timestamp = System.nanoTime();
    }

    public static void endTick() {
        shouldReport = false;
        PM = null;
    }

}
