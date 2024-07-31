package org.xodium.illyriacore.interfaces;

public interface ConfigInferface {
    // folder
    String CONFIG_FILE = "config.yml";
    // General
    String GENERAL_PREFIX = "general";
    String CHAT_PREFIX = "chatPrefix";
    // Modules
    String MODULES_PREFIX = "modules";
    String IMMUNITY_HANDLER = "OnJoinImmunity";
    String CUSTOM_ANVIL_HANDLER = "CustomAnvil";
    // Localization
    String LOC_PREFIX = "localization";
    String IMMUNITY_TIMER_TITLE = "ImmunityTimerTitle";
    String IMMUNITY_TIMER_DURATION = "ImmunityTimerDuration";
    // Development
    String DEV_PREFIX = "development";
    String DEBUG = "Debug";
    String DEBUG_PREFIX = "debugPrefix";
}