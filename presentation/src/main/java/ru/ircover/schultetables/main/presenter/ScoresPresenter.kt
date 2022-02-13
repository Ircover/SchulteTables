package ru.ircover.schultetables.main.presenter

import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.ircover.schultetables.ActivityScope
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableScoresRepository
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.util.DispatchersProvider
import ru.ircover.schultetables.util.launchCollect
import javax.inject.Inject

interface ScoresView : MvpView {
    @AddToEndSingle
    fun setScores(scores: List<SchulteTableScore>)
    @AddToEndSingle
    fun setSettingsValue(value: String)
}

@InjectViewState
@ActivityScope
class ScoresPresenter @Inject constructor(private val scoresRepository: SchulteTableScoresRepository,
                                          private val settingsWorker: SchulteTableSettingsWorker,
                                          dispatchersProvider: DispatchersProvider
)
    : MvpPresenter<ScoresView>() {

    init {
        presenterScope.launch(dispatchersProvider.main()) {
            launchCollect(scoresRepository.getChanges()) {
                loadScores()
            }
        }
        viewState.setSettingsValue(getSettingsValue())
    }

    private suspend fun loadScores() {
        val scores = scoresRepository.getTop10(settingsWorker.get())
        viewState.setScores(scores)
    }

    private fun getSettingsValue() = settingsWorker.get().let { settings ->
        "${settings.columnsCount}x${settings.rowsCount}"
    }
}