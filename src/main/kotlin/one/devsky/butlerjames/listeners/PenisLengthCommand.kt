package one.devsky.butlerjames.listeners

import de.fruxz.ascend.extension.data.randomInt
import de.fruxz.ascend.extension.time.year
import de.fruxz.ascend.tool.timing.calendar.Calendar
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.interfaces.HasOptions

@SlashCommand("penislength", "Calculates the length of x's penis", true)
class PenisLengthCommand: ListenerAdapter(), HasOptions {

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.USER, "user", "The user to calculate from", true)
        )
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "penislength") return@with

        if(guild == null) {
            return@with reply("This command can only be performed on guilds.").queue()
        }

        val user = getOption("user")!!.asMember ?: return@with reply("Cannot get information of this user.").setEphemeral(true).queue()

        // Calculate the penis length based on user factors like connections, account age and other things
        var cm = 0

        with(user) {
            when {
                isOwner -> cm += 10
                isBoosting -> cm += 3
                isTimedOut -> cm -= 5
                avatarUrl != null -> cm++
                activities.isNotEmpty() -> cm += 1
                else -> {}
            }
        }

        cm += Calendar.now().year - user.timeCreated.year
        cm +=  Calendar.now().year - user.timeJoined.year
        cm += randomInt(0..5)

        reply("${user.asMention}'s Penis is ${cm}cm long.").queue()
    }
}