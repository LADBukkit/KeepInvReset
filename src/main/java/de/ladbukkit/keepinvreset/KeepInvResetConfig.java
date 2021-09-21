package de.ladbukkit.keepinvreset;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * The Config for the KeepInvReset plugin.
 *
 * @author LADBukkit (Robin Eschbach)
 */
public class KeepInvResetConfig {
    /**
     * The underlying config file.
     */
    private final File configFile = new File("plugins/keepinvreset/config.yaml");

    /**
     * The underlying configuration.
     */
    private final FileConfiguration yamlConfig = YamlConfiguration.loadConfiguration(configFile);

    /**
     * The message when a player has been reset.
     */
    private String resetMessage = "&eThere has been a reset. Type &7/keepinvreset &eto view your old items. You can take &7%resets% &eitems.";

    /**
     * The name of the inventory the keepinvreset commands opens.
     */
    private String oldInventoryName = "&8Old Inventory";

    /**
     * The failure message if a not ressetted player issues the keepinvreset command.
     */
    private String notResettedMessage = "&cYou have not been resetted!";

    /**
     * The name of the spacers.
     */
    private String spacerName = "&0";

    /**
     * The error message if a number could not be saved.
     */
    private String couldntSaveNumber = "&cCould not save the amount of resets!";

    /**
     * The success message if a number could be saved.
     */
    private String saveNumber = "&eThe amount of resets has been saved.";

    /**
     * The error message if the player has not the right permission.
     */
    private String noPermission = "&cYou do not have the permission to use this command!";

    /**
     * Creates the file if it does not exist and reads all strings from the file.
     */
    public KeepInvResetConfig() {
        if(!this.configFile.exists()) {
            this.yamlConfig.set("resets", 3);
            this.yamlConfig.set("strings.reset", resetMessage);
            this.yamlConfig.set("strings.oldinventory", oldInventoryName);
            this.yamlConfig.set("strings.notresetted", notResettedMessage);
            this.yamlConfig.set("strings.spacername", spacerName);
            this.yamlConfig.set("strings.couldntsavenumber", couldntSaveNumber);
            this.yamlConfig.set("strings.savenumber", saveNumber);
            this.yamlConfig.set("strings.nopermission", noPermission);
            try {
                this.configFile.createNewFile();
                this.yamlConfig.save(this.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.resetMessage = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.reset"));
        this.oldInventoryName = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.oldinventory"));
        this.notResettedMessage = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.notresetted"));
        this.spacerName = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.spacername"));
        this.couldntSaveNumber = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.couldntsavenumber"));
        this.saveNumber = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.savenumber"));
        this.noPermission = ChatColor.translateAlternateColorCodes('&', this.yamlConfig.getString("strings.nopermission"));
    }

    // These are only getters. Look for the documentation of the attributes for the description of them.

    public FileConfiguration getYamlConfig() {
        return yamlConfig;
    }

    public File getConfigFile() {
        return configFile;
    }

    public String getResetMessage() {
        return resetMessage;
    }

    public String getOldInventoryName() {
        return oldInventoryName;
    }

    public String getNotResettedMessage() {
        return notResettedMessage;
    }

    public String getSpacerName() {
        return spacerName;
    }

    public String getCouldntSaveNumber() {
        return couldntSaveNumber;
    }

    public String getSaveNumber() {
        return saveNumber;
    }

    public String getNoPermission() {
        return noPermission;
    }
}
