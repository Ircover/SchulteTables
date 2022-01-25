package ru.ircover.schultetables.app

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.ircover.schultetables.data.*
import ru.ircover.schultetables.domain.SchulteTableScoresRepository
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideSchulteTableScoresRepositoryImpl(scoresDao: ScoresDao,
                                                mapper: ScoreMapper): SchulteTableScoresRepository =
        SchulteTableScoresRepositoryImpl(scoresDao, mapper)

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "schulteTableDB"
    ).build()

    @Singleton
    @Provides
    fun provideScoresDao(dataBase: AppDataBase): ScoresDao = dataBase.scoresDao()


    @Singleton
    @Provides
    fun provideScoreMapper(gson: Gson): ScoreMapper = ScoreMapperImpl(gson)
}