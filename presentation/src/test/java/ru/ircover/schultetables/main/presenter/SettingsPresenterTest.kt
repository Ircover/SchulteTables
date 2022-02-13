package ru.ircover.schultetables.main.presenter

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import ru.ircover.schultetables.domain.SchulteTableSettings
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.domain.SettingType
import ru.ircover.schultetables.utils.TestDispatchersProvider

@ExperimentalCoroutinesApi
class SettingsPresenterTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val settings = SchulteTableSettings(4, 5)

    private lateinit var sut: SettingsPresenter
    private lateinit var view: SettingsView
    private lateinit var settingsWorker: SchulteTableSettingsWorker

    @Before
    fun setup() {
        view = mock()
        settingsWorker = mock()
        `when`(settingsWorker.get()).thenReturn(settings)
        sut = SettingsPresenter(settingsWorker, TestDispatchersProvider(testCoroutineDispatcher)).apply {
            attachView(view)
        }
    }

    @Test
    fun initView() {
        sut.initView()

        verify(view).setMaxSizePositions(5, 5)
        verify(view).setColumnsCount(0, 4)
        verify(view).setRowsCount(1, 5)
    }

    @Test
    fun setColumnsCountProgress_oldValue() = runBlockingTest {
        sut.setColumnsCountProgress(0)

        verify(view, never()).setColumnsCount(any(), any())
        verify(settingsWorker, never()).save(any(), any())
    }

    @Test
    fun setColumnsCountProgress_newValue() = runBlockingTest {
        sut.setColumnsCountProgress(2)

        verify(view).setColumnsCount(2, 6)
        verify(settingsWorker).save(SchulteTableSettings(6, 5), SettingType.ColumnsCount)
    }

    @Test
    fun setRowsCountProgress_oldValue() = runBlockingTest {
        sut.setRowsCountProgress(1)

        verify(view, never()).setRowsCount(any(), any())
        verify(settingsWorker, never()).save(any(), any())
    }

    @Test
    fun setRowsCountProgress_newValue() = runBlockingTest {
        sut.setRowsCountProgress(2)

        verify(view).setRowsCount(2, 6)
        verify(settingsWorker).save(SchulteTableSettings(4, 6), SettingType.RowsCount)
    }
}