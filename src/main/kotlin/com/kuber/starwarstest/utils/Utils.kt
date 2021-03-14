package com.kuber.starwarstest.utils

fun getId(url: String?): Int? {
    return if(url.isNullOrEmpty())
        null
    else
        "[\\d]{1,}".toRegex().find(url)!!.value.toInt()
}
