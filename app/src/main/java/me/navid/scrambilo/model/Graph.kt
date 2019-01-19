package me.navid.scrambilo.model

import java.util.*

/**
 * Creates a row * col graph
 */
class Graph(row: Int, col: Int) {
    val adj = Array<LinkedList<Int>>(row * col) { LinkedList() }

    init {
        for (i in 0 until row)
            for (j in 0 until col) {
                if (i > 0) addEdge(row * i + j, row * i + j - row)
                if (j > 0) addEdge(row * i + j, row * i + j - 1)
                if (j < col - 1) addEdge(row * i + j, row * i + j + 1)
                if (i < row - 1) addEdge(row * i + j, row * i + j + row)
            }
    }

    fun addEdge(u: Int, v: Int) {
        adj[u].add(v)
    }
}