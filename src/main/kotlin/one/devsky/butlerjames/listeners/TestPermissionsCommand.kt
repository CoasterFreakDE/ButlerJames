package one.devsky.butlerjames.listeners

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.interfaces.HasSubcommands
import one.devsky.butlerjames.manager.testForPermissions

@SlashCommand("testpermissions", "Test if the bot has a set of permissions in a channel (or guild) and if not responds with an embed")
class TestPermissionsCommand : ListenerAdapter(), HasSubcommands {
    private fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "permission", "The permission to test for", true)
                    .addChoices(Permission.values().map { Command.Choice(it.name, it.name) }.take(25))
        )
    }

    override fun getSubCommands(): List<SubcommandData> {
        return listOf(
            SubcommandData("single", "Test if the bot has a set of permissions in a channel or the guild")
                .addOptions(getOptions()),
            SubcommandData("all", "Test for all permissions")
        )
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "testpermissions") return@with

        val permissions: List<Permission> = when(subcommandName) {
            "single" ->  getOption("permission")?.asString?.let { listOf(Permission.valueOf(it)) } ?: emptyList()
            "all" -> Permission.values().toList()
            else -> emptyList()
        }

        val (hasPermissions, embed) = testForPermissions(channel.asTextChannel(), *permissions.toTypedArray())
        if (hasPermissions) {
            reply("I have all the permissions").setEphemeral(true).queue()
        } else {
            replyEmbeds(embed!!.build()).setEphemeral(true).queue()
        }
    }

}