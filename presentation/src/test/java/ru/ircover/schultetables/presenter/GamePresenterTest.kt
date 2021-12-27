package ru.ircover.schultetables.presenter

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import ru.ircover.schultetables.domain.SchulteTableGame
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.domain.usecase.ClickCellUseCase
import ru.ircover.schultetables.domain.usecase.GenerateTableUseCase
import ru.ircover.schultetables.utils.TestDispatchersProvider

@ExperimentalCoroutinesApi
class GamePresenterTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var sut: GamePresenter
    private lateinit var view: GameView
    private lateinit var game: SchulteTableGame
    private lateinit var generateTableUseCase: GenerateTableUseCase
    private lateinit var settingsWorker: SchulteTableSettingsWorker
    private lateinit var clickCellUseCase: ClickCellUseCase

    @Before
    fun setup() {
        view = mock()
        game = mock()
        generateTableUseCase = mock()
        settingsWorker = mock()
        clickCellUseCase = mock()

        `when`(game.getCells()).thenReturn(emptyFlow())
        `when`(game.getExpectedCell()).thenReturn(emptyFlow())
        `when`(settingsWorker.getChanges()).thenReturn(emptyFlow())
        `when`(game.getFinishEvents()).thenReturn(emptyFlow())

        sut = GamePresenter(game, generateTableUseCase, settingsWorker, clickCellUseCase,
            TestDispatchersProvider(testCoroutineDispatcher)
        ).apply {
            attachView(view)
        }
    }

    @Test
    fun clickSettings() {
        sut.clickSettings()

        verify(view).openSettings()
    }
}