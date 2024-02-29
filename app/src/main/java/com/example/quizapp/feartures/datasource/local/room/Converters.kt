package com.example.quizapp.feartures.datasource.local.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.quizapp.feartures.domain.model.Meaning
import com.example.dictionaryapp.core_utils.parse.JsonParser
import com.example.quizapp.feartures.domain.model.Phonetic
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    // Convert từ List<Meaning> sang String
    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }

    // Convert từ List<Meaning> sang String
    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }

    // Convert từ List<Phonetic> sang String
    @TypeConverter
    fun fromPhoneticsJson(json: String): List<Phonetic> {
        return jsonParser.fromJson<ArrayList<Phonetic>>(
            json,
            object : TypeToken<ArrayList<Phonetic>>(){}.type
        ) ?: emptyList()
    }

    // Convert từ List<Phonetic> sang String
    @TypeConverter
    fun toPhoneticsJson(phonetics: List<Phonetic>): String {
        return jsonParser.toJson(
            phonetics,
            object : TypeToken<ArrayList<Phonetic>>(){}.type
        ) ?: "[]"
    }
}
