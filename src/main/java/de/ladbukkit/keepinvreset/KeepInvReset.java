package de.ladbukkit.keepinvreset;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * The Main Class of the KeepInvReset plugin.
 * @author LADBukkit (Robin Eschbach)
 */
public class KeepInvReset extends JavaPlugin {
    /**
     * A Set containing all the player that have been resetted.
     */
    private final Set<Player> resetted = new HashSet<>();

    /**
     * The config for this plugin. It contains all the strings and the amount of resets.
     */
    private KeepInvResetConfig invResetConfig;

    /**
     * Creates the inventory directory, the config, registers the events and the command executor.
     */
    @Override
    public void onEnable() {
        InventoryFile.INV_FOLDER.mkdirs();

        invResetConfig = new KeepInvResetConfig();

        Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);
        Bukkit.getPluginManager().registerEvents(new LogoutListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);

        getCommand("keepinvreset").setExecutor(new KeepInvResetCommand(this));
    }

    /**
     * @return The set of resetted players.
     */
    public Set<Player> getResetted() {
        return resetted;
    }

    /**
     * @return The config of this plugin.
     */
    public KeepInvResetConfig getInvResetConfig() {
        return invResetConfig;
    }
}
