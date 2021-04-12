package com.base.core.helpers

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.jvm.internal.ClassReference

class LocalPrefManager(var sharedPreferences: SharedPreferences) {

    fun push(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun push(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun push(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun push(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun push(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun push(key: String, value: Any?) {
        val json = toJson(value!!)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun pull(key: String, defaultValue: String): String =
        sharedPreferences.getString(key, defaultValue)!!

    fun pull(key: String, defaultValue: Int): Int = sharedPreferences.getInt(key, defaultValue)

    fun pull(key: String, defaultValue: Long): Long = sharedPreferences.getLong(key, defaultValue)

    fun pull(key: String, defaultValue: Float): Float =
        sharedPreferences.getFloat(key, defaultValue)

    fun pull(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    fun pull(key: String, type: Any): Any? {
        var json = sharedPreferences.getString(key, "")
        return if (json == "") {
            null
        } else {
            fromJson(json!!, (type as ClassReference).jClass)
        }
    }

    fun toJson(jsonObject: Any): String = Gson().toJson(jsonObject)

    fun <T> fromJson(jsonString: String, classType: Class<T>): T =
        Gson().fromJson(jsonString, classType)

    fun getAllKeys(): List<String> {
        val keyList = arrayListOf<String>()
        val allEntries = sharedPreferences.all
        allEntries.forEach {
            keyList.add(it.key)
        }
        return keyList
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}