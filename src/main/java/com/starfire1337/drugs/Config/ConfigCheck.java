package com.starfire1337.drugs.config;

import com.starfire1337.drugs.Drugs;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigCheck {

    private String errorMsg;
    private boolean error = false;

    public boolean checkConfig() {
        FileConfiguration config = Drugs.getInstance().getConfig();

        this.assertType(config, "int", "config-version");
        this.assertType(config, "boolean", "check-config");
        this.assertType(config, "boolean", "force-sneak");
        this.assertType(config, "boolean", "allow-recraft");
        this.assertType(config, "boolean", "allow-milk");
        this.assertType(config, "cs", "drugs");

        if(this.error)
            return false;

        if(config.getInt("config-version") != 3) {
            this.errorMsg = "Outdated configuration! Please check the website for the newest config!";
            return false;
        }

        for(String drug : config.getConfigurationSection("drugs").getKeys(false)) {
            this.assertType(config, "string", String.format("drugs.%s.displayname", drug));
            this.assertType(config, "string", String.format("drugs.%s.item", drug));
            this.assertType(config, "boolean", String.format("drugs.%s.craftable", drug));
            this.assertType(config, "cs", String.format("drugs.%s.recipe", drug));
            this.assertType(config, "string", String.format("drugs.%s.recipe.type", drug));
            if(this.error)
                return false;
            switch(config.getString(String.format("drugs.%s.recipe.type", drug))) {
                case "shaped":
                    this.assertType(config, "list", String.format("drugs.%s.recipe.shape", drug));
                    this.assertType(config, "cs", String.format("drugs.%s.recipe.ingredients", drug));
                    this.assertType(config, "int", String.format("drugs.%s.recipe.yield", drug));
                    break;
                case "shapeless":
                    this.assertType(config, "list", String.format("drugs.%s.recipe.ingredients", drug));
                    this.assertType(config, "int", String.format("drugs.%s.recipe.yield", drug));
                    break;
                case "smelted":
                    this.assertType(config, "string", String.format("drugs.%s.recipe.ingredient", drug));
                    break;
                default:
                    this.error = true;
                    this.errorMsg = String.format("Path drugs.%s.recipe.type expected to be 'shaped', 'shapeless' or 'smelted'!", drug);
                    return false;
            }
            this.assertType(config, "string", String.format("drugs.%s.usage-message", drug));
            this.assertType(config, "list", String.format("drugs.%s.effects", drug));
            this.assertType(config, "int", String.format("drugs.%s.cooldown", drug));
            this.assertType(config, "string", String.format("drugs.%s.cooldown-message", drug));
        }

        return !this.error;
    }

    public String getError() {
        return this.errorMsg;
    }

    private void assertType(FileConfiguration config, String type, String path) {

        if(this.error) {
            return;
        }

        if(!config.isSet(path)) {
            this.errorMsg = String.format("Path %s was not found!", path);
            this.error = true;
            return;
        }

        switch(type) {
            case "string":
                if(!config.isString(path)) {
                    this.errorMsg = String.format("Path %s must be a string.", path);
                    this.error = true;
                    return;
                }
                break;
            case "int":
                if(!config.isInt(path)) {
                    this.errorMsg = String.format("Path %s must be an integer.", path);
                    this.error = true;
                    return;
                }
                break;
            case "boolean":
                if(!config.isBoolean(path)) {
                    this.errorMsg = String.format("Path %s must be a boolean.", path);
                    this.error = true;
                    return;
                }
                break;
            case "list":
                if(!config.isList(path)) {
                    this.errorMsg = String.format("Path %s must be a list.", path);
                    this.error = true;
                    return;
                }
                break;
            case "cs":
                if(!config.isConfigurationSection(path)) {
                    this.errorMsg = String.format("Path %s must be a configuration section.", path);
                    this.error = true;
                    return;
                }
                break;
        }
    }

}
