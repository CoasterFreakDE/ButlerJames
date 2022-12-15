package one.devsky.butlerjames.listeners.utility

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.manager.RegisterManager

@SlashCommand("help", "Displays a list of commands")
class HelpCommand : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "help") return@with

        // All commands sorted by category
        val commands = RegisterManager.commandsWithCategories.entries.groupBy { it.value }.map { (category, commands) ->
            "**$category**\n" + commands.joinToString("\n") { "`/${it.key}`" }
        }.joinToString("\n\n")

        reply("Here is a list of all commands:\n\n$commands").setEphemeral(true).queue()
    }

}