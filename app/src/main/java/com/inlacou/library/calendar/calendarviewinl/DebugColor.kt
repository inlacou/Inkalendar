package com.inlacou.library.calendar.calendarviewinl
import android.util.Log
import com.inlacou.inker.Inker

open class DebugColor: Inker.Color() {
	override val v: ((s: String?) -> Unit)? =       { Log.v(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it ?: "null") }
	override val d: ((s: String?) -> Unit)? =       { Log.d(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it ?: "null") }
	override val i: ((s: String?) -> Unit)? =       { Log.i(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it ?: "null") }
	override val w: ((s: String?) -> Unit)? =       { Log.w(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it ?: "null") }
	override val w2: ((t: Throwable?) -> Unit)? =   { Log.w(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it) }
	override val e: ((s: String?) -> Unit)? =       { Log.e(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it ?: "null") }
	override val e2: ((t: Throwable?) -> Unit)? =   { Log.e(  try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, null, it) }
	override val wtf: ((s: String?) -> Unit)? =     { Log.wtf(try{ createTag() }catch (e: Exception) { "INKER_ERROR" }, it) }
}