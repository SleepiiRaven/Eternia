package com.eternia.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static final Set<Material> weapons = EnumSet.of(
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.GOLDEN_AXE,
            Material.IRON_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE,
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.GOLDEN_SWORD,
            Material.IRON_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD,
            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.IRON_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.NETHERITE_SHOVEL,
            Material.WOODEN_HOE,
            Material.STONE_HOE,
            Material.GOLDEN_HOE,
            Material.IRON_HOE,
            Material.DIAMOND_HOE,
            Material.NETHERITE_HOE,
            Material.SHEARS
    );

    public static ItemStack getItem(ItemStack item, String name, String ... lore) {
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
}
