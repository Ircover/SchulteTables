package ru.ircover.schultetables.domain

import kotlinx.coroutines.flow.Flow

enum class SettingType {
    ColumnsCount,
    RowsCount
}

interface SchulteTableSettingsWorker {
    fun get(): SchulteTableSettings
    suspend fun save(settings: SchulteTableSettings, vararg changedSettings: SettingType)
    fun getChanges(): Flow<SettingType>
}