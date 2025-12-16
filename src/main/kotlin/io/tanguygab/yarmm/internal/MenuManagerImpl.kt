package io.tanguygab.yarmm.internal

import io.tanguygab.yarmm.api.MenuManager
import io.tanguygab.yarmm.api.MenuSession
import io.tanguygab.yarmm.api.inventory.MenuInventory
import io.tanguygab.yarmm.api.inventory.MenuItem
import io.tanguygab.yarmm.internal.inventory.MenuInventoryImpl
import me.neznamy.tab.shared.platform.TabPlayer

class MenuManagerImpl : MenuManager {

    override val menus = mutableMapOf<String, MenuInventory>()
    override val sessions = mutableMapOf<TabPlayer, MenuSession>()

    fun load() {
        val items = listOf(
            MenuItem("STONE", "Hi %animation:slots% {slot}", slots = listOf("0", "1", "%animation:slots%")),
            MenuItem("%animation:materials%", "<aqua>Hey %player%", "%animation:slots%", slots = listOf("3")),
            MenuItem("EMERALD", "<green>Bye", lore=listOf("<u>Heya", "<white><i:false><u>yeeee", "<bold>wat"), slots = listOf("8")),
        )

        val menu = MenuInventoryImpl("Hi <green>Test <red>Menu %server_uptime%", items, mutableListOf(), rows=5)
        val menu2 = MenuInventoryImpl("Hi %%", items, mutableListOf(), rows=5)
        menus["menu"] = menu
        menus["menu2"] = menu2
    }
    fun unload() {
        sessions.values.forEach { it.close()}
    }

    override fun openMenu(player: TabPlayer, menu: MenuInventory): MenuSession {
        closeMenu(player)
        return MenuSessionImpl(player, menu).apply { sessions[player] = this }
    }

    override fun closeMenu(player: TabPlayer) {
        sessions[player]?.close()
        sessions.remove(player);
    }
}