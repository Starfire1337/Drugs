package com.starfire1337.drugs.Listeners;

import com.starfire1337.drugs.Drugs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class MilkDrinkListener implements Listener {

    @EventHandler
    public void onDrinkMilk(PlayerItemConsumeEvent e) {
        if(!e.getItem().getType().equals(Material.MILK_BUCKET)) {
            return;
        }

        Player p = e.getPlayer();

        if(!DrugUseListener.drugUse.containsKey(p.getName())) {
            return;
        }

        DrugUseListener.drugUse.remove(p.getName());
    }

}
