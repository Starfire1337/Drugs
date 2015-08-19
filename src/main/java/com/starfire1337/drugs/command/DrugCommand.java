package com.starfire1337.drugs.command;

import com.starfire1337.drugs.Drugs;
import com.starfire1337.drugs.config.ConfigCheck;
import com.starfire1337.drugs.drug.DrugManager;
import com.starfire1337.drugs.drug.DrugRecipe;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DrugCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(cmd.getName().equalsIgnoreCase("drugs")) {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.YELLOW + String.format("Drugs version: %s", ChatColor.RED + Drugs.getInstance().getDescription().getVersion()));
                sender.sendMessage(ChatColor.YELLOW + String.format("Created by: %sStarfire1337", ChatColor.RED));
                return true;
            }
            switch(args[0]) {
                case "reload":
                    Drugs.getInstance().getServer().clearRecipes();
                    DrugManager.clearDrugs();

                    Drugs.getInstance().reloadConfig();

                    ConfigCheck configCheck = new ConfigCheck();
                    if(Drugs.getInstance().getConfig().getBoolean("check-config")) {
                        if(!configCheck.checkConfig()) {
                            sender.sendMessage(ChatColor.YELLOW + "Error! Could not enable drugs!");
                            sender.sendMessage(ChatColor.YELLOW + "Configuration Error: " + configCheck.getError());
                            Drugs.getInstance().getServer().getPluginManager().disablePlugin(Drugs.getInstance());
                            return true;
                        } else {
                            DrugRecipe recipe = new DrugRecipe();
                            recipe.addRecipes();
                        }
                    }  else {
                        DrugRecipe recipe = new DrugRecipe();
                        recipe.addRecipes();
                    }
                    sender.sendMessage(ChatColor.YELLOW + "Reload complete!");
                    break;
                default:
                    return true;
            }
            return true;
        }
        return false;
    }

}
