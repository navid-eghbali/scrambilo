package me.navid.scrambilo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import me.navid.scrambilo.R
import me.navid.scrambilo.extension.toast

class HomeFragment : DaggerFragment() {
    private var adapter: HomeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = HomeAdapter { resId ->
            if (resId == null)
                requireContext().toast(getString(R.string.coming_soon))
            else
                findNavController().navigate(resId)
        }
        adapter?.addAll(
            arrayOf(
                HomeAdapter.AdapterItem(getString(R.string.new_game), R.id.action_mainFragment_to_gameFragment),
                HomeAdapter.AdapterItem(getString(R.string.time_trial), null),
                HomeAdapter.AdapterItem(getString(R.string.scoreboard), null),
                HomeAdapter.AdapterItem(getString(R.string.settings), null),
                HomeAdapter.AdapterItem(getString(R.string.about), null)
            )
        )
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter
    }
}