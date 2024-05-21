package me.draimgoose

import me.draimgoose.commands.EventCommand
import me.draimgoose.commands.MainCommand
import me.draimgoose.listeners.GuiEventListener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    var currentEvent: String? = null

    override fun onEnable() {
        // Регистрация команд
        getCommand("test")?.setExecutor(MainCommand(this))
        getCommand("event")?.setExecutor(EventCommand(this))

        // Регистрация событий
        server.pluginManager.registerEvents(GuiEventListener(this), this)

        logger.info("MyPlugin включен!")
    }

    override fun onDisable() {
        logger.info("MyPlugin выключен!")
    }
}
