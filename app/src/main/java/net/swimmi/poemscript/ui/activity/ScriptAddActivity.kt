package net.swimmi.poemscript.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_script_add.*
import kotlinx.android.synthetic.main.content_script_add.*
import net.swimmi.poemscript.R
import net.swimmi.poemscript.db.model.Poem
import org.litepal.LitePal
import org.litepal.extension.find

class ScriptAddActivity : AppCompatActivity() {

    private var oldPoem: Poem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_script_add)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener {
            postScript()
            setResult(Activity.RESULT_OK)
            finish()
        }
        if (intent.hasExtra("script_id")) {
            val id = intent.getLongExtra("script_id", 1)
            oldPoem = LitePal.find<Poem>(id)
            title = getString(R.string.title_script_edit)
            fab.setImageDrawable(getDrawable(R.drawable.ic_done_light))
            script_title.setText(oldPoem!!.title)
            script_author.setText(oldPoem!!.author)
            script_period.setText(oldPoem!!.period)
            script_desc.setText(oldPoem!!.desc)
            script_text.setText(oldPoem!!.text)
            script_word.setText(oldPoem!!.word)
        }
    }

    private fun postScript() {
        val title = script_title.text.toString()
        val author = script_author.text.toString()
        val period = script_period.text.toString()
        val desc = script_desc.text.toString()
        val text = script_text.text.toString()
        val word = script_word.text.toString()
        if (oldPoem == null) {
            Poem(1, title, author, period, desc, text, word).save()
        } else {
            oldPoem!!.title = title
            oldPoem!!.author = author
            oldPoem!!.period = period
            oldPoem!!.desc = desc
            oldPoem!!.text = text
            oldPoem!!.word = word
            oldPoem!!.save()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, ScriptListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
