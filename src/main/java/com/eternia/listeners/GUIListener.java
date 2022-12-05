package com.eternia.listeners;

import com.eternia.guis.FactionGUI;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener {
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        switch(event.getView().title().toString()) {
            case FactionGUI.invName:
                FactionGUI factionGUI = new FactionGUI();
        }
    }
}
