package com.starfire1337.drugs.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerDrugConsumeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private ItemStack drug;
    private Player player;
    private String name;

    public PlayerDrugConsumeEvent(Player p, ItemStack drug, String name) {
        this.drug = drug;
        this.player = p;
        this.name = name;
    }

    public Player getPlayer () {
        return this.player;
    }

    public ItemStack getDrug() {
        return this.drug;
    }

    public String getName() {
        return this.name;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}
