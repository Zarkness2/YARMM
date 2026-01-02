package io.tanguygab.yarmm.listeners

import io.tanguygab.yarmm.YARMM
import io.tanguygab.yarmm.tab
import me.neznamy.tab.api.TabPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class PlayerListener(val plugin: YARMM) : Listener {

    private val prompts = mutableMapOf<TabPlayer, CompletableFuture<String>>()

    fun prompt(player: TabPlayer, cooldown: Long): String? {
        val future = CompletableFuture<String>()
        prompts[player] = future
        return try {
            if (cooldown == -1L) future.get()
            else future.get(cooldown, TimeUnit.MILLISECONDS)
        } catch (_: Exception) { null }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onChat(@Suppress("DEPRECATION") e: AsyncPlayerChatEvent) {
        val player = e.player.tab
        if (player !in plugin.menuManager.sessions) return

        e.isCancelled = true
        if (player!! !in prompts) return

        prompts[player]!!.complete(e.message)
        prompts.remove(player)
    }

}