package com.eternia.spells.lightseekers.elves.heavy;

import com.destroystokyo.paper.ParticleBuilder;
import com.eternia.CooldownManager;
import com.eternia.spells.Spell;
import com.eternia.utils.ParticleUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ArrowRain extends Spell {
    @Override
    public void cast(Player player, CooldownManager cooldownManager) {
        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.CAMPFIRE_COSY_SMOKE);
        particleBuilder.count(90);
        particleBuilder.location(player.getEyeLocation().add(0, 3, 0));
        ParticleUtils.filledCircle(particleBuilder, 0);
    }
}
