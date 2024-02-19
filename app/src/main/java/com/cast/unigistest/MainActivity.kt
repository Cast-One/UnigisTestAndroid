package com.cast.unigistest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cast.unigistest.adapters.EndlessRecyclerViewScrollListener
import com.cast.unigistest.adapters.MovieNowAdapter
import com.cast.unigistest.adapters.MoviePopularAdapter
import com.cast.unigistest.databinding.ActivityMainBinding
import com.cast.unigistest.interfaces.MovieApiService
import com.cast.unigistest.models.MovieModel
import com.cast.unigistest.response.MovieDetailsResponse
import com.cast.unigistest.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val apiKey = "55957fcf3ba81b137f8fc01ac5a31fb5"
    private val baseURL = "https://api.themoviedb.org/3/"

    private lateinit var binding: ActivityMainBinding
    private val listPlayingNow = ArrayList<MovieModel>()
    private val listPopularMovies = ArrayList<MovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getNowMoviesList()
        getPopularMoviesList()
        checkMovies(933131)


        binding.recyclerPlayingNow.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMostPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerPlayingNow.addOnScrollListener(object : EndlessRecyclerViewScrollListener(binding.recyclerPlayingNow.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int) {
                getNowMoviesList(page)
            }
        })
        binding.recyclerMostPopular.addOnScrollListener(object : EndlessRecyclerViewScrollListener(binding.recyclerPlayingNow.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int) {
                getNowMoviesList(page)
            }
        })
    }

    private fun getNowMoviesList(page: Int = 1){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieApiService::class.java)
        val call = service.getNowPlayingMovies(apiKey = apiKey, "en-US", page = page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.body()?.results?.isEmpty() == false) {
                    setListPlayingNow(response.body()!!.results as ArrayList<MovieModel>)
                    val adapter = MovieNowAdapter(this@MainActivity, getListPlayingNow())
                    binding.recyclerPlayingNow.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun getPopularMoviesList(page: Int = 1){
        val service = getRetrofit().create(MovieApiService::class.java)
        val call = service.getPopularMovies(apiKey = apiKey, "en-US", page = page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.body()?.results?.isEmpty() == false) {
                    setListPopularMovie(response.body()!!.results as ArrayList<MovieModel>)
                    val adapter = MoviePopularAdapter(this@MainActivity, getListPopularMovie())
                    binding.recyclerMostPopular.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun setListPlayingNow(listPlayingNowResponse: ArrayList<MovieModel>){
        listPlayingNow.clear()
        listPlayingNow.addAll(listPlayingNowResponse)
    }

    private fun getListPlayingNow(): ArrayList<MovieModel> {
        return listPlayingNow
    }

    private fun setListPopularMovie(listPopularResponse: ArrayList<MovieModel>){
        listPopularMovies.clear()
        listPopularMovies.addAll(listPopularResponse)
    }

    private fun getListPopularMovie(): ArrayList<MovieModel> {
        return listPopularMovies
    }

    private fun checkMovies(movieId: Int){
        val service = getRetrofit().create(MovieApiService::class.java)
        val call = service.getMovieDetails(movieId, apiKey)

        call.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {
                if (response.body() != null) {
                    val movieDetails = response.body() as MovieDetailsResponse
                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })

    }

}