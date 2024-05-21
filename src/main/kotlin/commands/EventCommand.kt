package me.draimgoose.commands

import me.draimgoose.Main
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class EventCommand(private val plugin: Main) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            openEventGui(sender as Player)
        }
        return true
    }

    private fun openEventGui(player: Player) {
        val gui: Inventory = Bukkit.createInventory(null, 27, "${ChatColor.GOLD}Current Event")

        val eventItem = ItemStack(Material.PAPER)
        val eventMeta: ItemMeta? = eventItem.itemMeta
        eventMeta?.setDisplayName("${ChatColor.YELLOW}Текущий ивент: ${plugin.currentEvent ?: "Нет активных ивентов"}")
        eventItem.itemMeta = eventMeta

        val glassPane = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
        val glassPaneMeta: ItemMeta? = glassPane.itemMeta
        glassPaneMeta?.setDisplayName(" ")
        glassPane.itemMeta = glassPaneMeta

        for (i in 0 until gui.size) {
            gui.setItem(i, glassPane)
        }

        gui.setItem(13, eventItem)

        player.openInventory(gui)
    }
}
