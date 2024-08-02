package org.xodium.illyriacore.interfaces;

/**
 * The Config Interface.
 */
public interface CI {
    // folder
    String CONFIG_FILE = "config.yml";
    // General
    String GENERAL_PREFIX = "general";
    String CHAT_PREFIX = "chatPrefix";
    // Modules
    String MODULES_PREFIX = "modules";
    String MODULE_ENABLED = "Enabled";
    // Immunity Module
    String IMMUNITY_MODULE = "OnJoinImmunity";
    String IMMUNITY_TIMER_TITLE = "ImmunityTimerTitle";
    String IMMUNITY_TIMER_DURATION = "ImmunityTimerDuration";
    // Custom Anvil Module
    String CUSTOM_ANVIL_MODULE = "CustomAnvil";
}