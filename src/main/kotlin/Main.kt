package me.draimgoose

import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {
    override fun onEnable() {
        logger.info("Плагин включен!")
    }

    override fun onDisable() {
        logger.info("Плагин выключен!")
    }

}