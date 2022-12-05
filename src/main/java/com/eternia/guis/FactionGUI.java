package com.eternia.guis;


import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FactionGUI {
    public static final String invName = "Faction Selector"

    public static void openGUI() {
        Inventory inv = Bukkit.createInventory(p, 9 * 3, invName);
        inv.setItem(11, getItem(new ItemStack(Material.DIAMOND_SWORD), "&6Rogue", "&eClick to become a rogue.", "Rogues deal very high damage,", "but they're only single target."));
        inv.setItem(12, getItem(new ItemStack(Material.DIAMOND_AXE), "&6Warrior", "&eClick to become a warrior.", "Warriors deal moderate damage,", "and they are very tanky."));
        inv.setItem(13, getItem(new ItemStack(Material.STICK), "&6Mage", "&eClick to become a mage.", "Mages deal high damage and AOE,", "but they aren't tanky."));
        inv.setItem(14, getItem(new ItemStack(Material.BOW), "&6Scout", "&eClick to become a scout.", "Scouts harness ranged weapons such as bows", "to deal a moderate amount of damage."));
        inv.setItem(15, getItem(new ItemStack(Material.DIAMOND_HOE), "&6Cleric", "&eClick to become a cleric.", "Clerics harness their own life force,", "to heal their allies and damage their foes."));
        inv.setItem(22, getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "&6Summoner", "&eClick to become a summoner.", "Summoners harness souls to deal high damage,", "but normally deal low damage."));

        p.openInventory(inv);
    }

    private ItemStack getItem(ItemStack item, String name, String ... lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        meta.setLore(lores);

        item.setItemMeta(meta);

        return item;
    }

    public static void clickedGUI() {
    }
}
