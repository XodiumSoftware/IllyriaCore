package eu.illyrion.illyriacore.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import org.bukkit.Bukkit;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import eu.illyrion.illyriacore.IllyriaCore;

public class UpdateChecker {

    // TODO: Make these constants dynamic.
    private static final String REPO = "";
    private static final String CURRENT_VERSION = "";

    // TODO: Implement UpdateChecker.
    // TODO: Add logic that checks if there are releases available on the GitHub...
    // repository first otherwise it skip.
    public void init() {
        Bukkit.getScheduler().runTaskAsynchronously(IllyriaCore.getInstance(), () -> {
            try {
                URI uri = new URI("https://api.github.com/repos/" + REPO + "/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
                connection.setRequestMethod("GET");

                InputStream is = connection.getInputStream();
                JsonElement el = JsonParser.parseReader(new InputStreamReader(is));
                String latestVersion = el.getAsJsonObject().get("tag_name").getAsString();

                if (!CURRENT_VERSION.equalsIgnoreCase(latestVersion)) {
                    Bukkit.getConsoleSender().sendMessage("A new version is available: " + latestVersion);
                }

            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("Update checker failed! Stack trace:");
                e.printStackTrace();
            }
        });
    }
}
