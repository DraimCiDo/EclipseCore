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

class MainCommand(private val plugin: Main) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            openMainGui(sender)
        }
        return true
    }

    private fun openMainGui(player: Player) {
        val gui: Inventory = Bukkit.createInventory(null, 9, "Event Manager")

        val parkourButton = ItemStack(Material.FEATHER)
        val parkourMeta: ItemMeta? = parkourButton.itemMeta
        parkourMeta?.setDisplayName("${ChatColor.GREEN}Ивент паркур")
        parkourButton.itemMeta = parkourMeta

        val boatRaceButton = ItemStack(Material.OAK_BOAT)
        val boatRaceMeta: ItemMeta? = boatRaceButton.itemMeta
        boatRaceMeta?.setDisplayName("${ChatColor.BLUE}Ивент гонки на лодках")
        boatRaceButton.itemMeta = boatRaceMeta

        gui.setItem(3, parkourButton)
        gui.setItem(5, boatRaceButton)

        player.openInventory(gui)
    }
}
