package de.ladbukkit.keepinvreset;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * The Listener that handles the login of the player.
 *
 * @author LADBukkit (Robin Eschbach)
 */
public class LoginListener implements Listener {

    /**
     * The plugin this listener belongs to.
     */
    private final KeepInvReset plugin;

    /**
     * Creates a new LoginListener with the plugin it belongs to.
     * @param plugin The plugin this listener belongs to.
     */
    public LoginListener(KeepInvReset plugin) {
        this.plugin = plugin;
    }

    /**
     * Sends the player a message if he has been reset.
     * @param e The PlayerJoinEvent.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        InventoryFile invFile = new InventoryFile(p.getUniqueId());

        if(plugin.getInvResetConfig().getYamlConfig().getInt("resets") <= 0) return;

        if(!invFile.checkSeed(p.getWorld().getSeed())) {
            p.sendMessage(plugin.getInvResetConfig().getResetMessage().replace("%resets%", Integer.toString(plugin.getInvResetConfig().getYamlConfig().getInt("resets"))));
            this.plugin.getResetted().add(p);
        }
    }
}
