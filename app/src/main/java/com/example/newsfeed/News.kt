package com.example.newsfeed

import org.json.JSONObject
import java.security.CodeSource

data class News(
    val title:String,
    val author:String,
    val url:String,
    val imageUrl:String,
    val publishedAt:String,
    val source: JSONObject

)