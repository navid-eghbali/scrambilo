package me.navid.scrambilo.ui.game

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_game.*
import me.navid.scrambilo.R
import me.navid.scrambilo.extension.toast
import me.navid.scrambilo.extension.viewModelProvider
import me.navid.scrambilo.model.Term
import me.navid.scrambilo.ui.ToolbarManager
import me.navid.scrambilo.ui.game.board.BoardAdapter
import me.navid.scrambilo.ui.game.board.BoardView
import me.navid.scrambilo.ui.hints.HintsDialogFragment
import me.navid.scrambilo.util.TextUtils
import javax.inject.Inject

class GameFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var boardView: BoardView
    private lateinit var viewModel: GameViewModel
    private lateinit var wordsAdapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)

        initUI()

        with(viewModel) {
            if (!userWords.isNullOrEmpty()) wordsAdapter.addAll(userWords.toList())
            score.observe(viewLifecycleOwner, Observer { scoreText.text = "$it" })
            time.observe(viewLifecycleOwner, Observer { timeText.text = TextUtils.getTimeFormatted(it) })
            allWords.observe(viewLifecycleOwner, Observer { if (!it.isEmpty()) activity?.invalidateOptionsMenu() })
            isLoading.observe(viewLifecycleOwner, Observer {
                progress.visibility = if (it) View.VISIBLE else View.GONE
                contentLayout.visibility = if (it) View.GONE else View.VISIBLE
            })
            answerResult.observe(viewLifecycleOwner, Observer { onAnswer(it) })
        }
    }

    private fun initUI() {
        scoreText.setCompoundDrawablesRelativeWithIntrinsicBounds(
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_score_black_24dp),
            null,
            null,
            null
        )
        timeText.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_timer_black_24dp),
            null
        )

        boardView = BoardView(letterBoard, BoardLetterSelection()).apply {
            setAdapter(viewModel.board.map { BoardAdapter.AdapterItem(it) }.toTypedArray())
        }

        wordsList.apply {
            wordsAdapter = WordsAdapter()
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = wordsAdapter
        }
    }

    private fun onAnswer(result: GameViewModel.ResultType) {
        boardView.popWord()
        when (result) {
            GameViewModel.ResultType.Correct -> {
                wordsAdapter.add(wordsAdapter.itemCount, result.data!!)

                val anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.zoom_in_out)
                anim.setTarget(scoreText)
                anim.start()
            }
            GameViewModel.ResultType.Incorrect -> requireContext().toast(getString(R.string.invalid_word))
            GameViewModel.ResultType.Duplicate -> requireContext().toast(getString(R.string.duplicate_word))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_game, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_menu_hint).isEnabled = viewModel.boardHasTerm()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_menu_hint -> {
                HintsDialogFragment.newInstance(viewModel.getAllWords()).show(childFragmentManager, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class BoardLetterSelection : BoardView.OnLetterSelectionListener {
        override fun onSelectionBegin(str: String) {
            (requireActivity() as? ToolbarManager)?.setToolbarTitle(str)
        }

        override fun onSelectionMove(str: String) {
            (requireActivity() as? ToolbarManager)?.setToolbarTitle(str)
        }

        override fun onSelectionEnd(str: String, score: Int) {
            (requireActivity() as? ToolbarManager)?.setToolbarTitle(str)
            viewModel.answer(Term(str, score))
        }
    }
}
