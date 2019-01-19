package me.navid.scrambilo.model

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * The class for solving our board. It calculates all the paths started from different roots and add the terms to 'words'
 */
class Brain(
    private val board: Array<Alphabet>,
    private val map: Graph,
    private val trie: Trie
) {
    val words = ArrayList<Term>()

    @SuppressLint("CheckResult")
    fun solve(): LiveData<Result<List<Term>>> {
        val result = MutableLiveData<Result<List<Term>>>()
        result.value = Result.Loading

        Single.fromCallable {
            for (root in 0 until board.size)
                for (depth in 3..15)
                    DFS(root, depth, BooleanArray(board.size) { false }, "", 0)
            words
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result.value = Result.Success(it) }, {
                it.printStackTrace()
                result.value = Result.Error(Exception(it))
            })

        return result
    }

    private fun DFS(node: Int, depth: Int, visited: BooleanArray, path: String, score: Int) {
        visited[node] = true
        val pathSoFar = path + board[node].character
        val scoreSoFar = score + board[node].score
        if (depth == 1 && trie.contains(pathSoFar)) {
            words.add(Term(pathSoFar, scoreSoFar))
        } else if (depth > 1) {
            val i = map.adj[node].listIterator()
            while (i.hasNext()) {
                val nextNode = i.next()
                if (!visited[nextNode] && trie.mayContains(pathSoFar + board[nextNode].character)) {
                    DFS(nextNode, depth - 1, visited, pathSoFar, scoreSoFar)
                    visited[nextNode] = false
                }
            }
        }
    }

}

data class Term(val word: String, val score: Int) {
    override fun equals(other: Any?): Boolean {
        return word == (other as? Term)?.word
    }
}
