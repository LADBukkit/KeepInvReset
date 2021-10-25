package de.ladbukkit.keepinvreset;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

/**
 * Handles the logout of a player aka saves the inventory.
 *
 * @author LADBukkit (Robin Eschbach)
 */
public class LogoutListener implements Listener {
    /**
     * The plugin this listener belongs to.
     */
    private final KeepInvReset plugin;

    /**
     * Creates a new LogoutListener with the plugin it belongs to.
     * @param plugin The plugin this listener belongs to.
     */
    public LogoutListener(KeepInvReset plugin) {
        this.plugin = plugin;
    }

    /**
     * Save the inventory of the player.
     * @param e The PlayerQuitEvent.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        InventoryFile invFile = new InventoryFile(p.getUniqueId());
        try {
            invFile.save(p.getInventory(), p.getWorld().getSeed());
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.getLogger().severe("Could not save inventory for " + p.getName());
        }

        this.plugin.getResetted().remove(p);
    }
}
