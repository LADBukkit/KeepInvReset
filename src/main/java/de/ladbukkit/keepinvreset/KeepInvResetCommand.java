package de.ladbukkit.keepinvreset;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Handles the keepinvreset command.
 *
 * @author LADBukkit (Robin Eschbach)
 */
public class KeepInvResetCommand implements CommandExecutor {
    /**
     * The plugin this CommandExecutor belongs to.
     */
    private final KeepInvReset plugin;

    /**
     * Creates a new CommandExecutor with the plugin it belongs to.
     * @param plugin The plugin this CommandExecutor belongs to.
     */
    public KeepInvResetCommand(KeepInvReset plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the command,
     * @param sender The one who sends the command.
     * @param cmd The command that has been executed.
     * @param label The name with which it was executed.
     * @param args The arguments the sender send with the command.
     * @return Whether the execution was successful or not.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info("This command is only for the living!");
            return false;
        }

        Player p = (Player) sender;

        if(args.length == 0) {
            if(plugin.getResetted().contains(p) && plugin.getInvResetConfig().getYamlConfig().getInt("resets") > 0) {
                InventoryFile invFile = new InventoryFile(p.getUniqueId());
                p.openInventory(invFile.createInventory(plugin.getInvResetConfig().getOldInventoryName(), plugin.getInvResetConfig().getSpacerName()));
            } else {
                p.sendMessage(plugin.getInvResetConfig().getNotResettedMessage());
            }
            return true;
        }
        if(args.length == 1) {
            if(p.hasPermission("keepinvreset.number")) {
                try {
                    int number = Integer.parseInt(args[0]);
                    plugin.getInvResetConfig().getYamlConfig().set("resets", number);
                    plugin.getInvResetConfig().getYamlConfig().save(plugin.getInvResetConfig().getConfigFile());
                    p.sendMessage(plugin.getInvResetConfig().getSaveNumber());
                } catch(NumberFormatException | IOException e) {
                    p.sendMessage(plugin.getInvResetConfig().getCouldntSaveNumber());
                }
            } else {
                p.sendMessage(plugin.getInvResetConfig().getNoPermission());
            }

            return true;
        }

        return false;
    }
}
