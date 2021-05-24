package info.introvertische.calculator.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import info.introvertische.calculator.R


class HistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val listTitle = this.arguments?.getStringArrayList("answer")
        val listContext = this.arguments?.getStringArrayList("expression")
        val rootView = inflater.inflate(R.layout.fragment_history, container, false)

        val listHistory: ListView = rootView.findViewById(R.id.listHistory)
        val data: ArrayList<HashMap<String, String>> = arrayListOf()
        var m: HashMap<String, String>

        for (i in listTitle?.lastIndex!! downTo 0) {
            m = hashMapOf()
            m["title"] = listTitle[i]
            m["context"] = listContext!![i]
            data.add(m)
        }

        val from: Array<String> = arrayOf("title", "context")
        val to: IntArray = intArrayOf(R.id.listItemTitle, R.id.listItemContext)

        val simpleAdapter: SimpleAdapter = SimpleAdapter(inflater.context, data, R.layout.list_item, from, to)

        listHistory.adapter = simpleAdapter

        return rootView
    }

}