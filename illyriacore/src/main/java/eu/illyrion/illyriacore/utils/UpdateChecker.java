package eu.illyrion.illyriacore.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import eu.illyrion.illyriacore.IllyriaCore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    private final IllyriaCore plugin;
    private final String resourceUrl;

    public UpdateChecker(IllyriaCore plugin, String resourceUrl) {
        this.plugin = plugin;
        this.resourceUrl = resourceUrl;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(resourceUrl).openConnection();
                connection.setRequestMethod("GET");

                String latestVersion = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                        .readLine();
                PluginDescriptionFile pdf = plugin.getDescription();
                String currentVersion = pdf.getVersion();

                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    plugin.getLogger().info("There is a new update available.");
                    plugin.getLogger()
                            .info("Current version: " + currentVersion + ", Latest version: " + latestVersion);
                } else {
                    plugin.getLogger().info("Your version is up to date.");
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Update check failed: " + e.getMessage());
            }
        });
    }
}