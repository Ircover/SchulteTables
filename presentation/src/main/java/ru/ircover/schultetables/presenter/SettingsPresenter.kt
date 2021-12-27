package ru.ircover.schultetables.presenter

import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.alias.OneExecution
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.domain.SettingType
import ru.ircover.schultetables.util.DispatchersProvider
import javax.inject.Inject

interface SettingsView : MvpView {
    @OneExecution
    fun setMaxSizePositions(maxColumnsPositions: Int, maxRowsPositions: Int)
    @OneExecution
    fun setColumnsCount(progress: Int, count: Int)
    @OneExecution
    fun setRowsCount(progress: Int, count: Int)
}

private const val MAX_COLUMNS_POSITIONS = 5
private const val MAX_ROWS_POSITIONS = 5
private const val MIN_COLUMNS_COUNT = 4
private const val MIN_ROWS_COUNT = 4

@InjectViewState
class SettingsPresenter @Inject constructor(private val settingsWorker: SchulteTableSettingsWorker,
                                            private val dispatchersProvider: DispatchersProvider)
    : MvpPresenter<SettingsView>() {
    private var settingsModel = settingsWorker.get()

    fun initView() {
        viewState.apply {
            setMaxSizePositions(MAX_COLUMNS_POSITIONS, MAX_ROWS_POSITIONS)
            setColumnsCount(settingsModel.columnsCount - MIN_COLUMNS_COUNT, settingsModel.columnsCount)
            setRowsCount(settingsModel.rowsCount - MIN_ROWS_COUNT, settingsModel.rowsCount)
        }
    }

    fun setColumnsCountProgress(progress: Int) {
        val newValue = progress + MIN_COLUMNS_COUNT
        if(newValue != settingsModel.columnsCount) {
            settingsModel = settingsModel.copy(columnsCount = newValue)
            viewState.setColumnsCount(
                settingsModel.columnsCount - MIN_COLUMNS_COUNT,
                settingsModel.columnsCount
            )
            presenterScope.launch(dispatchersProvider.main()) {
                settingsWorker.save(settingsModel, SettingType.ColumnsCount)
            }
        }
    }

    fun setRowsCountProgress(progress: Int) {
        val newValue = progress + MIN_ROWS_COUNT
        if(newValue != settingsModel.rowsCount) {
            settingsModel = settingsModel.copy(rowsCount = newValue)
            viewState.setRowsCount(
                settingsModel.rowsCount - MIN_ROWS_COUNT,
                settingsModel.rowsCount
            )
            presenterScope.launch(dispatchersProvider.main()) {
                settingsWorker.save(settingsModel, SettingType.RowsCount)
            }
        }
    }
}