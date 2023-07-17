package server.schema.models

data class Audio(
    override val id: Int,
    override val title: String? = null,
    override val duration: Int? = null,
    val fileType: String? = null,
) : Media

class AudioService {
    private val audios = listOf(
        Audio(1, "18-06-23-Cesena", 12, "mp3"),
        Audio(2, "test-audio", 5, "mkv"),
        Audio(3, "18-06-23-Cesena"),
    )

    fun getAudios(ids: List<Int>): List<Audio> = audios.filter { ids.contains(it.id) }
}
