package com.starfire1337.drugs.listeners;

import com.starfire1337.drugs.Drugs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements Listener {

    public static String version;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        //Remove within the next few versions.
        if (Drugs.getInstance().getConfig().isSet("update-checking")) {
            Drugs.getInstance().getConfig().set("update-checking", true);
            Drugs.getInstance().saveConfig();
        }

        if (!p.hasPermission("drugs.admin") || !Drugs.getInstance().getConfig().getBoolean("update-checking"))
            return;

        if (version != null) {
            p.sendMessage(ChatColor.RED + "[Drugs] An update to version " + version + " is available!");
            return;
        }

        Drugs.getInstance().getServer().getScheduler().runTaskAsynchronously(Drugs.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.spigotmc.org/api/general.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.writeBytes("resource=11122");
                    dataOutputStream.flush();
                    dataOutputStream.close();

                    final String version = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();
                    if (!version.equalsIgnoreCase(Drugs.getInstance().getDescription().getVersion())) {
                        UpdateChecker.version = version;
                        Drugs.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Drugs.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                p.sendMessage(ChatColor.RED + "[Drugs] An update to version " + version + " is available!");
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
