package la.hitomi.sm


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
* Created by Kinetic on 2018-06-02.
*/
object Client {
    var retrofitService: API
    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val logger = OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://spirit-in-motion.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(logger)
                .build()

        retrofitService = retrofit.create(API::class.java)


    }
}