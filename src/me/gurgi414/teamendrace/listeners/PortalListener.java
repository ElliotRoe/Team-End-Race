package me.gurgi414.teamendrace.listeners;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import me.gurgi414.teamendrace.Main;
import me.gurgi414.teamendrace.objects.TrackablePlayer;

public class PortalListener implements Listener {

	@EventHandler
	public void onNetherPortalEnter(PlayerPortalEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();
		TrackablePlayer temp = Main.getTrackable(e.getPlayer());
		if (e.getCause().name().equals("NETHER_PORTAL")) {
			if (e.getFrom().getWorld().getName().endsWith("_nether")) {
				temp.setInNether(false);
				Main.debugMessage(e.getPlayer().getName() + " is now out of the nether");
				int i = -1;
				for (ArrayList<TrackablePlayer> team : Main.teamList) {
					i++;
					if (i != temp.getTeamNum()) {
						for (TrackablePlayer tp : team) {
							tp.onNetherPortalEnterOverworld(temp);
						}
					}
				}
			} else {
				temp.setInNether(true);
				temp.setValidLocation(from);
				Main.debugMessage(e.getPlayer().getName() + " is now in the nether last location: " + from.getX() + ":"
						+ from.getY() + ":" + from.getZ());
				int i = -1;
				for (ArrayList<TrackablePlayer> team : Main.teamList) {
					i++;
					if (i != temp.getTeamNum()) {
						for (TrackablePlayer tp : team) {
							tp.onNetherPortalEnter(temp);
						}
					}
				}
			}

		}

	}

}
