package com.example.quizapp.core_utils.trie

import com.example.quizapp.feartures.domain.model.WordInfo

class TrieNode<Key>(var key: Key?, var parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isValidWord = false
    var wordInfo: WordInfo? = null
}