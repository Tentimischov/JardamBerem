package com.baktiyar.android.jardamberem.utils

import com.baktiyar.android.jardamberem.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface ApiService {

    @GET("api/categories/{categoryId}/announcements")
    fun getProductByCategory(@Path("categoryId") id: Int): Call<List<Product>>


    @POST("api/cities/{id_of_city}/categories/{id_of_category}/announcements/")
    fun sendProduct(@Path("id_of_city") city: Int, @Path("id_of_category") id: Int,
                    @Body file: RequestBody): Call<ResponseBody>

    @DELETE("api/categories/{id_of_category}/announcements/{id_of_product}/")
    fun deleteProductById(@Path("id_of_category") categoryId: String,
                          @Path("id_of_product") productId: String): Call<ResponseBody>

    @DELETE("allannouncements/{id_of_announcement}/")
    fun deleteAnnouncement(@Path("id_of_announcement") id: Int): Call<ResponseBody>

    @POST("review/")
    fun sendFeedback(@Body feedback: Feedback): Call<ResponseBody>

    @POST
    fun sendComplainOnUser(productId: Int, androidId: Int): Call<ResponseBody>

    @GET("allannouncements/IsNeeded")
    fun getAnnouncements(@Query("limit") limit: Int,
                         @Query("offset") offset: Int):
            Call<AnnouncementsPaginated>

    @GET("allannouncements/IsNeededFalse")
    fun getAnnouncementsIsNeededFalse(@Query("limit") limit: Int,
                                      @Query("offset") offset: Int):
            Call<AnnouncementsPaginated>


   /*
   ------------------------------
   * request without pagination *
   ------------------------------


   @GET("allannouncements/IsNeeded")
    fun getAnnouncementss(@Query("limit") limit: Int,
                          @Query("offset") offset: Int):
            Call<ArrayList<Announcements>>

    @GET("allannouncements/IsNeededFalse")
    fun getAnnouncementsIsNeededFalses(@Query("limit") limit: Int,
                                       @Query("offset") offset: Int):
            Call<ArrayList<Announcements>>*/


    @GET("api/cities/{id_of_city}/categories/")
    fun getCategory(@Path("id_of_city") id: Int): Call<CategoryPaginated>

    @GET("api/cities")
    fun getCities(): Call<CityPaginated>

    @GET("charity_event")
    fun getActionData(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<ActionDataPaginated>

    @GET("info")
    fun getInfo(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InfoPaginated>

    @GET("history")
    fun getUrgent(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<UrgentPaginated>

    @GET("api/cities/{id_of_city}/categories/{id_of_category}/announcements")
    fun getAnnounByCategory(@Path("id_of_city") cityId: Int,
                            @Path("id_of_category") categoryId: Int,
                            @Query("limit") limit: Int, @Query("offset") offset: Int): Call<AnnouncementsPaginated>

    @GET("forum")
    fun getForum(): Call<ForumPaginated>

    @POST("forum/")
    fun sendForum(@Body forum: Forum): Call<ResponseBody>

    @GET("allannouncements/")
    fun getSearch(@Query("??city") city: Int,
                  @Query("title") title: String): Call<AnnouncementsPaginated>

}
