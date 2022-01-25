package ru.ircover.schultetables.domain

import java.util.*

data class SchulteTableScore(val duration: Long,
                             val settings: SchulteTableSettings,
                             val dateTime: Calendar)