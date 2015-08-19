package com.starfire1337.drugs;

import com.starfire1337.drugs.Config.ConfigCheck;
import com.starfire1337.drugs.Listeners.*;
import com.starfire1337.drugs.Drug.DrugManager;
import com.starfire1337.drugs.Drug.DrugRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Drugs extends JavaPlugin {

    private static Plugin plugin;

    private ConfigCheck configcheck = new ConfigCheck();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        plugin = this;

        getServer().getPluginManager().registerEvents(new ItemCraftListener(), this);
        getServer().getPluginManager().registerEvents(new DrugUseListener(), this);
        getServer().getPluginManager().registerEvents(new MilkDrinkListener(), this);

        if(getConfig().getBoolean("check-config")) {
            if(!configcheck.checkConfig()) {
                getLogger().info("-----------------------------------------");
                getLogger().info("Error! Could not enable drugs!");
                getLogger().info("Configuration Error: " + configcheck.getError());
                getLogger().info("-----------------------------------------");
                getServer().getPluginManager().disablePlugin(this);
            } else {
                DrugRecipe recipe = new DrugRecipe();
                recipe.addRecipes();
            }
        }  else {
            DrugRecipe recipe = new DrugRecipe();
            recipe.addRecipes();
        }
    }

    @Override
    public void onDisable() {
        plugin = null;

        DrugManager.clearDrugs();
    }

    public static Plugin getInstance() {
        return plugin;
    }

}
