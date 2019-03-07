package net.swimmi.poemscript.db.model

import org.litepal.crud.LitePalSupport

data class Poem(var id: Long, var title: String, var author: String, var period: String, var desc: String, var text: String, var word: String) : LitePalSupport() {

}