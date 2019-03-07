package net.swimmi.poemscript.db.model

import org.litepal.crud.LitePalSupport

data class Script(val pid: Int, val roles: List<Role>) : LitePalSupport() {
}