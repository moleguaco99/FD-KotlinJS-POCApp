external interface Anime {
    val id: Int
    val title: String
    val author: String
    val description: String
    val imageUrl: String
    val numberOfEpisodes: Int
    val videoUrl: String
}

data class AnimeRecord (
    override val id: Int,
    override val title: String,
    override val author: String,
    override val description: String,
    override val imageUrl: String,
    override val numberOfEpisodes: Int,
    override val videoUrl: String
) : Anime