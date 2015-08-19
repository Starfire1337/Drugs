package com.starfire1337.drugs.Drug;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DrugManager {

    private static HashMap<ItemStack, String> drugs = new HashMap<>();

    public static void addDrug(ItemStack is, String name) {
        drugs.put(is, name);
    }

    public static String getDrugName(ItemStack is) {
        return drugs.get(is);
    }

    public static boolean isDrug(ItemStack is) {
        return drugs.containsKey(is);
    }

    public static HashMap<ItemStack, String> getDrugs() {
        return drugs;
    }

    public static void clearDrugs() {
        drugs.clear();
    }

}
