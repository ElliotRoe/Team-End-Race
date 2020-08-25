package me.gurgi414.teamendrace.listeners;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.gurgi414.teamendrace.Main;
import me.gurgi414.teamendrace.objects.TrackablePlayer;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Main.debugMessage("Player respawned");
		TrackablePlayer temp = Main.getTrackable(e.getPlayer());
		temp.setInNether(false);

		int i = -1;
		for (ArrayList<TrackablePlayer> team : Main.teamList) {
			i++;
			if (i != temp.getTeamNum()) {
				for (TrackablePlayer tp : team) {
					tp.onPlayerRespawn(temp);
				}
			}
		}
	}

}
