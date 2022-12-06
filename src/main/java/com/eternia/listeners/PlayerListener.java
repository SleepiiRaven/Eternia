package com.eternia.listeners;

import com.eternia.CooldownManager;
import com.eternia.Eternia;
import com.eternia.player.PlayerStats;
import com.eternia.utils.ItemUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
    private final Eternia plugin = Eternia.getInstance();
    CooldownManager cooldownManager = plugin.getCdInstance();
    public PlayerListener(Eternia plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        UUID pUUID = event.getPlayer().getUniqueId();
        CooldownManager cooldownManager = plugin.getCdInstance();
        cooldownManager.createContainer(pUUID);
        //PlayerStats stats = PlayerStats.getStats(event.getPlayer());
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Entity arrow = event.getProjectile();
        Player player = (Player) event.getEntity();

        if (!(player.getScoreboardTags().contains("homing"))) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isOnGround() || arrow.isDead()) this.cancel();

                List<Entity> nearest = arrow.getNearbyEntities(10, 10, 10);
                Entity target = null;
                for (Entity near : nearest) {
                    if (near != player && near instanceof LivingEntity && player.hasLineOfSight(near)) {
                        if (target == null) {
                            target = near;
                        } else if (arrow.getLocation().distanceSquared(near.getLocation()) < arrow.getLocation().distanceSquared(target.getLocation())) {
                            target = near;
                        }
                    }
                }
                if (target == null) return;
                arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector()));
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @EventHandler
    public void playerLeftClick(PlayerAnimationEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Player player = event.getPlayer();
        // PlayerInteractEvent doesn't work with LEFT_CLICK_BLOCK in adventure mode, so using this for that.
        if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Spell Click") || event.getAnimationType() != PlayerAnimationType.ARM_SWING || !ItemUtils.weapons.contains(player.getInventory().getItemInMainHand().getType())) return;
        long cooldown = 10;
        PlayerStats playerStats = PlayerStats.getStats(player, player.getUniqueId());
        cooldownManager.setCooldownFromNow(player.getUniqueId(), "Spell Click", cooldown);
        if (playerStats.spellTriggers.spellMode) {
            playerStats.spellTriggers.continueNormalSpell(Action.LEFT_CLICK_AIR);
        }
    }

    @EventHandler
    public void playerHit(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        switch (event.getCause()) {
            case FALL:
                event.setDamage(event.getDamage() * 0.8);
                break;
        }
    }

    @EventHandler
    public void playerHitByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getDamager().getScoreboardTags().contains("arrowRainArrow")) {
            event.setDamage(5);
        }

        if (!(event.getEntity() instanceof Player)) return;
        if (event.getDamager() instanceof Arrow && event.getDamager().getScoreboardTags().contains(event.getEntity().getUniqueId().toString())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerRightClick(PlayerInteractEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Spell Click") || action == Action.LEFT_CLICK_AIR || !ItemUtils.weapons.contains(player.getInventory().getItemInMainHand().getType())) return;
        long cooldown = (10);
        PlayerStats playerStats = PlayerStats.getStats(player, player.getUniqueId());

        cooldownManager.setCooldownFromNow(player.getUniqueId(), "Spell Click", cooldown);

        if (playerStats.spellTriggers.spellMode) {
            playerStats.spellTriggers.continueNormalSpell(action);
            return;
        }

        playerStats.spellTriggers.enterSpellMode();
    }
}
