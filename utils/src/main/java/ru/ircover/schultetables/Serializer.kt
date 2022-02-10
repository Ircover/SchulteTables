package ru.ircover.schultetables

import com.google.gson.Gson
import javax.inject.Inject

interface Serializer {
    fun <T> serialize(obj: T): String
    fun <T> deserialize(json: String, classOfT: Class<T>): T
}

class SerializerImpl @Inject constructor(private val gson: Gson) : Serializer {
    override fun <T> serialize(obj: T): String =
        gson.toJson(obj)

    override fun <T> deserialize(json: String, classOfT: Class<T>): T =
        gson.fromJson(json, classOfT)
}

inline fun <reified T> Serializer.deserialize(json: String) = deserialize(json, T::class.java)