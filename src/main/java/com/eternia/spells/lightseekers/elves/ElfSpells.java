package com.eternia.spells.lightseekers.elves;

import com.eternia.spells.Spell;
import com.eternia.spells.lightseekers.elves.base.LightSpear;
import com.eternia.spells.lightseekers.elves.heavy.ArrowRain;
import com.eternia.spells.lightseekers.elves.movement.LeapofFate;
import com.eternia.spells.lightseekers.elves.util.EyeofLight;

public class ElfSpells {
    public static Spell LIGHT_SPEAR = new LightSpear();
    public static Spell EYE_OF_LIGHT = new EyeofLight();
    public static Spell LEAP_OF_FATE = new LeapofFate();
    public static Spell ARROW_RAIN = new ArrowRain();
}
