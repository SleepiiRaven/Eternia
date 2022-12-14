package com.eternia.listeners;

import com.eternia.Eternia;
import com.eternia.guis.FactionGUI;
import com.eternia.player.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIListener implements Listener {
    public GUIListener(Eternia plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        switch (event.getView().getTitle()) {
            case FactionGUI.invName:
                FactionGUI.clickedGUI(event);
                break;
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        switch (event.getView().getTitle()) {
            case FactionGUI.invName:
                if (PlayerStats.getStats((Player) event.getPlayer(), event.getPlayer().getUniqueId()).faction != null) break;
                FactionGUI.openGUI((Player) event.getPlayer());
        }
    }
}
