package com.eternia.player;

import com.eternia.spells.Spell;
import com.eternia.spells.SpellTriggers;
import com.eternia.spells.lightseekers.elves.ElfSpells;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class PlayerStats {
    public static HashMap<UUID, PlayerStats> playerStats = new HashMap<>();
    public Player player;
    public SpellTriggers spellTriggers;
    public FactionType faction;
    public RaceType race;
    public Spell[] spells = {
            ElfSpells.LIGHT_SPEAR,
            ElfSpells.EYE_OF_LIGHT,
            ElfSpells.ARROW_RAIN,
            ElfSpells.LEAP_OF_FATE
    };

    public PlayerStats(Player player, SpellTriggers spellTriggers, FactionType faction, RaceType race) {
        this.player = player;
        this.spellTriggers = spellTriggers;
        this.faction = faction;
        this.race = race;
    }
    
    public static PlayerStats getStats(Player player, UUID pUUID) {
        if (playerStats.containsKey(pUUID)) {
            return playerStats.get(pUUID);
        } else {
            return createStats(player, pUUID);
        }
    }
    
    public static PlayerStats createStats(Player player, UUID pUUID) {
        PlayerStats stats = new PlayerStats(player, new SpellTriggers(player), FactionType.LIGHTSEEKER, RaceType.ANGEL);
        playerStats.put(pUUID, stats);
        return stats;
    }
}
