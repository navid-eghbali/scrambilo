package me.navid.scrambilo

import me.navid.scrambilo.model.Alphabet
import me.navid.scrambilo.model.Graph
import me.navid.scrambilo.model.Trie
import org.junit.Before
import org.junit.Test
import java.io.InputStreamReader
import kotlin.random.Random

class DFSUnitTest {
    companion object {
        const val SIZE = 25
        val ENGLISH_ALPHABET = arrayOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        )
        val PERSIAN_ALPHABET = arrayOf(
            'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ', 'ح', 'خ', 'د',
            'ذ', 'ر', 'ز', 'ژ', 'س', 'ش', 'ص', 'ض', 'ط', 'ظ', 'ع',
            'غ', 'ف', 'ق', 'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ی'
        )
        val PERSIAN_WEIGHTS = arrayOf(
            1316, 59, 379, 128, 563, 33, 162, 62, 130, 166, 464,
            37, 805, 189, 31, 371, 240, 81, 49, 82, 16, 170,
            98, 235, 188, 295, 154, 426, 593, 688, 563, 459, 752
        )
        val PERSIAN_SCORES = arrayOf(
            1, 10, 4, 8, 3, 10, 8, 10, 8, 8, 4,
            10, 2, 8, 10, 4, 5, 10, 10, 10, 10, 8,
            10, 5, 8, 5, 8, 4, 3, 3, 3, 4, 2
        )
    }

    private var totalScore = 0
    private var numberOfWords = 0
    private lateinit var map: Graph
    private lateinit var trie: Trie
    private lateinit var alphabets: Array<Alphabet>

    @Before
    fun createGraph_LoadDictionary() {
        map = createGraph()
        trie = loadDictionary(Language.PERSIAN)
        alphabets = createTable(Language.PERSIAN)

        alphabets.forEachIndexed { index, c ->
            System.out.print("${c.character} ")
            if (index % 5 == 4) System.out.println()
        }
    }

    @Test
    fun givenAGraph_PrintAllPermutations() {
        for (i in 0 until SIZE)
            IDDFS(SIZE, i)
        System.out.println("$numberOfWords -> $totalScore")
    }

    private fun IDDFS(n: Int, root: Int) {
        for (depth in 3..n)
            DLS(n, root, depth, BooleanArray(n) { false }, "", 0)
    }

    private fun DLS(n: Int, node: Int, depth: Int, visited: BooleanArray, path: String, score: Int) {
        visited[node] = true
        val pathSoFar = path + alphabets[node].character
        val scoreSoFar = score + alphabets[node].score
        if (depth == 1 && trie.contains(pathSoFar)) {
            numberOfWords++
            totalScore += scoreSoFar
            System.out.println("$pathSoFar -> $scoreSoFar")
        } else if (depth > 1) {
            val i = map.adj[node].listIterator()
            while (i.hasNext()) {
                val nextNode = i.next()
                if (!visited[nextNode] && trie.mayContains(pathSoFar + alphabets[nextNode].character)) {
                    DLS(n, nextNode, depth - 1, visited, pathSoFar, scoreSoFar)
                    visited[nextNode] = false
                }
            }
        }
    }

    private fun createGraph(): Graph {
        val g = Graph(SIZE, SIZE)
        g.addEdge(0, 1)
        g.addEdge(0, 5)
        g.addEdge(1, 0)
        g.addEdge(1, 2)
        g.addEdge(1, 6)
        g.addEdge(2, 1)
        g.addEdge(2, 3)
        g.addEdge(2, 7)
        g.addEdge(3, 2)
        g.addEdge(3, 4)
        g.addEdge(3, 8)
        g.addEdge(4, 3)
        g.addEdge(4, 9)
        g.addEdge(5, 0)
        g.addEdge(5, 6)
        g.addEdge(5, 10)
        g.addEdge(6, 1)
        g.addEdge(6, 5)
        g.addEdge(6, 7)
        g.addEdge(6, 11)
        g.addEdge(7, 2)
        g.addEdge(7, 6)
        g.addEdge(7, 8)
        g.addEdge(7, 12)
        g.addEdge(8, 3)
        g.addEdge(8, 7)
        g.addEdge(8, 9)
        g.addEdge(8, 13)
        g.addEdge(9, 4)
        g.addEdge(9, 8)
        g.addEdge(9, 14)
        g.addEdge(10, 5)
        g.addEdge(10, 11)
        g.addEdge(10, 15)
        g.addEdge(11, 6)
        g.addEdge(11, 10)
        g.addEdge(11, 12)
        g.addEdge(11, 16)
        g.addEdge(12, 7)
        g.addEdge(12, 11)
        g.addEdge(12, 13)
        g.addEdge(12, 17)
        g.addEdge(13, 8)
        g.addEdge(13, 12)
        g.addEdge(13, 14)
        g.addEdge(13, 18)
        g.addEdge(14, 9)
        g.addEdge(14, 13)
        g.addEdge(14, 19)
        g.addEdge(15, 10)
        g.addEdge(15, 16)
        g.addEdge(15, 20)
        g.addEdge(16, 11)
        g.addEdge(16, 15)
        g.addEdge(16, 17)
        g.addEdge(16, 21)
        g.addEdge(17, 12)
        g.addEdge(17, 16)
        g.addEdge(17, 18)
        g.addEdge(17, 22)
        g.addEdge(18, 13)
        g.addEdge(18, 17)
        g.addEdge(18, 19)
        g.addEdge(18, 23)
        g.addEdge(19, 14)
        g.addEdge(19, 18)
        g.addEdge(19, 24)
        g.addEdge(20, 15)
        g.addEdge(20, 21)
        g.addEdge(21, 16)
        g.addEdge(21, 20)
        g.addEdge(21, 22)
        g.addEdge(22, 17)
        g.addEdge(22, 21)
        g.addEdge(22, 23)
        g.addEdge(23, 18)
        g.addEdge(23, 22)
        g.addEdge(23, 24)
        g.addEdge(24, 19)
        g.addEdge(24, 23)

        return g
    }

    private fun loadDictionary(lang: Language): Trie {
        val trie = Trie()
        val stream = javaClass.classLoader?.getResourceAsStream(lang.file)
        stream?.let {
            val reader = InputStreamReader(it)
            reader.forEachLine { w -> trie.insert(w) }
        }

        return trie
    }

    private fun createTable(lang: Language): Array<Alphabet> {
        return when (lang) {
            Language.ENGLISH -> Array(SIZE) { lang.alphabet[Random.nextInt(lang.alphabet.size)] }
            Language.PERSIAN -> {
                val arr = ArrayList<Alphabet>()
                lang.alphabet.forEach {
                    for (i in 1..it.weight)
                        arr.add(it)
                }
                Array(SIZE) { arr[Random.nextInt(arr.size)] }
            }
        }
    }

    enum class Language(val alphabet: Array<Alphabet>, val file: String) {
        ENGLISH(ENGLISH_ALPHABET.map { Alphabet(it, 1, 1) }.toTypedArray(), "corncob_lowercase.txt"),
        PERSIAN(PERSIAN_ALPHABET.mapIndexed { i, c ->
            Alphabet(
                c,
                PERSIAN_WEIGHTS[i],
                PERSIAN_SCORES[i]
            )
        }.toTypedArray(), "moin.txt")
    }
}