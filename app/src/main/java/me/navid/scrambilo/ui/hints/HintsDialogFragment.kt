package me.navid.scrambilo.ui.hints

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import me.navid.scrambilo.R
import me.navid.scrambilo.model.Term

class HintsDialogFragment : DialogFragment() {
    companion object {
        private const val TERMS = "Terms"

        fun newInstance(terms: Array<Term>): HintsDialogFragment =
            HintsDialogFragment().apply { arguments = Bundle().apply { putSerializable(TERMS, terms) } }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_hints, null)
        val builder = AlertDialog.Builder(requireContext())
            .setView(view)

        val hintsAdapter = HintsAdapter()
        arguments?.getSerializable(TERMS)?.let { hintsAdapter.addAll(it as Array<Term>) }
        with(view.findViewById<RecyclerView>(android.R.id.list)) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = hintsAdapter
        }

        view.findViewById<MaterialButton>(android.R.id.button1).setOnClickListener { dismiss() }

        return builder.create()
    }
}