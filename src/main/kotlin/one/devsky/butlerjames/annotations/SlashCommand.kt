package one.devsky.butlerjames.annotations

annotation class SlashCommand(
    val name: String,
    val description: String,
    val isNsfw: Boolean = false,
)