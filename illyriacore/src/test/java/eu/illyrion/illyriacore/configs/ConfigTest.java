package eu.illyrion.illyriacore.configs;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.illyrion.illyriacore.IllyriaCore;
import eu.illyrion.illyriacore.interfaces.ConfigInferface;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest implements ConfigInferface {

    private IllyriaCore plugin;
    private FileConfiguration fileConfiguration;

    @BeforeEach
    public void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(IllyriaCore.class);
        fileConfiguration = plugin.getConfig();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testInit() {
        File confFile = new File(plugin.getDataFolder(), CONFIG_FILE);

        if (!confFile.exists()) {
            plugin.saveResource(CONFIG_FILE, false);
        }

        plugin.reloadConfig();
        fileConfiguration = plugin.getConfig();

        FileConfiguration result = Config.init();

        assertEquals(result.saveToString(), fileConfiguration.saveToString());
    }
}
