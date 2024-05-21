package me.draimgoose.listeners

import me.draimgoose.Main
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class GuiEventListener(private val plugin: Main) : Listener {

    private var selectedEventType: String? = null

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        when (event.view.title) {
            ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigString("gui.event_manager_title")) -> {
                event.isCancelled = true
                when (event.currentItem?.type) {
                    Material.FEATHER -> {
                        selectedEventType = plugin.getConfigString("gui.parkour_event_item.display_name")
                        openMapSelectionGui(player)
                    }
                    Material.OAK_BOAT -> {
                        selectedEventType = plugin.getConfigString("gui.boat_race_event_item.display_name")
                        openMapSelectionGui(player)
                    }
                    else -> return
                }
            }
            ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigString("gui.current_event_title")) -> {
                event.isCancelled = true
            }
            ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigString("gui.map_selection_title")) -> {
                event.isCancelled = true
                val selectedMap = event.currentItem?.itemMeta?.displayName
                if (selectedMap != null) {
                    openConfirmationGui(player, selectedMap)
                }
            }
            ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigString("gui.confirmation_title")) -> {
                event.isCancelled = true
                when (event.currentItem?.type) {
                    Material.valueOf(plugin.getConfigString("gui.confirm_item.material")) -> startEvent(player)
                    Material.valueOf(plugin.getConfigString("gui.cancel_item.material")) -> player.closeInventory()
                    else -> return
                }
            }
        }
    }

    private fun openMapSelectionGui(player: Player) {
        val gui: Inventory = plugin.server.createInventory(null, 27,
            ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigString("gui.map_selection_title")))

        val glassPane = ItemStack(Material.valueOf(plugin.getConfigString("gui.glass_pane_item.material")))
        val glassPaneMeta: ItemMeta? = glassPane.itemMeta
        glassPaneMeta?.setDisplayName(ChatColor.translateAlternateColorCodes('&',
            plugin.getConfigString("gui.glass_pane_item.display_name")))
        glassPane.itemMeta = glassPaneMeta

        for (i in 0 until gui.size) {
            gui.setItem(i, glassPane)
        }

        val mapItems = plugin.getConfigStringList("gui.map_items")
        for ((index, mapItem) in mapItems.withIndex()) {
            val map = ItemStack(Material.valueOf(mapItem["material"]!!))
            val mapMeta: ItemMeta? = map.itemMeta
            mapMeta?.setDisplayName(ChatColor.translateAlternateColorCodes('&', mapItem["display_name"]!!))
            map.itemMeta = mapMeta
            gui.setItem(11 + index * 2, map)
        }

        player.openInventory(gui)
    }

    private fun openConfirmationGui(player: Player, selectedMap: String) {
        val gui: Inventory = plugin.server.createInventory(null, 27,
            ChatColor.translateAlternateColorCodes('&',
                plugin.getConfigString("gui.confirmation_title")))

        val confirm = ItemStack(Material.valueOf(plugin.getConfigString("gui.confirm_item.material")))
        val confirmMeta: ItemMeta? = confirm.itemMeta
        confirmMeta?.setDisplayName(ChatColor.translateAlternateColorCodes('&',
            plugin.getConfigString("gui.confirm_item.display_name")))
        confirm.itemMeta = confirmMeta

        val cancel = ItemStack(Material.valueOf(plugin.getConfigString("gui.cancel_item.material")))
        val cancelMeta: ItemMeta? = cancel.itemMeta
        cancelMeta?.setDisplayName(ChatColor.translateAlternateColorCodes('&',
            plugin.getConfigString("gui.cancel_item.display_name")))
        cancel.itemMeta = cancelMeta

        val glassPane = ItemStack(Material.valueOf(plugin.getConfigString("gui.glass_pane_item.material")))
        val glassPaneMeta: ItemMeta? = glassPane.itemMeta
        glassPaneMeta?.setDisplayName(ChatColor.translateAlternateColorCodes('&',
            plugin.getConfigString("gui.glass_pane_item.display_name")))
        glassPane.itemMeta = glassPaneMeta

        for (i in 0 until gui.size) {
            gui.setItem(i, glassPane)
        }

        gui.setItem(11, confirm)
        gui.setItem(15, cancel)

        player.openInventory(gui)
    }

    private fun startEvent(player: Player) {
        val eventTitle = selectedEventType ?: "Ивент"
        plugin.currentEvent = eventTitle
        player.closeInventory()
        plugin.server.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
            plugin.getConfigString("chat.event_start").replace("%event%", eventTitle)))
    }
}
