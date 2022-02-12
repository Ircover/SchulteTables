package ru.ircover.schultetables.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import ru.ircover.schultetables.TimeManager
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableScoresRepository
import ru.ircover.schultetables.domain.SchulteTableSettings
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import java.util.*

@ExperimentalCoroutinesApi
class SaveResultUseCaseImplTest {
    private lateinit var timeManager: TimeManager
    private lateinit var repository: SchulteTableScoresRepository
    private lateinit var settingsWorker: SchulteTableSettingsWorker
    private lateinit var sut: SaveResultUseCaseImpl

    @Before
    fun setup() {
        timeManager = mock()
        repository = mock()
        settingsWorker = mock()
        sut = SaveResultUseCaseImpl(timeManager, repository, settingsWorker)
    }

    @Test
    fun execute() = runBlockingTest {
        val nowMillis = 1644655188000L
        val startTimeMillis = nowMillis - 50L
        val settings = SchulteTableSettings(1, 1)
        val expectedDate = GregorianCalendar(2022, 1, 12, 11, 39, 48)
        `when`(timeManager.getNowMillis()).thenReturn(nowMillis)
        `when`(settingsWorker.get()).thenReturn(settings)

        sut.execute(startTimeMillis)

        verify(repository).add(SchulteTableScore(50L, settings, expectedDate))
    }
}