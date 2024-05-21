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
            "${ChatColor.DARK_BLUE}Event Manager" -> {
                event.isCancelled = true
                when (event.currentItem?.type) {
                    Material.FEATHER -> {
                        selectedEventType = "Ивент паркур"
                        openMapSelectionGui(player)
                    }
                    Material.OAK_BOAT -> {
                        selectedEventType = "Ивент гонки на лодках"
                        openMapSelectionGui(player)
                    }
                    else -> return
                }
            }
            "${ChatColor.GOLD}Current Event" -> {
                event.isCancelled = true
            }
            "${ChatColor.DARK_GREEN}Выбор карты" -> {
                event.isCancelled = true
                val selectedMap = event.currentItem?.itemMeta?.displayName
                if (selectedMap != null) {
                    openConfirmationGui(player, selectedMap)
                }
            }
            "${ChatColor.RED}Подтверждение" -> {
                event.isCancelled = true
                when (event.currentItem?.type) {
                    Material.LIME_WOOL -> startEvent(player)
                    Material.RED_WOOL -> player.closeInventory()
                    else -> return
                }
            }
        }
    }

    private fun openMapSelectionGui(player: Player) {
        val gui: Inventory = plugin.server.createInventory(null, 27, "${ChatColor.DARK_GREEN}Выбор карты")

        val map1 = ItemStack(Material.MAP)
        val map1Meta: ItemMeta? = map1.itemMeta
        map1Meta?.setDisplayName("${ChatColor.RED}Карта 1")
        map1.itemMeta = map1Meta

        val map2 = ItemStack(Material.MAP)
        val map2Meta: ItemMeta? = map2.itemMeta
        map2Meta?.setDisplayName("${ChatColor.RED}Карта 2")
        map2.itemMeta = map2Meta

        val glassPane = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
        val glassPaneMeta: ItemMeta? = glassPane.itemMeta
        glassPaneMeta?.setDisplayName(" ")
        glassPane.itemMeta = glassPaneMeta

        for (i in 0 until gui.size) {
            gui.setItem(i, glassPane)
        }

        gui.setItem(11, map1)
        gui.setItem(15, map2)

        player.openInventory(gui)
    }

    private fun openConfirmationGui(player: Player, selectedMap: String) {
        val gui: Inventory = plugin.server.createInventory(null, 27, "${ChatColor.RED}Подтверждение")

        val confirm = ItemStack(Material.LIME_WOOL)
        val confirmMeta: ItemMeta? = confirm.itemMeta
        confirmMeta?.setDisplayName("${ChatColor.GREEN}Подтвердить")
        confirm.itemMeta = confirmMeta

        val cancel = ItemStack(Material.RED_WOOL)
        val cancelMeta: ItemMeta? = cancel.itemMeta
        cancelMeta?.setDisplayName("${ChatColor.RED}Отмена")
        cancel.itemMeta = cancelMeta

        val glassPane = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
        val glassPaneMeta: ItemMeta? = glassPane.itemMeta
        glassPaneMeta?.setDisplayName(" ")
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
        plugin.server.broadcastMessage("${ChatColor.GREEN}${ChatColor.BOLD}Начинается $eventTitle!")
    }
}
