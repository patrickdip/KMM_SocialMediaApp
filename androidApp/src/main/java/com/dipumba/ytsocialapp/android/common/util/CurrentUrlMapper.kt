package com.dipumba.ytsocialapp.android.common.util

private const val CURRENT_BASE_URL = "http://192.168.0.103:8081/"

fun String.toCurrentUrl(): String{
    return try {
        "$CURRENT_BASE_URL${this.substring(26)}"
    }catch (error: Throwable){
        this
    }
}