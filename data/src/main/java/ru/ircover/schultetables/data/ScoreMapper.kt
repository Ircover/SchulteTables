package ru.ircover.schultetables.data

import com.google.gson.Gson
import ru.ircover.schultetables.dateFromMillis
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableSettings

interface ScoreMapper {
    fun map(score: Score): SchulteTableScore
    fun map(score: SchulteTableScore): Score
    fun mapSettings(settings: SchulteTableSettings): String
    fun mapSettings(settings: String): SchulteTableSettings
}

class ScoreMapperImpl(private val gson: Gson): ScoreMapper {
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
        gson.toJson(settings)

    override fun mapSettings(settings: String): SchulteTableSettings =
        gson.fromJson(settings, SchulteTableSettings::class.java)

}