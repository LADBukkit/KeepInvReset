package de.ladbukkit.keepinvreset;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Can check a saved inventory against another inventory.
 * Can also handle the save and load of an inventory.
 *
 * @author LADBukkit (Robin Eschbach)
 */
public class InventoryFile {
    /**
     * The folder where to find all the inventories.
     */
    public static final File INV_FOLDER = new File("plugins/keepinvreset/inventories");

    /**
     * The uuid of this inventory (The player associated with it)
     */
    private final UUID uuid;

    /**
     * The file to this config.
     */
    private final File file;

    /**
     * The config for this inventory.
     */
    private final FileConfiguration config;

    /**
     * If available it loads the inventory. If not it just stores the uuid.
     * @param uuid The uuid of the player associated with this inventory file.
     */
    public InventoryFile(UUID uuid) {
        this.uuid = uuid;
        this.file = new File(INV_FOLDER, uuid.toString() + ".yaml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Check if two itemstacks are equal while also equaling Material.AIR and null.
     * @param a The first itemstack.
     * @param b The second itemstack.
     * @return Whether the two itemstacks are equal.
     */
    private boolean itemStackEquals(ItemStack a, ItemStack b) {
        if(a == null) {
            if(b == null) return true;
            return b.getType() == Material.AIR;
        }
        if(b == null) return a.getType() == Material.AIR;
        if(a.getType() == Material.AIR && b.getType() == Material.AIR) return true;

        return Objects.equals(a, b);
    }

    /**
     * Checks an inventory against this saved inventory.
     * @param inv The inventory to check against.
     * @return Whether the inventory and the saved inventory are the same.
     */
    public boolean checkInventory(PlayerInventory inv) {
        // no config = nothing to check
        if(!this.file.exists()) return false;

        // check offhand
        if(!itemStackEquals(this.config.getItemStack("offhand"), inv.getItemInOffHand())) return false;

        // check armor
        for(int i = 0; i < inv.getArmorContents().length; i++) {
            if(!itemStackEquals(this.config.getItemStack("armor." + i), inv.getArmorContents()[i])) return false;
        }

        // check inventory
        for(int i = 0; i < inv.getStorageContents().length; i++) {
            if(!itemStackEquals(this.config.getItemStack("slot." + i), inv.getStorageContents()[i])) return false;
        }

        return true;
    }

    /**
     * Checks the seed against the one in the config.
     * @param seed The seed to check.
     * @return if they are the same.
     */
    public boolean checkSeed(long seed) {
        if(!this.file.exists()) return true;
        return seed == this.config.getLong("seed", 0);
    }

    /**
     * Saves the given inventory in the file associated with the uuid of this object.
     * @param inv The inventory to save.
     * @throws IOException If the file couldn't be saved.
     */
    public void save(PlayerInventory inv, long seed) throws IOException {
        this.config.set("seed", seed);

        // save offhand
        if(!Tag.SHULKER_BOXES.getValues().contains(inv.getItemInOffHand().getType())) {
            this.config.set("offhand", inv.getItemInOffHand());
        }

        // save armor
        for(int i = 0; i < inv.getArmorContents().length; i++) {
            if(inv.getArmorContents()[i] != null && !Tag.SHULKER_BOXES.getValues().contains(inv.getArmorContents()[i].getType())) {
                this.config.set("armor." + i, inv.getArmorContents()[i]);
            }
        }

        // save inventory
        for(int i = 0; i < inv.getStorageContents().length; i++) {
            if(inv.getStorageContents()[i] != null && !Tag.SHULKER_BOXES.getValues().contains(inv.getStorageContents()[i].getType())) {
                this.config.set("slot." + i, inv.getStorageContents()[i]);
            }
        }

        // create file
        if(!file.exists()) {
            file.createNewFile();
        }

        // save config
        this.config.save(file);
    }

    /**
     * Creates an inventory containing all the items in file.
     * @param name The name of the inventory.
     * @param spacerName The name of the spacer.
     * @return An inventory with all the items.
     */
    public Inventory createInventory(String name, String spacerName) {
        Inventory inv = Bukkit.createInventory(null, 6 * 9, name);

        // set spacers
        ItemStack is = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(spacerName);
        is.setItemMeta(im);
        for(int i = 4; i < 8; i++) {
            inv.setItem(i, is);
        }
        for(int i = 9; i < 18; i++) {
            inv.setItem(i, is);
        }

        // set inv data
        inv.setItem(8, this.config.getItemStack("offhand"));

        for(int i = 0; i < 4; i++) {
            inv.setItem(3 - i, this.config.getItemStack("armor." + i));
        }

        for(int i = 0; i < 36; i++) {
            // Flip the indices to make it appear the same as the player inventory.
            int offset = (3 - (i / 9)) * 9;
            inv.setItem(18 + (offset + i % 9), this.config.getItemStack("slot." + i));
        }

        return inv;
    }
}
