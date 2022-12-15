package one.devsky.butlerjames.listeners

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.interfaces.HasOptions
import java.net.URL

@SlashCommand("meme", "Get a random meme from reddit")
class MemeCommand : ListenerAdapter(), HasOptions {

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "subreddit", "The subreddit to get the meme from", false)
        )
    }


    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "meme") return@with

        var subreddit = getOption("subreddit")?.asString ?: "memes"

        // Get Json Contents from https://meme-api.com/gimme/$subreddit
        // Get the meme url from the json
        // Send the meme url as a message
        if (subreddit.startsWith("r/")) subreddit = subreddit.substring(2)

        val json = URL("https://meme-api.com/gimme/$subreddit").readText()
        val memeUrl = json.substringAfter("\"url\":\"").substringBefore("\"")
        reply(memeUrl).queue()
    }

}