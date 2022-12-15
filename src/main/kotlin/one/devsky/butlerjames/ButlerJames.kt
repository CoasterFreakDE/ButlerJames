package one.devsky.butlerjames

import de.fruxz.ascend.extension.logging.getItsLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import one.devsky.butlerjames.manager.RegisterManager.registerAll
import one.devsky.butlerjames.manager.RegisterManager.registerCommands

class ButlerJames(token: String) {

    companion object {
        lateinit var instance: ButlerJames
    }

    val jda: JDA

    init {
        instance = this
        getItsLogger().info("Booting Butler James...")

        jda = JDABuilder.createLight(token)
            .setActivity(Activity.listening("to your commands"))
            .registerAll()
            .build()
            .awaitReady()
            .registerCommands()
    }
}