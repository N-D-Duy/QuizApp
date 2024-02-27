package com.example.quizapp.core_utils.functions

import android.content.Context
import javax.inject.Inject

class ReadWordsFromAsset {
    companion object {
        fun readWordsFromAsset(context: Context): List<String> {
            val words = mutableListOf<String>()
            try {
                // Mở AssetManager
                val assetManager = context.assets

                // Mở tệp tin từ thư mục assets
                val inputStream = assetManager.open("word.txt")

                // Đọc nội dung từ InputStream
                val reader = inputStream.bufferedReader()
                reader.useLines { lines ->
                    words.addAll(lines.map { it.trim() })
                }
                // Đóng InputStream
                inputStream.close()
            } catch (e: Exception) {
                throw Exception("Error reading words from assets: ${e.message}")
            }
            return words.shuffled().subList(0, 20)
        }
    }
}