package com.my.project.firstkotlin.data.remote.data

import com.my.project.firstkotlin.data.remote.CommonRemote
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("query") receipt : String,
        @Query("number") number : Int,
        @Query("offset") offset : Int = 0
    ) : Response<RecipeResponse>

    companion object {
        operator fun invoke() : RecipeApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", CommonRemote.API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                    return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(CommonRemote.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApiService::class.java)
        }
    }

}