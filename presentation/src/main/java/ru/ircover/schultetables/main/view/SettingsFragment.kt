package ru.ircover.schultetables.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ircover.schultetables.databinding.FragmentSettingsBinding
import ru.ircover.schultetables.main.presenter.SettingsPresenter
import ru.ircover.schultetables.main.presenter.SettingsView
import ru.ircover.schultetables.util.getPresentationComponent
import ru.ircover.schultetables.util.setOnSeekBarChangeListener
import javax.inject.Inject

class SettingsFragment : MvpAppCompatFragment(), SettingsView {
    private var binding: FragmentSettingsBinding? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter {
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
        return FragmentSettingsBinding.inflate(inflater).apply {
            binding = this
            sbColumnsCount.setOnSeekBarChangeListener {
                presenter.setColumnsCountProgress(it)
            }
            sbRowsCount.setOnSeekBarChangeListener {
                presenter.setRowsCountProgress(it)
            }
            presenter.initView()
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun setMaxSizePositions(maxColumnsPositions: Int, maxRowsPositions: Int) {
        binding?.sbColumnsCount?.max = maxColumnsPositions
        binding?.sbRowsCount?.max = maxRowsPositions
    }

    override fun setColumnsCount(progress: Int, count: Int) {
        binding?.sbColumnsCount?.progress = progress
        binding?.tvColumnsCount?.text = count.toString()
    }

    override fun setRowsCount(progress: Int, count: Int) {
        binding?.sbRowsCount?.progress = progress
        binding?.tvRowsCount?.text = count.toString()
    }
}