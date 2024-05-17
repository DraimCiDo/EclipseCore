package me.draimgoose

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin(), Listener, CommandExecutor {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        getCommand("test")?.setExecutor(this)
        logger.info("Плагин включен!")
    }

    override fun onDisable() {
        logger.info("Плагин выключен!")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player && command.name.equals("test", ignoreCase = true)) {
            openTestGUI(sender)
            return true
        }
        return false
    }

    private fun openTestGUI(player: Player) {
        val gui: Inventory = Bukkit.createInventory(null, 9, "Test GUI")

        val testButton = ItemStack(Material.DIAMOND)
        val meta: ItemMeta? = testButton.itemMeta
        if (meta != null) {
            meta.setDisplayName("${ChatColor.GREEN}Click Me!")
            testButton.itemMeta = meta
        }

        gui.setItem(4, testButton)

        player.openInventory(gui)
    }

    @EventHandler
    fun onInventoryClick(event : InventoryClickEvent) {
        if (event.view.title == "Test GUI") {
            event.isCancelled = true
            if (event.currentItem != null && event.currentItem!!.type == Material.DIAMOND) {
                event.whoClicked.sendMessage("${ChatColor.GREEN}You clicked the button!")
                event.whoClicked.closeInventory()
            }
        }
    }
}