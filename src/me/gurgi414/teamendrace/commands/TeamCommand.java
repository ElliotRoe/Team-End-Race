package me.gurgi414.teamendrace.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gurgi414.teamendrace.Main;
import me.gurgi414.teamendrace.objects.TrackablePlayer;

public class TeamCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	private Server server;

	private boolean areTeamsSet = false;

	private final ArrayList<String> TEAMS = new ArrayList<>(Arrays.asList("blue", "red", "green", "yellow"));
	private final ChatColor[] TEAMCOLORS = { ChatColor.BLUE, ChatColor.RED, ChatColor.GREEN, ChatColor.YELLOW };

	public TeamCommand(Main plugin) {
		this.plugin = plugin;
		this.server = Bukkit.getServer();
		plugin.getCommand("team").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use the command");
			return false;
		}

		Player p = (Player) sender;

		if (!(p.isOp())) {
			p.sendMessage("Only the server admin can set teams *stupid bitch*");
			return false;
		}

		if (args.length == 0) {
			p.sendMessage("Please input argss");
		}
		if (args[0].equalsIgnoreCase("setTeamNumber")) {
			try {
				if (Integer.parseInt(args[1]) < 2) {
					p.sendMessage("You have to have more than one team");
					return false;
				} else if (Integer.parseInt(args[1]) > 4) {
					p.sendMessage("You can only have four teams max");
					return false;
				}
			} catch (Exception e) {
				p.sendMessage("You must input a integer");
			}
			for (int i = 0; i < Integer.parseInt(args[1]); i++) {
				Main.teamList.add(new ArrayList<TrackablePlayer>());
				// 1.Blue 2.Red 3. Green 4. Yellow

			}
			areTeamsSet = true;
			return true;
		}

		if (areTeamsSet)
			switch (args[0].toLowerCase()) {
			case "add":
				// try {
				int tempIndex = TEAMS.indexOf(args[2]);
				Main.teamList.get(tempIndex)
						.add(new TrackablePlayer(server.getPlayer(args[1]), tempIndex, this.plugin));
				server.broadcastMessage(args[1] + " has been added to the " + args[2] + " team");

				/*
				 * } catch (Exception e) { p.sendMessage("Not a valid team. Try again."); }
				 */

				break;
			case "remove":
				try {
					Main.teamList.get(TEAMS.indexOf(args[2])).remove(Main.getTrackable(server.getPlayer(args[1])));
					server.broadcastMessage(args[1] + " has been added to the " + args[2] + " team");

				} catch (Exception e) {
					p.sendMessage(
							"You either tried to remove a non-exisitent player, or picked the wrong team try again");
				}
				break;
			case "list":
				int i = 0;
				for (ArrayList<TrackablePlayer> team : Main.teamList) {
					server.broadcastMessage(
							ChatColor.BOLD + "" + TEAMCOLORS[i] + TEAMS.get(i) + " Team:" + ChatColor.RESET);
					for (TrackablePlayer tp : team) {

						server.broadcastMessage(tp.getPlayer().getName());

					}
					i++;
					if (i < Main.teamList.size()) {
						server.broadcastMessage("----------");
					}
				}
				break;
			}
		else {
			p.sendMessage("Please set the number teams you would like before adding or removing players");
		}
		return false;
	}

}
