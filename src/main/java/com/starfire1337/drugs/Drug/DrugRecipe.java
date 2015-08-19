package com.starfire1337.drugs.Drug;

import com.starfire1337.drugs.Config.Config;
import com.starfire1337.drugs.Drugs;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import java.util.List;

public class DrugRecipe {

    private Config c = new Config();

    public void addRecipes() {
        FileConfiguration config = Drugs.getInstance().getConfig();

        for(String drug : config.getConfigurationSection("drugs").getKeys(false)) {

            ItemStack is = c.getDrug(drug);

            DrugManager.addDrug(c.getDrug(drug, 1), drug);

            if(config.getBoolean(String.format("drugs.%s.craftable", drug))) {
                switch(config.getString(String.format("drugs.%s.recipe.type", drug))) {
                    case "shaped":
                        ShapedRecipe drugRecipe = new ShapedRecipe(is);
                        List<?> shapeList = config.getList(String.format("drugs.%s.recipe.shape", drug));
                        String[] shape = new String[shapeList.size()];
                        shape = shapeList.toArray(shape);
                        drugRecipe.shape(shape);

                        for(String item : config.getConfigurationSection(String.format("drugs.%s.recipe.ingredients", drug)).getKeys(false)) {
                            String parts[] = config.getString(String.format("drugs.%s.recipe.ingredients.%s", drug, item)).split(":");
                            int dmg = (parts.length == 2 ? Integer.parseInt(parts[1]) : 0);

                            MaterialData md = new MaterialData(Integer.parseInt(parts[0]), (byte) dmg);

                            drugRecipe.setIngredient(item.charAt(0), md);
                        }

                        Drugs.getInstance().getServer().addRecipe(drugRecipe);
                        break;

                    case "shapeless":
                        ShapelessRecipe shapelessDrugRecipe = new ShapelessRecipe(is);

                        for(Object ingredient : config.getList(String.format("drugs.%s.recipe.ingredients", drug))) {
                            String[] parts = ((String) ingredient).split(",");
                            String[] itemParts = parts[1].split(":");
                            int dmg = (itemParts.length == 2 ? Integer.parseInt(itemParts[1]) : 0);

                            MaterialData md = new MaterialData(Integer.parseInt(itemParts[0]), (byte) dmg);

                            shapelessDrugRecipe.addIngredient(Integer.parseInt(parts[0]), md);
                        }

                        Drugs.getInstance().getServer().addRecipe(shapelessDrugRecipe);
                        break;

                    case "smelted":
                        String parts[] = config.getString(String.format("drugs.%s.recipe.ingredient", drug)).split(":");
                        int dmg = (parts.length == 2 ? Integer.parseInt(parts[1]) : 0);

                        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(is, Material.getMaterial(Integer.parseInt(parts[0])), dmg);

                        Drugs.getInstance().getServer().addRecipe(furnaceRecipe);

                        break;
                }
            }
        }
    }

}
