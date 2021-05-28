package info.introvertische.calculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import info.introvertische.calculator.R
import info.introvertische.calculator.interfaces.ClickDeleteHistory
import info.introvertische.calculator.interfaces.ClickListItem
import java.lang.Exception


class HistoryFragment : Fragment(), View.OnClickListener {

    private lateinit var clickDeleteHistory: ClickDeleteHistory
    private lateinit var clickListItem: ClickListItem

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

        val buttonDelete: ImageView = rootView.findViewById(R.id.buttonDeleteHistory)

        buttonDelete.setOnClickListener(this)
        listHistory.onItemClickListener =
            OnItemClickListener { parent, itemClicked, position, id ->
                try {
                    clickListItem.listItemPosition((listContext?.size ?: 0) - position - 1)
                } catch (exp: Exception) {

                }
            }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickDeleteHistory = context as ClickDeleteHistory
        clickListItem = context as ClickListItem
    }

    override fun onClick(v: View?) {
        clickDeleteHistory.deleteHistory(true)
    }

}