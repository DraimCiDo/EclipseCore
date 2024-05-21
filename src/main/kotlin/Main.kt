package me.draimgoose

import me.draimgoose.commands.EventCommand
import me.draimgoose.commands.MainCommand
import me.draimgoose.listeners.GuiEventListener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin() {

    var currentEvent: String? = null

    override fun onEnable() {
        saveDefaultConfig()
        loadLangFile()


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

    private fun loadLangFile() {
        val langFile = File(dataFolder, "lang.yml")
        if (!langFile.exists()) {
            saveResource("lang.yml", false)
        }
    }

    fun getLangString(path: String): String {
        return config.getString(path) ?: "String not found"
    }

    fun getConfigString(path: String): String {
        return config.getString(path) ?: "String not found"
    }

    fun getConfigStringList(path: String): List<Map<String, String>> {
        return config.getMapList(path) as List<Map<String, String>>
    }
}
