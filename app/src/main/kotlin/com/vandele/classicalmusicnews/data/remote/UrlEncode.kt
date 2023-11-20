package com.vandele.classicalmusicnews.data.remote

import java.net.URLEncoder

fun urlEncode(value: String): String = URLEncoder.encode(value, Charsets.UTF_8.name()) ?: ""
