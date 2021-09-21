package de.ladbukkit.keepinvreset;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

/**
 * Handles all the actions aligned with the inventory of the keepinvreset command.
 *
 * @author LADBukkit (Robin Eschbach)
 */
public class InventoryListener implements Listener {
    /**
     * The plugin this listener belongs to.
     */
    private final KeepInvReset plugin;

    /**
     * The amount of clicks a player has made.
     */
    private final HashMap<Player, Integer> amounts = new HashMap<>();

    /**
     * Creates a new InventoryListener with the plugin it belongs to.
     * @param plugin The plugin this listener belongs to.
     */
    public InventoryListener(KeepInvReset plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the clicks inside the keepinvreset inventory.
     * @param e The InventoryClickEvent.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(plugin.getInvResetConfig().getOldInventoryName())){
            Player p = (Player) e.getWhoClicked();
            int am = amounts.getOrDefault(p, 0);

            // Maybe collapse ifs
            if(e.getClickedInventory() instanceof PlayerInventory) {
                e.setCancelled(true);
                return;
            }

            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }

            if((e.getSlot() > 4 && e.getSlot() < 8) || (e.getSlot() >= 9 && e.getSlot() < 18)) {
                e.setCancelled(true);
                return;
            }

            p.getInventory().addItem(e.getCurrentItem());
            e.getClickedInventory().setItem(e.getSlot(), null);
            p.updateInventory();

            am++;
            amounts.put(p, am);
            if(am >= plugin.getInvResetConfig().getYamlConfig().getInt("resets")) {
                amounts.remove(p);
                plugin.getResetted().remove(p);
                p.closeInventory();
            }
        }
    }

    /**
     * Removes the player from the resetted list when he closes the inventory.
     * @param e The InventoryCloseEvent.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(plugin.getInvResetConfig().getOldInventoryName())) {
            Player p = (Player) e.getPlayer();
            amounts.remove(p);
            plugin.getResetted().remove(p);
        }
    }

    /**
     * Removes the player from the amounts map when he leaves.
     * @param e The PlayerQuitEvent.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        amounts.remove(e.getPlayer());
    }
}
