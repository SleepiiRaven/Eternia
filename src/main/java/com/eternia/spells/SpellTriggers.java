package com.eternia.spells;

import com.eternia.CooldownManager;
import com.eternia.Eternia;
import com.eternia.utils.SpellUtils;
import java.lang.reflect.InvocationTargetException;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpellTriggers {
    public Eternia plugin = Eternia.getInstance();
    public CooldownManager cooldownManager = plugin.getCdInstance();
    public Player player;
    public SpellTriggers(Player player) {
        this.player = player;
    }
    //region Normal Spell
    public String currentMessage = ChatColor.GREEN.toString() + ChatColor.UNDERLINE +  "R" + ChatColor.RESET + ChatColor.RED + " _ _";
    public boolean spellMode = false;
    public int clicksSoFar = 0;
    public boolean[] spellClicks = new boolean[2]; //left = false, right = true.
    public void enterSpellMode() {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5f, 1f);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(currentMessage));
        spellMode = true;
    }
    public void continueNormalSpell(Action clickType) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5f, 0.8f);
        boolean click = false;
        if (clickType.equals(Action.RIGHT_CLICK_AIR) || clickType.equals(Action.RIGHT_CLICK_BLOCK)) {
            click = true;
        }
        if (clicksSoFar == 0) {
            currentMessage = (click) ? /*IF WE ARE RIGHT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE +  "R R" + ChatColor.RESET + ChatColor.RED + " _":
                    /*IF WE ARE LEFT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE +  "R L" + ChatColor.RESET + ChatColor.RED + " _";
        } else {
            // If our first click was a left click
            if (!spellClicks[0]) {
                currentMessage = (click) ? /*IF WE ARE RIGHT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R L R" :
                        /*IF WE ARE LEFT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R L L";
            } else {
                currentMessage = (click) ? /*IF WE ARE RIGHT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE +  "R R R":
                        /*IF WE ARE LEFT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE +  "R R L";
            }
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(currentMessage));
        if (clicksSoFar < 2) {
            spellClicks[clicksSoFar] = click;
        }
        clicksSoFar += 1;
        if (clicksSoFar == 2) {
            finishSpell();
            spellMode = false;
            clicksSoFar = 0;
            spellClicks = new boolean[2];
            currentMessage = ChatColor.GREEN.toString() + ChatColor.UNDERLINE +  "R" + ChatColor.RESET + ChatColor.RED + " _ _";
        }
    }
    public void finishSpell() {
        boolean firstClick = spellClicks[0];
        boolean secondClick = spellClicks[1];

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "Casted!"));

        // If the notation is LEFT-RIGHT
        if (!firstClick && secondClick) {
            long cooldown = 1000;

            // Base Spell
            if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Base Spell")) {
                player.sendMessage(ChatColor.RED + "That ability is currently on cooldown.");
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.5f);
                return;
            }
            SpellUtils.redirect(player, player.getUniqueId(), SpellType.BASE);
            cooldownManager.setCooldownFromNow(player.getUniqueId(), "Base Spell", cooldown);
        }

        // If the notation is RIGHT-LEFT
        if (firstClick && !secondClick) {
            long cooldown = 8000;

            // Utility Spell
            if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Utility Spell")) {
                player.sendMessage(ChatColor.RED + "That ability is currently on cooldown.");
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.5f);
                return;
            }
            SpellUtils.redirect(player, player.getUniqueId(), SpellType.UTIL);
            cooldownManager.setCooldownFromNow(player.getUniqueId(), "Utility Spell", cooldown);
        }

        // If the notation is LEFT-LEFT
        if (!firstClick && !secondClick) {
            long cooldown = 3000;

            // Heavy Spell
            if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Heavy Spell")) {
                player.sendMessage(ChatColor.RED + "That ability is currently on cooldown.");
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.5f);
                return;
            }
            SpellUtils.redirect(player, player.getUniqueId(), SpellType.HEAVY);
            cooldownManager.setCooldownFromNow(player.getUniqueId(), "Heavy Spell", cooldown);
        }

        // If the notation is RIGHT-RIGHT
        if (firstClick && secondClick) {
            long cooldown = 3000;

            // Movement Spell
            if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Movement Spell")) {
                player.sendMessage(ChatColor.RED + "That ability is currently on cooldown.");
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.5f);
                return;
            }

            boolean hasDisableEffect = false;
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.SLOW_FALLING)) {
                    hasDisableEffect = true;
                }
            }

            if (hasDisableEffect) return;

            SpellUtils.redirect(player, player.getUniqueId(), SpellType.MOVEMENT);
            cooldownManager.setCooldownFromNow(player.getUniqueId(), "Movement Spell", cooldown);
        }
    }
    //endregion
    //region Weapon Spell
    //endregion
    //region Ultimate Spell

    //endregion
}
