package ht.ferit.fjjukic.roomapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ht.ferit.fjjukic.roomapplication.R
import ht.ferit.fjjukic.roomapplication.adapter.InspiringPeopleAdapter
import ht.ferit.fjjukic.roomapplication.interfaces.FragmentListener

class InspiringPeopleFragment(
    private var inspiringPersonAdapter: InspiringPeopleAdapter,
    private var fragmentListener: FragmentListener
) : Fragment() {

    private lateinit var fabAdd: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_first, container, false)
        this.fabAdd = view.findViewById(R.id.fabAdd)
        val listView: ListView = view.findViewById(R.id.listView)
        listView.adapter = inspiringPersonAdapter

        this.fabAdd.setOnClickListener {
            fragmentListener.addNewItem()
        }

        return view;
    }
}