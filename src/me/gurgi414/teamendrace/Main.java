package me.gurgi414.teamendrace;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.gurgi414.teamendrace.commands.StartCommand;
import me.gurgi414.teamendrace.commands.TeamCommand;
import me.gurgi414.teamendrace.listeners.PlayerRespawnListener;
import me.gurgi414.teamendrace.listeners.PortalListener;
import me.gurgi414.teamendrace.objects.TrackablePlayer;

public class Main extends JavaPlugin {

	public static ArrayList<ArrayList<TrackablePlayer>> teamList = new ArrayList<ArrayList<TrackablePlayer>>();

	public static boolean debug = true;

	@Override
	public void onEnable() {
		new TeamCommand(this);
		new StartCommand(this);
		getServer().getPluginManager().registerEvents(new PortalListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
	}

	public static TrackablePlayer getTrackable(Player player) {
		for (ArrayList<TrackablePlayer> team : teamList) {
			for (TrackablePlayer tp : team) {
				if (tp.getPlayer().getName().equals(player.getName())) {
					return tp;
				}
			}
		}
		return null;
	}

	public static void debugMessage(String message) {
		Bukkit.getServer().broadcastMessage(ChatColor.ITALIC + "Debug: " + message);
	}

}
