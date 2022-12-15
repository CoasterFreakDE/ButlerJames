package one.devsky.butlerjames.listeners.`fun`

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.manager.testForPermissions

@SlashCommand("segs-hack", "Tutorial on how to use the discord segs hack")
class SegsHackCommand : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "segs-hack") return@with

        // Test for permissions
        val (hasPermissions, embed) = testForPermissions(channel.asTextChannel(),
            Permission.MESSAGE_SEND
        )
        if (!hasPermissions) {
            replyEmbeds(embed!!.build()).setEphemeral(true).queue()
            return@with
        }

        reply("Send an image through discords GIF feature.\n" +
                "After that type in `s/e/gs`\n" +
                "Your done!").setEphemeral(true).queue()
    }

}