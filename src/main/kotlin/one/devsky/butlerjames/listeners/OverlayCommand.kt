package one.devsky.butlerjames.listeners

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.utils.FileUpload
import one.devsky.butlerjames.annotations.SlashCommand
import one.devsky.butlerjames.extensions.overlayImage
import one.devsky.butlerjames.interfaces.HasOptions
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

@SlashCommand("overlay", "Overlay a image on another")
class OverlayCommand : ListenerAdapter(), HasOptions {
    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "base-url", "The url as a background", false),
            OptionData(OptionType.ATTACHMENT, "base-attachment", "Background as a file", false),
            OptionData(OptionType.USER, "base-user", "Background as a user avatar", false),
            OptionData(OptionType.STRING, "top-url", "The url of the overlay", false),
            OptionData(OptionType.ATTACHMENT, "top-attachment", "Overlay as a file", false),
            OptionData(OptionType.INTEGER, "opacity", "Opacity from 0 to 100 (Default: 50)", false),
            OptionData(OptionType.BOOLEAN, "hidden", "Should the result be limited to you? (Default: false)", false),
        )
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = with(event) {
        if(name != "overlay") return@with

        val baseUrl = getOption("base-url")?.asString
        val baseAttachment = getOption("base-attachment")?.asAttachment
        val baseUser = getOption("base-user")?.asUser

        val topUrl = getOption("top-url")?.asString
        val topAttachment = getOption("top-attachment")?.asAttachment

        val hidden = getOption("hidden")?.asBoolean ?: false

        if (baseUrl == null && baseAttachment == null && baseUser == null) {
            reply("You need to provide a background").setEphemeral(true).queue()
            return@with
        }

        if (topUrl == null && topAttachment == null) {
            reply("You need to provide a overlay").setEphemeral(true).queue()
            return@with
        }

        val base = when {
            baseUrl != null -> ImageIO.read(URL(baseUrl))
            baseAttachment != null -> ImageIO.read(URL(baseAttachment.url))
            baseUser != null -> ImageIO.read(URL(baseUser.effectiveAvatarUrl.replace("webp", "png")))
            else -> null
        }

        val top = when {
            topUrl != null -> ImageIO.read(URL(topUrl))
            topAttachment != null -> ImageIO.read(URL(topAttachment.url))
            else -> null
        }

        if (base == null || top == null) {
            reply("Something went wrong").setEphemeral(true).queue()
            return@with
        }

        val opacity = getOption("opacity")?.asLong?.toFloat()?.div(100) ?: 0.5f

        val result = base.overlayImage(top, opacity)

        // Temporary safe as a file
        val file = File.createTempFile("overlay", ".png")
        ImageIO.write(result, "png", file)

        val fileUpload = FileUpload.fromData(file, "overlay.png")
        reply("").addFiles(fileUpload).setEphemeral(hidden).queue()
    }

}