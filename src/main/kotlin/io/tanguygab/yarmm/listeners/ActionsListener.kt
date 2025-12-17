package io.tanguygab.yarmm.listeners

import io.github.tanguygab.conditionalactions.events.ActionsRegisterEvent
import io.tanguygab.yarmm.YARMM
import io.tanguygab.yarmm.actions.CloseAction
import io.tanguygab.yarmm.actions.OpenAction
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ActionsListener(val plugin: YARMM) : Listener {

    @EventHandler
    fun onActionsLoad(e: ActionsRegisterEvent) {
        e.addActions(
            CloseAction(plugin),
            OpenAction(plugin),
        )
    }

}