package io.tanguygab.yarmm.api

import io.tanguygab.yarmm.api.inventory.MenuInventory
import io.tanguygab.yarmm.api.inventory.MenuPage
import me.neznamy.tab.shared.platform.TabPlayer

interface MenuSession {

    val player: TabPlayer
    val menu: MenuInventory

    //val page: MenuPage

    fun close()

}