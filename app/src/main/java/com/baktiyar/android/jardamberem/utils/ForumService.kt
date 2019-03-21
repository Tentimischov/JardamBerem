package com.baktiyar.android.jardamberem.utils

import com.baktiyar.android.jardamberem.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ForumService {

    @GET("api/categories/{categoryId}/announcements")
    fun getProductByCategory(@Path("categoryId") id: Int): Call<List<Product>>

    @POST("api/categories/{id_of_category}/announcements/")
    fun sendProduct(@Path("id_of_category") id: Int,
                    @Body file: RequestBody): Call<ResponseBody>

    @POST("history/")
    fun sendUrgentProduct(@Body file: RequestBody): Call<ResponseBody>


    @DELETE("allannouncements/{id_of_announcement}/")
    fun deleteAnnouncement(@Path("id_of_announcement") id: Int): Call<ResponseBody>

    @DELETE("history/{id_of_history}/")
    fun deleteUrgent(@Path("id_of_history") id: Int): Call<ResponseBody>


    @POST("review/")
    fun sendFeedback(@Body feedback: Feedback): Call<ResponseBody>

    @GET("api/categories/")
    fun getCategory(): Call<CategoryPaginated>

    @GET("city")
    fun getCities(): Call<CityPaginated>

    @GET("info")
    fun getInfo(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InfoPaginated>

    @GET("history")
    fun getUrgent(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<UrgentPaginated>

    @GET("api/categories/{id_of_category}/announcements/?")
    fun getAnnouncementByCategory(@Path("id_of_category") categoryId: Int,
                                  @Query("limit") limit: Int,
                                  @Query("offset") offset: Int,
                                  @Query("isNeeded") isNeeded: Boolean,
                                  @Query("city_id") cityId: Int): Call<AnnouncementsPaginated>

    @GET("charity_event")
    fun getActionData(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<ActionDataPaginated>

    @GET("forum")
    fun getForum(@Query("limit") limit: Int,
                 @Query("offset") offset: Int): Call<ForumPaginated>

    @POST("forum/")
    fun sendForum(@Body forum: Forum): Call<ResponseBody>

    @GET("announcement?")
    fun getSearch(@Query("search") search: String): Call<AnnouncementsPaginated>

    @DELETE("forum/{id_of_forum}/")
    fun deleteForum(@Path("id_of_forum") id: Int): Call<ResponseBody>

    @GET("announcements?")
    fun getAnnouncement(@Query("limit") limit: Int,
                        @Query("offset") offset: Int,
                        @Query("isNeeded") isNeeded: Boolean,
                        @Query("city_id") cityId: Int) : Call<AnnouncementsPaginated>




}
