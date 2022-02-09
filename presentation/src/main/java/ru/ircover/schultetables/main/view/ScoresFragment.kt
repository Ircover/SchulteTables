package ru.ircover.schultetables.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ircover.schultetables.databinding.FragmentScoresBinding
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.main.adapter.ScoresAdapter
import ru.ircover.schultetables.main.presenter.ScoresPresenter
import ru.ircover.schultetables.main.presenter.ScoresView
import ru.ircover.schultetables.util.getPresentationComponent
import javax.inject.Inject

class ScoresFragment : MvpAppCompatFragment(), ScoresView {
    private var binding: FragmentScoresBinding? = null
    private val scoresAdapter = ScoresAdapter()

    @Inject
    @InjectPresenter
    lateinit var presenter: ScoresPresenter

    @ProvidePresenter
    fun providePresenter(): ScoresPresenter {
        return presenter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getPresentationComponent()!!.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoresBinding.inflate(inflater)
        return FragmentScoresBinding.inflate(inflater).apply {
            binding = this
            rvScores.apply {
                layoutManager = LinearLayoutManager(inflater.context, LinearLayoutManager.VERTICAL, false)
                adapter = scoresAdapter
            }
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun setScores(scores: List<SchulteTableScore>) {
        scoresAdapter.submitList(scores)
    }

    override fun setSettingsValue(value: String) {
        binding?.tvSettingsValue?.text = value
    }
}