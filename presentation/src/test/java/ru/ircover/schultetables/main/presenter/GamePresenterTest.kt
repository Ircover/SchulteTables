package ru.ircover.schultetables.main.presenter

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import ru.ircover.schultetables.domain.*
import ru.ircover.schultetables.domain.usecase.ClickCellUseCase
import ru.ircover.schultetables.domain.usecase.GenerateTableUseCase
import ru.ircover.schultetables.domain.usecase.SaveResultUseCase
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
    private lateinit var saveResultUseCase: SaveResultUseCase

    private lateinit var cellsFlow: MutableSharedFlow<Matrix2D<SchulteTableCell>>
    private lateinit var expectedCellFlow: MutableSharedFlow<SchulteTableCell>
    private lateinit var settingsChangesFlow: MutableSharedFlow<SettingType>
    private lateinit var finishEventFlow: MutableSharedFlow<Long>

    @Before
    fun setup() {
        view = mock()
        game = mock()
        generateTableUseCase = mock()
        settingsWorker = mock()
        clickCellUseCase = mock()
        saveResultUseCase = mock()

        cellsFlow = MutableSharedFlow()
        expectedCellFlow = MutableSharedFlow()
        settingsChangesFlow = MutableSharedFlow()
        finishEventFlow = MutableSharedFlow()

        `when`(game.getCells()).thenReturn(cellsFlow)
        `when`(game.getExpectedCell()).thenReturn(expectedCellFlow)
        `when`(settingsWorker.getChanges()).thenReturn(settingsChangesFlow)
        `when`(game.getFinishEvents()).thenReturn(finishEventFlow)

        sut = GamePresenter(game, generateTableUseCase, settingsWorker, clickCellUseCase, saveResultUseCase,
            TestDispatchersProvider(testCoroutineDispatcher)
        ).apply {
            attachView(view)
        }
    }

    @Test
    fun game_getCells() = runBlockingTest {
        val cells = Matrix2D(arrayOf(arrayOf(SchulteTableCell("test", 1))))

        cellsFlow.emit(cells)

        verify(view).showCells(cells)
    }

    @Test
    fun game_getExpectedCell() = runBlockingTest {
        val cell = SchulteTableCell("test", 1)

        expectedCellFlow.emit(cell)

        verify(view).setExpectedCellText("test")
    }

    @Test
    fun settingsWorker_getChanges() = runBlockingTest {
        sut.onResume()//сначала сбросить флаг обновления

        settingsChangesFlow.emit(SettingType.ColumnsCount)

        verifyRefreshOnAction(sut::onResume)
    }

    @Test
    fun game_getFinishEvents() = runBlockingTest {
        sut.onResume()//сначала сбросить флаг обновления

        finishEventFlow.emit(15L)

        verify(saveResultUseCase).execute(15L)
        verify(view).openScoresList()
        verifyRefreshOnAction(sut::onResume)
    }

    @Test
    fun initView() {
        sut.initView()

        verify(view).setCallback(sut)
    }

    @Test
    fun clickSettings() {
        sut.clickSettings()

        verify(view).openSettings()
    }

    @Test
    fun clickRefresh() = runBlockingTest {
        verifyRefreshOnAction(sut::clickRefresh)
    }

    @Test
    fun click_SchulteTableCell_RightCell() = runBlockingTest {
        val cell = SchulteTableCell("test", 1)
        `when`(clickCellUseCase.execute(game, cell)).thenReturn(true)

        sut.click(cell)

        verify(view, never()).notifyWrongCell()
        verify(clickCellUseCase).execute(game, cell)
    }

    @Test
    fun click_SchulteTableCell_WrongCell() = runBlockingTest {
        val cell = SchulteTableCell("test", 1)
        `when`(clickCellUseCase.execute(game, cell)).thenReturn(false)

        sut.click(cell)

        verify(view).notifyWrongCell()
    }

    private suspend fun verifyRefreshOnAction(action: () -> Unit) {
        val startData = SchulteTableStartData(Matrix2D.empty(), SchulteTableCell.EMPTY, 0L)
        `when`(generateTableUseCase.execute()).thenReturn(startData)

        action()

        verify(game).refresh(startData)
    }
}