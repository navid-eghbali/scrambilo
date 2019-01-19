package me.navid.scrambilo.ui.hints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hints.*
import me.navid.scrambilo.R
import me.navid.scrambilo.model.Term

class HintsFragment : DaggerFragment() {
    companion object {
        const val TERMS = "Terms"
    }

    private lateinit var hintsAdapter: HintsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hints, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hintsAdapter = HintsAdapter()
        arguments?.getSerializable(TERMS)?.let { hintsAdapter.addAll(it as Array<Term>) }
        list.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = hintsAdapter
        }
    }
}