package one.devsky.butlerjames.listeners.`fun`

import de.fruxz.ascend.extension.data.randomInt
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.interfaces.HasOptions
import java.time.LocalDate

@SlashCommand("hack", "Hacks a user (FUN)")
class HackCommand : ListenerAdapter(), HasOptions {
    override fun getOptions(): List<OptionData> {
        return listOf(OptionData(OptionType.USER, "user", "The user to hack"))
    }


    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "hack") return@with
        val user = getOption("user")?.asUser ?: return@with reply("Cannot get information of this user.").setEphemeral(true).queue()

        deferReply(false).setContent("Starting account injection on ${user.asMention}").setAllowedMentions(emptySet()).queue()

        val faker = Faker(fakerConfig { locale = "de"  })
        val age = randomInt(10..21)
        val fakeUser = FakeUser(
            faker.name.firstName(),
            faker.name.lastName(),
            faker.internet.safeEmail(),
            faker.person.birthDate(age = age.toLong(), at = LocalDate.now()).toString(),
            age,
            faker.address.fullAddress()
        )

        Thread.sleep(3000)
        hook.editOriginal("Injection successful! Waiting for account data..").queue()

        Thread.sleep(3000)
        hook.editOriginal("Account data received!").queue()

        Thread.sleep(1000)

        hook.editOriginal("Account injection on ${user.asMention} successful!").setEmbeds(
            fakeUser.toEmbed()
        ).setAllowedMentions(emptySet()).queue()
    }

    internal data class FakeUser(
        val surname: String,
        val lastName: String,
        val email: String,
        val birthday: String,
        val age: Int,
        val address: String,
        val ip: String = "${randomInt(0..255)}.${randomInt(0..255)}.${randomInt(0..255)}.${randomInt(0..255)}",
    ) {
        fun toEmbed() = EmbedBuilder()
            .setColor(0x2f3136)
            .setTitle("Hacked Account")
            .addField("Name", "$surname $lastName", true)
            .addField("Email", email, true)
            .addField("Birthday", birthday, true)
            .addField("Age", age.toString(), true)
            .addField("Address", address, true)
            .addField("IP", ip, true)
            .build()
    }
}