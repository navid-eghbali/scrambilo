package me.navid.scrambilo.repository

import me.navid.scrambilo.model.Alphabet
import me.navid.scrambilo.model.Language
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class BoardRepository @Inject constructor() {
    //Creates a board randomly based on a letter's weight
    fun createBoard(lang: Language, n: Int, range: IntRange?): Array<Alphabet> {
        return when (lang) {
            Language.English -> Array(n * n) { lang.alphabet[Random.nextInt(lang.alphabet.size)] }
            Language.Persian -> {
                val arr = ArrayList<Alphabet>()
                lang.alphabet.forEach {
                    for (i in 1..it.weight) {
                        arr.add(it)
                        if (range != null && it.score in range) arr.add(it) //Doubling some alphabets to create difficulty
                    }
                }
                Array(n * n) { arr[Random.nextInt(arr.size)] }
            }
        }
    }
}