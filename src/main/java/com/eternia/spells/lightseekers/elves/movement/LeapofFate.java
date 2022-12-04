package com.eternia.spells.lightseekers.elves.movement;

import com.eternia.CooldownManager;
import com.eternia.Plugin;
import com.eternia.spells.Spell;
import com.eternia.utils.BlockUtils;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class LeapofFate extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 20);
        double radius = 10;
        p.setVelocity(new Vector(0D, 2, 0D));

        for (Entity nearbyEntity : p.getNearbyEntities(radius, radius, radius)) {
            if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity instanceof Player) continue;
            ((LivingEntity) nearbyEntity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 1, new Particle.DustOptions(Color.WHITE, 2));
                p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, p.getLocation(), 10);

                if (p.isOnGround() || BlockUtils.isWaterSource(p.getLocation().getBlock().getState())) {
                    this.cancel();
                }
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 10, 1));
            }
        }.runTaskTimer(Plugin.getInstance(), 20L, 5L);
    }
}