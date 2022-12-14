package com.eternia.spells.lightseekers.elves.heavy;

import com.destroystokyo.paper.ParticleBuilder;
import com.eternia.CooldownManager;
import com.eternia.Eternia;
import com.eternia.spells.Spell;
import com.eternia.utils.FastUtils;
import com.eternia.utils.ParticleUtils;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class ArrowRain extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        Location location = player.getEyeLocation().add(0, 10, 0);
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                List<Arrow> arrows = new ArrayList<>();
                ParticleBuilder particleBuilder = new ParticleBuilder(Particle.CLOUD);
                particleBuilder.count(20);
                particleBuilder.extra(0);
                particleBuilder.location(location);
                ParticleUtils.filledCircle(particleBuilder, 10);
                int partialCount = 10;
                int revolutionDegrees = 360;
                double currentDegrees = 0;

                for (int i = 0; i < partialCount; i++) {
                    // Always rerandomise rotation
                    currentDegrees = FastUtils.randomDoubleInRange(0, revolutionDegrees);

                    double offsetX = FastUtils.sinDeg(currentDegrees) * 10;
                    double offsetZ = FastUtils.cosDeg(currentDegrees) * 10;
                    // Randomly move inwards
                    double inwardFactor = Math.sqrt(FastUtils.RANDOM.nextDouble());
                    offsetX *= inwardFactor;
                    offsetZ *= inwardFactor;

                    Location currentLocation = location.clone();
                    currentLocation.add(offsetX, 0, offsetZ);
                    arrows.add(player.getWorld().spawnArrow(currentLocation, new Vector(0, -1, 0), 1, 1));
                }
                for (Arrow arrow : arrows) {
                    arrow.addScoreboardTag(player.getUniqueId().toString());
                    arrow.addScoreboardTag("arrowRainArrow");
                    new BukkitRunnable() {
                        public void run() {
                            arrow.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 1, new Particle.DustOptions(Color.AQUA, 1));
                            if (arrow.isDead() || arrow.isOnGround()) {
                                arrow.remove();
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Eternia.getInstance(), 0L, 1L);
                }
                ticks += 1;
                if (ticks >= 60) this.cancel();
            }
        }.runTaskTimer(Eternia.getInstance(), 0L, 1L);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.5f, 1.2f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.5f, 0.8f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1f, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_3, 0.4f, 1.1f);
    }
}
