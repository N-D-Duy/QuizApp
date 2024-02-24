package com.example.quizapp.core_utils.upcoming_word

import com.example.quizapp.feartures.domain.model.WordInfo

class QueueImpl: PriorityQueue {
    private val priorityQueue = java.util.PriorityQueue<WordInfo> { word1, word2 ->
        // Sắp xếp theo thời gian còn lại tăng dần
        (word1.dismissDuration!!.durationInMinutes - word2.dismissDuration!!.durationInMinutes).toInt()
    }
    override fun enqueue(element: WordInfo) {
        priorityQueue.offer(element)
    }

    override fun dequeue(): WordInfo? {
        return priorityQueue.poll()
    }

    override fun peek(): WordInfo? {
        return priorityQueue.peek()
    }

    override fun isEmpty(): Boolean {
        return priorityQueue.isEmpty()
    }
}