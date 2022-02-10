package ru.ircover.schultetables.util

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.ircover.schultetables.Serializer
import ru.ircover.schultetables.deserialize
import ru.ircover.schultetables.domain.SchulteTableSettings
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.domain.SettingType

private const val KEY_SETTINGS_VALUE = "key_settings_value"

class SchulteTableSettingsWorkerImpl(private val context: Context,
                                     private val serializer: Serializer) : SchulteTableSettingsWorker {
    private val changes: MutableSharedFlow<SettingType> = MutableSharedFlow()

    override fun get(): SchulteTableSettings {
        return getSharedPreferences().run {
            if(contains(KEY_SETTINGS_VALUE)) {
                serializer.deserialize(getString(KEY_SETTINGS_VALUE, null) ?: "{}")
            } else {
                SchulteTableSettings(5, 5)
            }
        }
    }

    override suspend fun save(settings: SchulteTableSettings, vararg changedSettings: SettingType) {
        getSharedPreferences().edit().apply {
            putString(KEY_SETTINGS_VALUE, serializer.serialize(settings))
            apply()
        }
        changedSettings.forEach { changes.emit(it) }
    }

    override fun getChanges(): Flow<SettingType> = changes

    private fun getSharedPreferences(): SharedPreferences =
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
}