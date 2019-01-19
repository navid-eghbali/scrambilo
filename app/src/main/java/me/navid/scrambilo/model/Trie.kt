package me.navid.scrambilo.model

import android.os.Build

class Trie {
    private var root: Node? = null

    init {
        root = Node()
    }

    fun insert(word: String) {
        var current = root

        word.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                current = current?.children?.computeIfAbsent(it) { Node() }
            } else {
                var node = current?.children?.get(it)
                if (node == null) {
                    node = Node()
                    current?.children?.put(it, node)
                }
                current = node
            }
        }
        current?.content = word
        current?.isWord = true
    }

    fun contains(word: String): Boolean {
        var current = root

        word.forEach {
            val node = current?.children?.get(it) ?: return false
            current = node
        }
        return current?.isWord ?: false
    }

    //If we have this branch it may leads us to a word
    fun mayContains(word: String): Boolean {
        var current = root

        word.forEach {
            val node = current?.children?.get(it) ?: return false
            current = node
        }
        return true
    }

    fun isEmpty(): Boolean = (root == null)
}