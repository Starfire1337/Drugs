package com.starfire1337.drugs.Config;

import com.starfire1337.drugs.Drugs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Config {

    public ItemStack getDrug(String name) {
        FileConfiguration config = Drugs.getInstance().getConfig();

        String parts[] = config.getString(String.format("drugs.%s.item", name)).split(":");
        int dmg = (parts.length == 2 ? Integer.parseInt(parts[1]) : 0);

        if(Material.getMaterial(Integer.parseInt(parts[0])) == null) {
            Drugs.getInstance().getLogger().info(parts[0]);
        Drugs.getInstance().getLogger().info(config.getString(String.format("drugs.%s.item", name)));
        }

        ItemStack is = new ItemStack(Material.getMaterial(Integer.parseInt(parts[0])), config.getInt(String.format("drugs.%s.recipe.yield", name)), (short) dmg);

        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(String.format("drugs.%s.displayname", name))));
        is.setItemMeta(im);

        return is;
    }

    public ItemStack getDrug(String name, int amount) {
        FileConfiguration config = Drugs.getInstance().getConfig();

        String parts[] = config.getString(String.format("drugs.%s.item", name)).split(":");
        int dmg = (parts.length == 2 ? Integer.parseInt(parts[1]) : 0);

        ItemStack is = new ItemStack(Material.getMaterial(Integer.parseInt(parts[0])), amount, (short) dmg);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(String.format("drugs.%s.displayname", name))));
        is.setItemMeta(im);

        return is;
    }

}
