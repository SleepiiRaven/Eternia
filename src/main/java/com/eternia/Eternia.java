package com.eternia;


import com.eternia.commands.Adventure;
import com.eternia.commands.Creative;
import com.eternia.commands.Spectator;
import com.eternia.commands.Survival;
import com.eternia.listeners.PlayerListener;
import com.eternia.utils.JsonUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Eternia extends JavaPlugin {
    private static Eternia instance;
    private CooldownManager cdInstance;
    public static Eternia getInstance() {
        return instance;
    }
    public CooldownManager getCdInstance() {
        return cdInstance;
    }
    @Override
    public void onEnable() {
        instance = this;
        cdInstance = new CooldownManager();

        JsonUtils.loadConfig();

        getCommand("survival").setExecutor(new Survival());
        getCommand("creative").setExecutor(new Creative());
        getCommand("adventure").setExecutor(new Adventure());
        getCommand("spectator").setExecutor(new Spectator());

        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
