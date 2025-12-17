package io.tanguygab.yarmm.actions

import io.github.tanguygab.conditionalactions.actions.Action
import io.tanguygab.yarmm.MenuCloseReason
import io.tanguygab.yarmm.YARMM
import io.tanguygab.yarmm.tab
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class OpenAction(val yarmm: YARMM) : Action("^(?i)open:( )?") {

    override fun getSuggestion() = "open: <menu>"

    override fun execute(player: OfflinePlayer?, match: String) {
        if (player !is Player) return
        val menu = yarmm.menuManager.menus[parsePlaceholders(player, match)] ?: return

        player.tab?.let { yarmm.menuManager.openMenu(it, menu) }
    }

}