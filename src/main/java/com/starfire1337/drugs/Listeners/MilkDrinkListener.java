package com.starfire1337.drugs.listeners;

import com.starfire1337.drugs.Drugs;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class MilkDrinkListener implements Listener {

    @EventHandler(priority=EventPriority.LOW)
    public void onDrinkMilk(PlayerItemConsumeEvent e) {
        if(!e.getItem().getType().equals(Material.MILK_BUCKET)) {
            return;
        }

        Player p = e.getPlayer();

        if(!DrugUseListener.drugUse.containsKey(p.getName())) {
            return;
        }

        if(!Drugs.getInstance().getConfig().getBoolean("allow-milk")) {
            if(DrugUseListener.drugUse.get(p.getName()) > (System.currentTimeMillis() / 1000L)) {
                e.setCancelled(true);
                return;
            }
        }

        DrugUseListener.drugUse.remove(p.getName());
    }

}
