package one.devsky.butlerjames

import io.github.cdimascio.dotenv.dotenv


// Initial function of the bot defining the main entry point with loggers and env variables.
fun main() {
    val dotenv = dotenv()
    val token = dotenv["DISCORD_TOKEN"]

    ButlerJames(token)
}