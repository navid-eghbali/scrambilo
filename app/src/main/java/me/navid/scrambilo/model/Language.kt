package me.navid.scrambilo.model

enum class Language(val alphabet: Array<Alphabet>, val file: String) {
    ENGLISH(Data.ENGLISH_ALPHABET.map { Alphabet(it, 1, 1) }.toTypedArray(), "corncob.txt"),
    PERSIAN(Data.PERSIAN_ALPHABET.mapIndexed { i, c ->
        Alphabet(
            c,
            Data.PERSIAN_WEIGHTS[i],
            Data.PERSIAN_SCORES[i]
        )
    }.toTypedArray(), "moin.txt");

    object Data {
        val ENGLISH_ALPHABET = arrayOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        )
        val PERSIAN_ALPHABET = arrayOf(
            'ا', 'آ', 'ب', 'پ', 'ت', 'ث', 'ج', 'چ', 'ح', 'خ', 'د',
            'ذ', 'ر', 'ز', 'ژ', 'س', 'ش', 'ص', 'ض', 'ط', 'ظ', 'ع',
            'غ', 'ف', 'ق', 'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ی'
        )
        //For calculating the weights I've extract the amplitude of each letter in my dictionary and multiply it by 10000
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
}
