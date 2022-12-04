package com.eternia.spells.lightseekers.elves.base;

import com.eternia.CooldownManager;
import com.eternia.spells.Spell;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class LightSpear extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        double range = 3;
        double distance = 30;
        double collision = 1;
        double damage = 5;
        double kb = ThreadLocalRandom.current().nextDouble(0.3, 0.5);
        Location viewPos = p.getEyeLocation();
        Vector viewDir = viewPos.getDirection();
        for (double t = 0; t < distance; t += 0.5) {
            double x = viewDir.getX() * t;
            double y = viewDir.getY() * t;
            double z = viewDir.getZ() * t;
            viewPos.add(x, y, z);
            p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, viewPos, 1, 0, 0, 0);
            p.getWorld().spawnParticle(Particle.REDSTONE, viewPos, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(252, 88, 241), 2));
            p.getWorld().spawnParticle(Particle.REDSTONE, viewPos, 1, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(132, 3, 188), 2));
            p.getWorld().spawnParticle(Particle.END_ROD, viewPos, 1, 0, 0, 0);
            Collection<Entity> closebyMonsters = p.getWorld().getNearbyEntities(viewPos, range, range, range);
            for (Entity closebyMonster : closebyMonsters) {
                // make sure it's a living entity, not an armor stand or something, continue skips the current loop
                if (!(closebyMonster instanceof LivingEntity) || (closebyMonster == p)) continue;
                LivingEntity livingMonster = (LivingEntity) closebyMonster;
                // Get the entity's collision box
                BoundingBox monsterBoundingBox = livingMonster.getBoundingBox();
                BoundingBox collisionBox = BoundingBox.of(viewPos, collision, collision, collision);
                if (!(monsterBoundingBox.overlaps(collisionBox))) continue;
                livingMonster.damage(damage, p);
                Vector viewNormalized = (viewDir.normalize()).multiply(kb);
                livingMonster.setVelocity(viewNormalized);
            }
            viewPos.subtract(x, y, z);
        }
        p.playSound(p, Sound.ITEM_CROSSBOW_SHOOT, 1.0f, 0.5f);
    }
}
