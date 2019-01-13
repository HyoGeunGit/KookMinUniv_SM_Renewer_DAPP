package la.hitomi.sm

import retrofit2.Call
import retrofit2.http.*
import okhttp3.ResponseBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET



/**
* Created by Kinetic on 2018-06-02.
*/

interface API {
    @GET("/place")
    fun getPlace(): Call<ArrayList<LocationRepo>>

    @POST("/auth/signin")
    @FormUrlEncoded
    fun logIn(@Field("id") id: String, @Field("pw") pw: String): Call<ResponseBody>

    @POST("/auth/signup")
    @FormUrlEncoded
    fun logUp(@Field("name") name: String, @Field("id") id: String, @Field("pw") pw: String): Call<ResponseBody>

    @GET("/sns")
    fun getsnsList(): Call<ArrayList<PeopleRepo>>

    @POST("/sns")
    @FormUrlEncoded
    fun writesns(@Field("writer") writer: String, @Field("content") content: String): Call<ResponseBody>

    @GET("/sns/{id}")
    fun getsnsDetail(@Path("id") id: String): Call<ResponseBody>

    @POST("/sns/{id}")
    @FormUrlEncoded
    fun writeAnswer(@Path("id") id: String, @Field("writer") writer: String, @Field("content") content: String): Call<ResponseBody>
}