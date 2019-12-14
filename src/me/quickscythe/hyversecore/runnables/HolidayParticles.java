package me.quickscythe.hyversecore.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import me.quickscythe.hyversecore.utils.CoreUtils;
import me.quickscythe.hyversecore.utils.Holiday;

public class HolidayParticles implements Runnable {

	@Override
	public void run() {
		if (CoreUtils.getHoliday().equals(Holiday.NONE))
			return;
		switch (CoreUtils.getHoliday()) {
		case VALENTINES:
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (CoreUtils.playerparticles.get(player.getUniqueId()))
					player.spawnParticle(Particle.REDSTONE,
							player.getLocation().add(-0.5 + CoreUtils.getRandom().nextDouble(),
									(1.5 + CoreUtils.getRandom().nextDouble())
											- (CoreUtils.getRandom().nextInt(2) + CoreUtils.getRandom().nextDouble()),
									-0.5 + CoreUtils.getRandom().nextDouble()),
							0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB((int) 255, (int) 0, (int) 0), 1));
			}

			break;
		case CHRISTMAS:
			for (Player player : Bukkit.getOnlinePlayers()) {
				try{if (!CoreUtils.playerparticles.get(player.getUniqueId())) continue;}catch(NullPointerException ex) { continue; }
				if (CoreUtils.getRandom().nextBoolean())
					player.spawnParticle(Particle.REDSTONE,
							player.getLocation().add(-0.5 + CoreUtils.getRandom().nextDouble(),
									(1.5 + CoreUtils.getRandom().nextDouble())
											- (CoreUtils.getRandom().nextInt(2) + CoreUtils.getRandom().nextDouble()),
									-0.5 + CoreUtils.getRandom().nextDouble()),
							0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB((int) 255, (int) 0, (int) 0), 1));
				else
					player.spawnParticle(Particle.REDSTONE,
							player.getLocation().add(-0.5 + CoreUtils.getRandom().nextDouble(),
									(1.5 + CoreUtils.getRandom().nextDouble())
											- (CoreUtils.getRandom().nextInt(2) + CoreUtils.getRandom().nextDouble()),
									-0.5 + CoreUtils.getRandom().nextDouble()),
							0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB((int) 0, (int) 255, (int) 0), 1));
			}

			break;
		default:
			break;
		}

	}

}
