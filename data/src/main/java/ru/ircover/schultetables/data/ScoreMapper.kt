package ru.ircover.schultetables.data

import ru.ircover.schultetables.Serializer
import ru.ircover.schultetables.dateFromMillis
import ru.ircover.schultetables.deserialize
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableSettings

interface ScoreMapper {
    fun map(score: Score): SchulteTableScore
    fun map(score: SchulteTableScore): Score
    fun mapSettings(settings: SchulteTableSettings): String
    fun mapSettings(settings: String): SchulteTableSettings
}

class ScoreMapperImpl(private val serializer: Serializer): ScoreMapper {
    override fun map(score: Score) = SchulteTableScore(
        score.duration,
        mapSettings(score.settings),
        dateFromMillis(score.dateMillis)
    )

    override fun map(score: SchulteTableScore) = Score(
        0,
        score.duration,
        mapSettings(score.settings),
        score.dateTime.timeInMillis
    )

    override fun mapSettings(settings: SchulteTableSettings): String =
        serializer.serialize(settings)

    override fun mapSettings(settings: String): SchulteTableSettings =
        serializer.deserialize(settings)

}