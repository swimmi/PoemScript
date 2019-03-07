package net.swimmi.poemscript.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_script_add.*
import kotlinx.android.synthetic.main.content_script_add.*
import net.swimmi.poemscript.R
import net.swimmi.poemscript.db.model.Script
import org.litepal.LitePal
import org.litepal.extension.find

class ScriptAddActivity : AppCompatActivity() {

    private var oldScript: Script? = null

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
            oldScript = LitePal.find<Script>(id)
            title = getString(R.string.title_edit_script)
            fab.setImageDrawable(getDrawable(R.drawable.ic_done_light))
            script_title.setText(oldScript!!.title)
            script_author.setText(oldScript!!.author)
            script_period.setText(oldScript!!.period)
            script_desc.setText(oldScript!!.desc)
            script_text.setText(oldScript!!.text)
            script_word.setText(oldScript!!.word)
        }
    }

    private fun postScript() {
        val title = script_title.text.toString()
        val author = script_author.text.toString()
        val period = script_period.text.toString()
        val desc = script_desc.text.toString()
        val text = script_text.text.toString()
        val word = script_word.text.toString()
        if (oldScript == null) {
            Script(1, title, author, period, desc, text, word).save()
        } else {
            oldScript!!.title = title
            oldScript!!.author = author
            oldScript!!.period = period
            oldScript!!.desc = desc
            oldScript!!.text = text
            oldScript!!.word = word
            oldScript!!.save()
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
