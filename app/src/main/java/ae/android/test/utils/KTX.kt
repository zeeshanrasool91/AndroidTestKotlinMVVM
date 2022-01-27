package ae.android.test.utils

import ae.android.test.networking.NetController
import android.view.View
import com.google.gson.Gson


fun <T> String.getObject(classType: Class<T>): T {
    return NetController.gson.fromJson(this, classType)
}

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}


fun Gson.getString(t: Any): String {
    return toJson(t)
}

fun Any.getString(): String {
    return NetController.gson.toJson(this)
}


fun View.gone() {
    this.visibility=View.GONE
}

fun View.hide() {
    this.visibility=View.INVISIBLE
}

fun View.show() {
    this.visibility=View.VISIBLE
}