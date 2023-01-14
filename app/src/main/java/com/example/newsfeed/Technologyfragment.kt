package com.example.newsfeed

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.newsfeed.databinding.FragmentHomefragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Technologyfragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Technologyfragment : Fragment(),NewsClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var madapter:NewsAdapter
    lateinit var binding:FragmentHomefragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomefragmentBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerview1
        recyclerView.layoutManager = LinearLayoutManager(context)
        fetch()
        madapter= NewsAdapter(this)
        recyclerView.adapter=madapter
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Technologyfragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Technologyfragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun fetch(){
        //val con:String="us"
        val url ="https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=8a27a8c795af41d0b27dbfcc79bf65c6"
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
                        newsjsonObject.getString("publishedAt"),

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
        MySingleton.getInstance(binding.root.context).addToRequestQueue(jsonObjectRequest)
    }

    override fun clickonNews(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(binding.root.context, Uri.parse(item.url))
    }

    override fun shareClick(imageView: ImageView, item: News) {
        val intent = Intent(Intent.ACTION_SEND)
        if(imageView.height>10){
            val bitmap = imageView!!.drawable as BitmapDrawable
            val bitmap1=bitmap.bitmap
            val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver,bitmap1,item.title,null)
            intent.type ="image/*"
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
        }
        intent.type="text/*"
        intent.putExtra(Intent.EXTRA_TEXT,item.title+"\n checkout here "+item.url)
        val chooser = Intent.createChooser(intent,"share this meme using.")
        startActivity(chooser,null)
    }
}