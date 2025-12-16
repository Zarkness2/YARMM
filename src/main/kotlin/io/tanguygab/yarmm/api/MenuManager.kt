package io.tanguygab.yarmm.api

import io.tanguygab.yarmm.api.inventory.MenuInventory
import me.neznamy.tab.shared.platform.TabPlayer

interface MenuManager {

    val menus: Map<String, MenuInventory>
    val sessions: Map<TabPlayer, MenuSession>

    fun openMenu(player: TabPlayer, menu: MenuInventory): MenuSession
    fun closeMenu(player: TabPlayer)
}