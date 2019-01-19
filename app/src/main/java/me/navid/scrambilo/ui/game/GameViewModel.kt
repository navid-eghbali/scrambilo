package me.navid.scrambilo.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.navid.scrambilo.extension.map
import me.navid.scrambilo.extension.switchMap
import me.navid.scrambilo.model.*
import me.navid.scrambilo.repository.BoardRepository
import me.navid.scrambilo.repository.DictionaryRepository
import java.util.*
import javax.inject.Inject

class GameViewModel @Inject constructor(
    boardRepository: BoardRepository,
    dictionaryRepository: DictionaryRepository
) : ViewModel() {
    companion object {
        private const val SIZE = 5
    }

    private val map: Graph
    private val language = Language.PERSIAN
    private val trie: LiveData<Result<Trie>>
    private val difficulty = Difficulty.HARD
    private val solveBoardResult: LiveData<Result<List<Term>>>

    val board: Array<Alphabet>
    val isLoading: LiveData<Boolean>
    val time = MutableLiveData<Int>()
    val score = MutableLiveData<Int>()
    val allWords: LiveData<List<Term>>
    val answerResult = MutableLiveData<ResultType>()
    val userWords: MutableSet<Term> = HashSet()

    init {
        this.map = Graph(SIZE, SIZE)
        this.board = boardRepository.createBoard(language, SIZE, difficulty.range)
        this.trie = dictionaryRepository.createDictionary(language)

        //Using map is like observing that LiveData
        this.isLoading = trie.map { it == Result.Loading }

        //For solving board first we have to wait for the dictionary to be loaded
        //so we put a switchMap on trie to get notified when it has finished loading
        this.solveBoardResult = trie.switchMap {
            if (it is Result.Success)
                Brain(board, map, it.data).solve()
            else
                MutableLiveData<Result<List<Term>>>().apply { value = Result.Loading }
        }
        //We observe solveBoardResult and when it got a valid data we pass it to upper layer
        this.allWords = solveBoardResult.map { (it as? Result.Success)?.data ?: emptyList() }

        time.value = 0
        score.value = 0
    }

    fun answer(term: Term) {
        if (userWords.contains(term)) {
            answerResult.value = ResultType.Duplicate
        } else {
            if (hasWord(term.word)) {
                userWords.add(term)
                score.value = score.value?.plus(term.score)
                answerResult.value = ResultType.Correct.apply { data = term }
            } else
                answerResult.value = ResultType.Incorrect
        }
    }

    fun boardHasTerm() = !allWords.value.isNullOrEmpty()

    fun getAllWords() = allWords.value?.toTypedArray() ?: emptyArray()

    private fun hasWord(text: String) = (trie.value as? Result.Success)?.data?.contains(text) ?: false

    enum class ResultType {
        Correct,
        Incorrect,
        Duplicate;

        var data: Term? = null
    }
}
