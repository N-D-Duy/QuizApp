package com.example.quizapp.feartures.domain.model

import com.example.quizapp.feartures.datasource.local.room.entity.WordInfoEntity
import com.example.quizapp.core_utils.enums.DismissDuration
import java.io.Serializable

data class WordInfo(
    var word: String,
    var meanings: List<Meaning>,
    var phonetic: String? = "",
    var origin: String? = "",
    var isFavorite: Boolean = false, //check xem đã là từ yêu thích hay chưa.
    var isUsed: Boolean = false, //check xem đã là từ lịch sử search hay chưa.
    var dismissDuration: DismissDuration? = null,
    var expiredTime: Long? = null, //thời gian hết hạn
    var isSkipped: Byte = 0, //biến kiểm tra số lần người dùng skip theo đó sẽ điều chỉnh opts chọn time skip
): Serializable {

    fun toWordEntity(): WordInfoEntity? {
        return WordInfoEntity(
            id = 0,
            word = this.word,
            meanings = this.meanings,
            phonetic = this.phonetic,
            isFavorite = this.isFavorite,
            origin = this.origin,
            isUsed = this.isUsed,
            dismissDuration = this.dismissDuration,
            expiredTime = this.expiredTime,
            isSkipped = this.isSkipped,
        )
    }
}

class InvalidWordException(message: String) : Exception(message)