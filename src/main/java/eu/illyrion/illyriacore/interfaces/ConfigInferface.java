package eu.illyrion.illyriacore.interfaces;

public interface ConfigInferface {
    // folder
    String CONFIG_FILE = "config.yml";
    // General
    String GENERAL_PREFIX = "general.";
    String CHAT_PREFIX = GENERAL_PREFIX + "chatPrefix";
    // Modules
    String MODULES_PREFIX = "modules.";
    String CUSTOM_ITEM_HANDLER = MODULES_PREFIX + "CustomItems";
    String IMMUNITY_HANDLER = MODULES_PREFIX + "OnJoinImmunity";
    String CUSTOM_ANVIL_HANDLER = MODULES_PREFIX + "CustomAnvil";
    // Localization
    String LOCALIZATION_PREFIX = "localization.";
    String IMMUNITY_TIMER = LOCALIZATION_PREFIX + "ImmunityTimer";
    String IMMUNITY_TIMER_DURATION = LOCALIZATION_PREFIX + "ImmunityTimerDuration";
    // Development
    String DEV_PREFIX = "development.";
    String DEBUG = DEV_PREFIX + "Debug";
    String DEBUG_PREFIX = DEV_PREFIX + "debugPrefix";
}