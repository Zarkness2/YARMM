package io.tanguygab.yarmm.actions

import io.github.tanguygab.conditionalactions.actions.Action
import io.tanguygab.yarmm.MenuCloseReason
import io.tanguygab.yarmm.YARMM
import io.tanguygab.yarmm.tab
import me.neznamy.tab.shared.TAB
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class PromptAction(val yarmm: YARMM) : Action("^(?i)prompt:( )?".toRegex()) {

    override fun getSuggestion() = "prompt: <name> <cooldown> <message> [default:<default value>]"

    override fun execute(player: OfflinePlayer?, match: String) {
        if (player !is Player) return

        val args = parsePlaceholders(player, match).split(" ", limit = 3)
        val name = args[0]
        val cooldown = args.getOrNull(1)?.toLongOrNull() ?: -1L
        val message = args.getOrNull(2)
        val default = message?.substringAfterLast("default:", yarmm.lang.promptNoValue) ?: yarmm.lang.promptNoValue
        player.sendMessage(if (message != null)
            MiniMessage.miniMessage().deserialize(message.substringBeforeLast("default:"))
        else yarmm.lang.getPromptDefault(name))

        val player = player.tab!!
        yarmm.menuManager.closeMenu(player, MenuCloseReason.PROMPT)

        val value = yarmm.playerListener.prompt(player, cooldown)?.replace("%", "") ?: default

        TAB.getInstance().placeholderManager.registerPlayerPlaceholder("%prompt-$name%", -1) { value }
        yarmm.menuManager.sessions[player]?.reopen()
    }
}