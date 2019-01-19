package me.navid.scrambilo

import org.junit.Test
import java.io.InputStreamReader

class DictionaryUnitTest {
    companion object {
        val PERSIAN_ALPHABET = arrayOf(
            'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ', 'ح', 'خ', 'د',
            'ذ', 'ر', 'ز', 'ژ', 'س', 'ش', 'ص', 'ض', 'ط', 'ظ', 'ع',
            'غ', 'ف', 'ق', 'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ی'
        )
    }

    @Test
    fun loadDictionary() {
        var sum = 0
        val letters = HashMap<Char, Int>()
        val stream = javaClass.classLoader?.getResourceAsStream("moin.txt")
        stream?.let {
            val reader = InputStreamReader(it)
            reader.forEachLine { word ->
                word.forEach { c ->
                    if (PERSIAN_ALPHABET.contains(c)) {
                        sum++
                        if (letters[c] == null)
                            letters[c] = 1
                        else
                            letters[c] = letters.getValue(c) + 1
                    }
                }
            }
        }

        val ratios = HashMap<Char, Double>()
        letters.forEach { t, u -> ratios[t] = u / sum.toDouble() }

        val result = ratios.toList().sortedBy { (t, _) -> t }.toMap()
        result.forEach { t, u -> System.out.println("$t: $u") }
    }
}