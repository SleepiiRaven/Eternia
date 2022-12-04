package com.eternia.spells.lightseekers.elves.util;

import com.eternia.CooldownManager;
import com.eternia.Plugin;
import com.eternia.spells.Spell;
import com.eternia.utils.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EyeofLight extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        player.addScoreboardTag("homing");
        BukkitTask particleEffect = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = player.getLocation();
                ParticleUtils.summonCircle(location, 2, Particle.TOTEM);
            }
        }.runTaskTimer(Plugin.getInstance(), 0L, 10L);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.removeScoreboardTag("homing");
                particleEffect.cancel();
            }
        }.runTaskLater(Plugin.getInstance(), 100L);
    }
}
