package me.navid.scrambilo

import me.navid.scrambilo.model.Trie
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.InputStreamReader

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TrieUnitTest {
    @Test
    fun givenATrie_TrieNotEmpty() {
        val trie = createTrie()

        assertFalse(trie.isEmpty())
    }

    @Test
    fun givenATrie_TrieContainsThoseElements() {
        val trie = createTrie()

        assertFalse(trie.contains("3"))
        assertFalse(trie.contains("vida"))
        assertTrue(trie.contains("life"))
        assertTrue(trie.contains("absent"))
        assertTrue(trie.contains("absolute"))
        assertTrue(trie.contains("absented"))
    }

    @Test
    fun createTrieFromDictionary_TrieNotEmpty() {
        val trie = loadDictionary()

        assertFalse(trie.isEmpty())
    }

    @Test
    fun createTrieFromDictionary_TrieContainsSomeElements() {
        val trie = loadDictionary()

        assertTrue(trie.contains("سلام"))
        assertTrue(trie.contains("چگونه"))
        assertTrue(trie.contains("میز"))
        assertTrue(trie.contains("در"))
        assertTrue(trie.contains("صندلی"))
        assertTrue(trie.contains("کامپیوتر"))
        assertFalse(trie.contains("خسشهتب"))
    }

    private fun createTrie(): Trie {
        val trie = Trie()
        trie.insert("Programming")
        trie.insert("is")
        trie.insert("a")
        trie.insert("way")
        trie.insert("of")
        trie.insert("life")
        trie.insert("absent")
        trie.insert("absented")
        trie.insert("absend")
        trie.insert("absolute")

        return trie
    }

    private fun loadDictionary(): Trie {
        val trie = Trie()
        val stream = javaClass.classLoader?.getResourceAsStream("moin.txt")
        stream?.let {
            val reader = InputStreamReader(it)
            reader.forEachLine { w -> trie.insert(w) }
        }

        return trie
    }
}
