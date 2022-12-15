package one.devsky.butlerjames.manager

import de.fruxz.ascend.extension.time.year
import de.fruxz.ascend.tool.timing.calendar.Calendar
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission

import net.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer
import one.devsky.butlerjames.ButlerJames

// Top-level function for testing if the bot has a set of permissions in a channel (or guild) and if not responds with an embed
fun testForPermissions(channelOrGuild: IPermissionContainer, vararg permission: Permission): Pair<Boolean, EmbedBuilder?> {
    val self = channelOrGuild.guild.getMember(ButlerJames.instance.jda.selfUser)!!
    val missingPermissions = permission.filter { channelOrGuild.getPermissionOverride(self)?.allowed?.contains(it)?.not() ?: !self.hasPermission(channelOrGuild, it) }

    if (missingPermissions.isEmpty()) return true to null

    val embed = EmbedBuilder()
        .setTitle("`        Missing permissions        `")
        .setDescription("I need the following permissions to do this:\n ${missingPermissions.joinToString(", ") { "`${it.name}`" }}")
        .setColor(0x2f3136)
        .setFooter("© ${Calendar.now().year} Butler James")

    return false to embed
}