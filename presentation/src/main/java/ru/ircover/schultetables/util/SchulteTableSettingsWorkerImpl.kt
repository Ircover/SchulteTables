package ru.ircover.schultetables.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.ircover.schultetables.domain.SchulteTableSettings
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.domain.SettingType

private const val KEY_SETTINGS_VALUE = "key_settings_value"

class SchulteTableSettingsWorkerImpl(private val context: Context,
                                     private val gson: Gson) : SchulteTableSettingsWorker {
    private val changes: MutableSharedFlow<SettingType> = MutableSharedFlow()

    override fun get(): SchulteTableSettings {
        return getSharedPreferences().run {
            if(contains(KEY_SETTINGS_VALUE)) {
                gson.fromJson(getString(KEY_SETTINGS_VALUE, null), SchulteTableSettings::class.java)
            } else {
                SchulteTableSettings(5, 5)
            }
        }
    }

    override suspend fun save(settings: SchulteTableSettings, vararg changedSettings: SettingType) {
        getSharedPreferences().edit().apply {
            putString(KEY_SETTINGS_VALUE, gson.toJson(settings))
            apply()
        }
        changedSettings.forEach { changes.emit(it) }
    }

    override fun getChanges(): Flow<SettingType> = changes

    private fun getSharedPreferences(): SharedPreferences =
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
}