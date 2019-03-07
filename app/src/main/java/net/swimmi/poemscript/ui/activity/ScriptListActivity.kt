package net.swimmi.poemscript.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yanzhenjie.recyclerview.*

import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_script_list.*
import net.swimmi.poemscript.R
import net.swimmi.poemscript.db.model.Script
import org.litepal.LitePal
import org.litepal.extension.delete
import org.litepal.extension.findAll

class ScriptListActivity : AppCompatActivity() {

    private lateinit var dataList: List<Script>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        fab.setOnClickListener {
            startActivityForResult(Intent(this, ScriptAddActivity::class.java), 1)
        }

        setupRecyclerView(item_list as SwipeRecyclerView)
    }

    private fun setupRecyclerView(srv: SwipeRecyclerView) {
        val mSwipeMenuCreator = SwipeMenuCreator { _, rightMenu: SwipeMenu, _: Int ->
            rightMenu.orientation = SwipeMenu.VERTICAL
            val width = 200

            val editItem = SwipeMenuItem(this)
            editItem.text = "修改"
            editItem.width = width
            editItem.weight = 1
            editItem.setTextColorResource(R.color.colorPrimary)
            editItem.setBackground(R.drawable.btn_bg)
            rightMenu.addMenuItem(editItem)

            val deleteItem = SwipeMenuItem(this)
            deleteItem.text = "删除"
            deleteItem.width = width
            deleteItem.weight = 1
            deleteItem.setTextColorResource(R.color.colorRed)
            deleteItem.setBackground(R.drawable.btn_bg)
            rightMenu.addMenuItem(deleteItem)
        }
        srv.setSwipeMenuCreator(mSwipeMenuCreator)

        val mOnItemMenuClickListener = OnItemMenuClickListener { swipeMenuBridge: SwipeMenuBridge, i: Int ->
            swipeMenuBridge.closeMenu()
            val menuPosition = swipeMenuBridge.position
            val item = dataList[i]
            when(menuPosition) {
                0 -> {
                    val intent = Intent(this, ScriptAddActivity::class.java).apply {
                        putExtra("script_id", item.id)
                    }
                    startActivityForResult(intent, 2)
                }
                1 -> {
                    LitePal.delete<Script>(item.id)
                    loadListData()
                    Snackbar.make(item_list, "删除成功", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        srv.setOnItemMenuClickListener(mOnItemMenuClickListener)
        loadListData()
    }

    private fun loadListData() {
        dataList = LitePal.findAll<Script>()
        (item_list as SwipeRecyclerView).adapter = SimpleItemRecyclerViewAdapter(
            this,
            dataList
        )
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ScriptListActivity,
        private val values: List<Script>
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Script
                val intent = Intent(v.context, ScriptDetailActivity::class.java).apply {
                    putExtra("script_id", item.id)
                }
                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.titleTv.text = item.title
            holder.authorTv.text = item.author
            holder.periodTv.text = item.period
            holder.descTv.text = item.desc
            holder.textTv.text = item.text
            holder.wordTv.text = item.word

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTv: TextView = view.tv_title
            val authorTv: TextView = view.tv_author
            val periodTv: TextView = view.tv_period
            val descTv: TextView = view.tv_desc
            val textTv: TextView = view.tv_text
            val wordTv: TextView = view.tv_word
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                loadListData()
                Snackbar.make(item_list, "添加成功", Snackbar.LENGTH_SHORT).show()
            }
            if (requestCode == 2) {
                loadListData()
                Snackbar.make(item_list, "修改成功", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
