package com.starfire1337.drugs.Listeners;

import com.starfire1337.drugs.Config.Config;
import com.starfire1337.drugs.Drug.DrugManager;
import com.starfire1337.drugs.Drugs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class DrugUseListener implements Listener {

    private Config c = new Config();
    public static HashMap<String, Integer> drugUse = new HashMap<>();

    @EventHandler
    public void onUseDrug(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        FileConfiguration config = Drugs.getInstance().getConfig();

        if(!config.getBoolean("allow-milk")) {
            if(e.getItem().getType().equals(Material.MILK_BUCKET)) {
                e.setCancelled(true);
            }
        }

        if(config.getBoolean("force-sneak")) {
            if(!e.getPlayer().isSneaking()) {
                return;
            }
        }

        ItemStack is = e.getItem();
        int amnt = is.getAmount();

        is.setAmount(1);

        if(!DrugManager.isDrug(is)) {
            is.setAmount(amnt);
            return;
        }

        String name = DrugManager.getDrugName(is);
        is.setAmount(amnt);

        if(drugUse.containsKey(p.getName())) {
            if(drugUse.get(p.getName()) > (System.currentTimeMillis() / 1000L)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString(String.format("drugs.%s.cooldown-message", name))));
                return;
            }
        }

        if(!p.hasPermission(String.format("drugs.use.%s", name))) {
            return;
        }

        ConfigurationSection cs = config.getConfigurationSection(String.format("drugs.%s", name));

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', cs.getString("usage-message")));
        for(Object effect : cs.getList("effects")) {
            String[] parts = ((String) effect).split(",");
            PotionEffectType effectType = PotionEffectType.getByName(parts[0]);
            if(p.hasPotionEffect(effectType)) {
                p.removePotionEffect(effectType);
            }
            p.addPotionEffect(new PotionEffect(effectType, 20 * Integer.parseInt(parts[1]), Integer.parseInt(parts[2]) - 1));
        }

        p.getInventory().removeItem(c.getDrug(name, 1));
        p.updateInventory();

        if(drugUse.containsKey(p.getName())) {
            drugUse.remove(p.getName());
        }
        drugUse.put(p.getName(), (int) (config.getInt(String.format("drugs.%s.cooldown", name)) + (System.currentTimeMillis() / 1000L)));

    }

}
