package com.starfire1337.drugs.listeners;

import com.starfire1337.drugs.config.Config;
import com.starfire1337.drugs.drug.DrugManager;
import com.starfire1337.drugs.Drugs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class ItemCraftListener implements Listener {

    private Config c = new Config();

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent e) {
        if(!Drugs.getInstance().getConfig().getBoolean("allow-recraft")) {
            if(!(e.getView().getPlayer() instanceof Player)) {
                return;
            }
            Player p = (Player) e.getView().getPlayer();
            ItemStack isd = e.getRecipe().getResult();
            int amnt = isd.getAmount();
            isd.setAmount(1);
            if(!DrugManager.isDrug(isd)) {
                isd.setAmount(amnt);
                return;
            }

            if(!p.hasPermission(String.format("drugs.craft.%s", DrugManager.getDrugName(isd)))) {
                e.getInventory().setResult(new ItemStack(Material.AIR));
            }

            isd.setAmount(amnt);

            ItemStack[] is = e.getInventory().getMatrix();
            for(ItemStack i : is) {
                if(i != null && i.hasItemMeta()) {
                    if(i.getItemMeta().hasDisplayName()) {
                        e.getInventory().setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

}
