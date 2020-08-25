package me.gurgi414.teamendrace.objects;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.gurgi414.teamendrace.Main;

public class TrackablePlayer {

	private Player player;
	private Player target;

	private Location validLocation;

	private boolean inNether = false;

	private int teamNum;

	private Server server;

	private Plugin plugin;

	public TrackablePlayer(Player player, int team, Plugin plugin) {
		this.player = player;
		this.teamNum = team;
		this.plugin = plugin;
		server = Bukkit.getServer();
		target = null;
	}

	public void onNetherPortalEnter(TrackablePlayer temp) {
		if (temp.getPlayer().getName().equals(target.getName())) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
				@Override
				public void run() {
					trackNearest();
				}
			}, 25);
			Main.debugMessage("Refreshing target because previous target went through portal to nether");
		}
	}

	public void onNetherPortalEnterOverworld(TrackablePlayer temp) {
		if (temp.getPlayer().getName().equals(target.getName())) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
				@Override
				public void run() {
					trackNearest();
				}
			}, 25);
			Main.debugMessage("Refreshing target because previous target went through portal to overworld");
		}
	}

	public void onPlayerRespawn(TrackablePlayer temp) {
		if (temp.getPlayer().getName().equals(target.getName())) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
				@Override
				public void run() {
					trackNearest();
				}
			}, 25);
			Main.debugMessage("Refreshing target because previous target died");
		}
	}

	public Player trackNearest() {
		if (!(this.isInNether())) {
			TrackablePlayer temp = findNearestEnemyLocation();
			if (temp.isInNether()) {
				Main.debugMessage("Now tracking " + temp.getPlayer().getName() + "'s last posistion");
				compassCommand("reset");
				compassCommand("pos " + temp.getValidLocation().getBlockX() + " " + temp.getValidLocation().getBlockY()
						+ " " + temp.getValidLocation().getBlockZ());
			} else {
				compassCommand("reset");
				compassCommand("follow " + temp.getPlayer().getName());
				Main.debugMessage("Now tracking " + temp.getPlayer().getName());
			}

			target = temp.getPlayer();
		}

		return target;
	}

	private TrackablePlayer findNearestEnemyLocation() {
		int i = -1;
		double shortest = Double.MAX_VALUE;
		double dist = 0;
		TrackablePlayer nearest = null;

		for (ArrayList<TrackablePlayer> team : Main.teamList) {
			i++;
			if (teamNum == i)
				continue;
			for (TrackablePlayer trackable : team) {

				dist = trackable.getValidLocation().distance(this.getValidLocation());
				if (dist < shortest) {
					shortest = dist;
					nearest = trackable;
				}
			}
		}

		return nearest;
	}

	private void compassCommand(String cmd) {
		if (this.player.isOp()) {
			server.dispatchCommand(this.player, "compass " + cmd);
		} else {
			this.player.setOp(true);
			server.dispatchCommand(this.player, "compass " + cmd);
			this.player.setOp(false);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public Player getTarget() {
		return target;
	}

	public void setTarget(Player target) {
		this.target = target;
	}

	public Location getValidLocation() {
		if (!this.isInNether())
			validLocation = this.player.getLocation();
		// x,y,z format
		return validLocation;
	}

	public void setValidLocation(Location validLocation) {
		if (validLocation.getWorld().getName().endsWith("_nether"))
			Main.debugMessage("Warning! Tried setting invalid location");

		this.validLocation = validLocation;
	}

	public boolean isInNether() {
		return this.player.getLocation().getWorld().getName().endsWith("_nether");
	}

	public void setInNether(boolean inNether) {
		this.inNether = inNether;
	}

	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

}
