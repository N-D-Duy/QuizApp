package com.example.quizapp.core_utils.upcoming_word

import com.example.quizapp.feartures.domain.model.WordInfo

interface PriorityQueue {
    fun enqueue(element: WordInfo)
    fun dequeue(): WordInfo?
    fun peek(): WordInfo?
    fun isEmpty(): Boolean
}