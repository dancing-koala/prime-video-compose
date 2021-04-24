package com.dancing_koala.primevideo.utils

fun <T> makeListFullOf(uniqueItem: T, size: Int) =
    mutableListOf<T>().apply {
        repeat(size) { add(uniqueItem) }
    }.toList()