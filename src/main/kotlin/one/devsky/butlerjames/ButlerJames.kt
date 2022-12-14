package one.devsky.butlerjames

import de.fruxz.ascend.extension.logging.getItsLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

class ButlerJames(token: String) {

    private val jda: JDA

    init {
        getItsLogger().info("Booting Butler James...")

        jda = JDABuilder.createLight(token)
            .setActivity(Activity.listening("to your commands"))
            .build()
    }
}