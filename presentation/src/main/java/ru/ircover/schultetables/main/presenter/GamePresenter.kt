package ru.ircover.schultetables.main.presenter

import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.ircover.schultetables.ActivityScope
import ru.ircover.schultetables.domain.*
import ru.ircover.schultetables.domain.usecase.ClickCellUseCase
import ru.ircover.schultetables.domain.usecase.GenerateTableUseCase
import ru.ircover.schultetables.domain.usecase.SaveResultUseCase
import ru.ircover.schultetables.util.DispatchersProvider
import ru.ircover.schultetables.util.launchCollect
import javax.inject.Inject

interface GameView : MvpView {
    @OneExecution
    fun openSettings()
    @AddToEndSingle
    fun showCells(cells: Matrix2D<SchulteTableCell>)
    @AddToEndSingle
    fun setExpectedCellText(text: String)
    @AddToEndSingle
    fun setCallback(callback: SchulteTableCallback)
    @OneExecution
    fun notifyWrongCell()
    @OneExecution
    fun openScoresList()
}

@InjectViewState
@ActivityScope
class GamePresenter @Inject constructor(private val game: SchulteTableGame,
                                        private val generateTableUseCase: GenerateTableUseCase,
                                        private val settingsWorker: SchulteTableSettingsWorker,
                                        private val clickCellUseCase: ClickCellUseCase,
                                        private val saveResultUseCase: SaveResultUseCase,
                                        dispatchersProvider: DispatchersProvider)
        : MvpPresenter<GameView>(), SchulteTableCallback {
    private var needToRefresh = true
    init {
        presenterScope.launch(dispatchersProvider.main()) {
            launchCollect(game.getCells()) {
                viewState.showCells(it)
            }
            launchCollect(game.getExpectedCell()) {
                viewState.setExpectedCellText(it.text)
            }
            launchCollect(settingsWorker.getChanges()) {
                when (it) {
                    SettingType.ColumnsCount, SettingType.RowsCount -> needToRefresh = true
                }
            }
            launchCollect(game.getFinishEvents()) { startTimeMillis ->
                needToRefresh = true
                saveResultUseCase.execute(startTimeMillis)
                viewState.openScoresList()
            }
        }
    }

    fun initView() {
        viewState.setCallback(this)
    }

    fun clickSettings() {
        viewState.openSettings()
    }

    fun clickRefresh() {
        refreshGame()
    }

    fun onResume() {
        if(needToRefresh) {
            refreshGame()
            needToRefresh = false
        }
    }

    override fun click(cell: SchulteTableCell) {
        presenterScope.launch {
            if(!clickCellUseCase.execute(game, cell)) {
                viewState.notifyWrongCell()
            }
        }
    }

    private fun refreshGame() {
        presenterScope.launch {
            game.refresh(generateTableUseCase.execute())
        }
    }
}