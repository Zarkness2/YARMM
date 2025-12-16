package io.tanguygab.yarmm.internal.inventory

import io.tanguygab.yarmm.api.inventory.MenuInventory
import io.tanguygab.yarmm.api.inventory.MenuPage
import io.tanguygab.yarmm.api.inventory.MenuItem
import io.tanguygab.yarmm.internal.MenuData
import me.neznamy.tab.shared.platform.TabPlayer
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

class MenuInventoryImpl(
    override val title: String,

    override val items: List<MenuItem>,
    override val pages: List<MenuPage>,

    override val type: InventoryType? = null,
    override val rows: Int? = null
) : MenuInventory {

    override fun get(player: TabPlayer, data: MenuData): Inventory {
        val title = MiniMessage.miniMessage().deserialize(data.title.get())
        val p = player.player as Player

        return if (rows != null) Bukkit.createInventory(p, (rows + 1) * 9, title)
        else Bukkit.createInventory(p, type ?: InventoryType.CHEST, title)
    }
}