package com.example.lolketingcompose.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(msgRes: Int) {
    Toast.makeText(this, getString(msgRes), Toast.LENGTH_SHORT).show()
}

fun <T> SnapshotStateList<T>.clearAndAddAll(items: List<T>) {
    clear()
    addAll(items)
}

fun argumentEncode(item: Any): String =
    Uri.encode(Gson().toJson(item))

inline fun <reified T> String.argumentDecode(): T = Gson().fromJson(this, T::class.java)

inline fun <reified T> SavedStateHandle.getArgumentDecode(key: String): T? {
    return get<String>(key)?.argumentDecode()
}