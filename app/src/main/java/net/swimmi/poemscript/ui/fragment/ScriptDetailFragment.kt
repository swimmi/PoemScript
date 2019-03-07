package net.swimmi.poemscript.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_script_detail.view.*
import net.swimmi.poemscript.R
import net.swimmi.poemscript.db.model.Script
import org.litepal.LitePal
import org.litepal.extension.find

class ScriptDetailFragment : Fragment() {

    private var item: Script? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey("script_id")) {
                item = LitePal.find<Script>(it.getLong("script_id"))
                activity?.toolbar_layout?.title = item?.title
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_script_detail, container, false)

        item?.let {
            rootView.item_detail.text = it.text
        }

        return rootView
    }
}
