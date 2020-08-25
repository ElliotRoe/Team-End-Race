package me.gurgi414.teamendrace.tasks;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import me.gurgi414.teamendrace.Main;
import me.gurgi414.teamendrace.objects.TrackablePlayer;

public class TrackTask extends BukkitRunnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (ArrayList<TrackablePlayer> team : Main.teamList) {
			for (TrackablePlayer tp : team) {

				tp.trackNearest();

			}
		}
		Main.debugMessage("Compass tracking is now refreshed");
	}

}
