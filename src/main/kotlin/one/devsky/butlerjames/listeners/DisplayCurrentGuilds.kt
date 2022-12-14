package one.devsky.butlerjames.listeners

import de.fruxz.ascend.extension.logging.getItsLogger
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DisplayCurrentGuilds : ListenerAdapter() {


    override fun onReady(event: ReadyEvent) {
        event.jda.guilds.size
            .let { getItsLogger().info("${event.jda.selfUser.name} is now in $it guilds") }
    }

}