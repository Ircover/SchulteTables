package ru.ircover.schultetables.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import moxy.MvpAppCompatFragment
import ru.ircover.schultetables.R
import ru.ircover.schultetables.databinding.FragmentGameBinding
import ru.ircover.schultetables.main.presenter.GamePresenter
import ru.ircover.schultetables.main.presenter.GameView
import moxy.presenter.ProvidePresenter
import moxy.presenter.InjectPresenter
import ru.ircover.schultetables.domain.Matrix2D
import ru.ircover.schultetables.domain.SchulteTableCallback
import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.util.getPresentationComponent
import ru.ircover.schultetables.util.vibrateDevice
import javax.inject.Inject

class GameFragment : MvpAppCompatFragment(), GameView {
    private var binding: FragmentGameBinding? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: GamePresenter

    @ProvidePresenter
    fun providePresenter(): GamePresenter {
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
        return FragmentGameBinding.inflate(inflater).apply {
            binding = this
            bSettings.setOnClickListener { presenter.clickSettings() }
            bRefresh.setOnClickListener { presenter.clickRefresh() }
            presenter.initView()
        }.root
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun openSettings() {
        findNavController().navigate(R.id.navSettingsFragment)
    }

    override fun showCells(cells: Matrix2D<SchulteTableCell>) {
        binding?.stMain?.setCells(cells)
    }

    override fun setExpectedCellText(text: String) {
        binding?.tvExpectedCellValue?.text = text
    }

    override fun setCallback(callback: SchulteTableCallback) {
        binding?.stMain?.setCallback(callback)
    }

    override fun notifyWrongCell() {
        context?.vibrateDevice()
    }

    override fun openScoresList() {
        findNavController().navigate(R.id.navScoresFragment)
    }
}