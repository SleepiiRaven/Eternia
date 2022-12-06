package com.eternia.spells.lightseekers.elves.util;

import com.eternia.CooldownManager;
import com.eternia.Eternia;
import com.eternia.spells.Spell;
import com.eternia.utils.JsonUtils;
import com.eternia.utils.ParticleUtils;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import net.minestom.server.coordinate.Vec;
//import net.minestom.server.particle.Particle;
import net.worldseed.particleemitter.runtime.ParticleEmitter;
import net.worldseed.particleemitter.runtime.ParticleParser;
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
        }.runTaskTimer(Eternia.getInstance(), 0L, 10L);
        /**try {
            ParticleEmitter emitter = ParticleParser.parse(Particle.DUST, 1000, JsonUtils.jsonObjects.get("Eye of Light"));
            emitter.setPosition(new Vec(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
            emitter.tick();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
         **/
        new BukkitRunnable() {
            @Override
            public void run() {
                player.removeScoreboardTag("homing");
                particleEffect.cancel();
            }
        }.runTaskLater(Eternia.getInstance(), 100L);
    }
}
