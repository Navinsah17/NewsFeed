package com.example.newsfeed

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity(){

    //private lateinit var madapter:NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupviewpager()

        /*recyclerview.layoutManager = LinearLayoutManager(this)
        fetch()
        madapter= NewsAdapter(this)
        recyclerview.adapter=madapter*/
    }
    @SuppressLint("CutPasteId")
    private fun setupviewpager(){
        val tabLayout=findViewById<TabLayout>(R.id.tablayouti)
        val viewPage=findViewById<ViewPager>(R.id.fragmentcontainer)
        tabLayout.setupWithViewPager(viewPage)
        val vpAdapter= pagerAdapter(supportFragmentManager)
        vpAdapter.apply {
            add(Homefragment(),"Home ")
            add(Businessfragment(),"Business")
            add(Technologyfragment(),"Technology")
            add(Sportsfragment(),"Sports")
        }
        viewPage.adapter = vpAdapter
        tabLayout.tabGravity=TabLayout.GRAVITY_FILL
    }
/*
    private fun fetch(){
        val url ="https://newsapi.org/v2/top-headlines?country=in&apiKey=8a27a8c795af41d0b27dbfcc79bf65c6"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsjsonObject=newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsjsonObject.getString("title"),
                        newsjsonObject.getString("author"),
                        newsjsonObject.getString("url"),
                        newsjsonObject.getString("urlToImage"),
                        newsjsonObject.getString("content"),
                        newsjsonObject.getJSONObject("source")
                    )
                    newsArray.add(news)
                }
                madapter.updatenews(newsArray)

            },
            Response.ErrorListener{
                Log.d("Error", it.toString())
            }


        ){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun clickonNews(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }*/
}

