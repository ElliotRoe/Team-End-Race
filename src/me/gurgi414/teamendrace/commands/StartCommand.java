package me.gurgi414.teamendrace.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.gurgi414.teamendrace.Main;
import me.gurgi414.teamendrace.objects.TrackablePlayer;
import me.gurgi414.teamendrace.tasks.TrackTask;

public class StartCommand implements CommandExecutor {

	private Main plugin;

	private final int refreshRate = 1200;

	public StartCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("startrace").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use the command");
			return false;
		}

		Player player = (Player) sender;

		if (!(player.isOp())) {
			player.sendMessage("Only the server admin can start");
			return false;
		}

		for (ArrayList<TrackablePlayer> team : Main.teamList) {
			if (team.size() == 0) {
				player.sendMessage("You can only start the game when teams are fully set");
				return false;
			}
		}

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			ItemStack compass = new ItemStack(Material.COMPASS, 1);
			Inventory playerInven = p.getInventory();
			if (!(playerInven.contains(compass))) {
				playerInven.addItem(compass);
			}
		}

		TrackTask trackTask = new TrackTask();

		trackTask.runTaskTimer(plugin, 0, this.refreshRate);

		Bukkit.getServer().broadcastMessage("Players can now track with their compasses");

		return true;
	}
}
