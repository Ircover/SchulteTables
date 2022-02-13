package ru.ircover.schultetables.main.presenter

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableScoresRepository
import ru.ircover.schultetables.domain.SchulteTableSettings
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.utils.TestDispatchersProvider
import java.util.*

@ExperimentalCoroutinesApi
class ScoresPresenterTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val settings = SchulteTableSettings(3, 2)

    private lateinit var sut: ScoresPresenter
    private lateinit var view: ScoresView
    private lateinit var scoresRepository: SchulteTableScoresRepository
    private lateinit var settingsWorker: SchulteTableSettingsWorker

    private lateinit var scoresChangesFlow: MutableSharedFlow<Unit>

    @Before
    fun setup() {
        view = mock()
        scoresRepository = mock()
        settingsWorker = mock()
        scoresChangesFlow = MutableSharedFlow()

        `when`(scoresRepository.getChanges()).thenReturn(scoresChangesFlow)
        `when`(settingsWorker.get()).thenReturn(settings)

        sut = ScoresPresenter(scoresRepository, settingsWorker,
            TestDispatchersProvider(testCoroutineDispatcher)
        ).apply {
            attachView(view)
        }
    }

    @Test
    fun init_setSettingsValue() {
        //запуск должен был быть в конструкторе
        verify(view).setSettingsValue("3x2")
    }

    @Test
    fun scoresRepository_getChanges() = runBlockingTest {
        val scores = listOf(SchulteTableScore(0L, SchulteTableSettings(3, 2), Calendar.getInstance()))
        `when`(scoresRepository.getTop10(settings)).thenReturn(scores)

        scoresChangesFlow.emit(Unit)

        verify(view).setScores(scores)
    }
}