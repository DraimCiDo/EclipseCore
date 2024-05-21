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
        val gui: Inventory = Bukkit.createInventory(null, 9, "Current Event")

        val eventItem = ItemStack(Material.PAPER)
        val eventMeta: ItemMeta? = eventItem.itemMeta
        eventMeta?.setDisplayName("${ChatColor.YELLOW}Текущий ивент: ${plugin.currentEvent ?: "Нет активных ивентов"}")
        eventItem.itemMeta = eventMeta

        gui.setItem(4, eventItem)

        player.openInventory(gui)
    }
}
