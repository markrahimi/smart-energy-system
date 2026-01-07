package com.cps2.energy.data.api

import com.cps2.energy.data.models.Device
import com.cps2.energy.data.models.Notification
import com.cps2.energy.data.models.SensorData
import com.cps2.energy.data.models.Threshold
import com.cps2.energy.data.models.User
import retrofit2.http.*

interface ApiService {

    // User APIs
    @GET("users") suspend fun getUsers(): List<User>

    @GET("users/{id}") suspend fun getUserById(@Path("id") id: String): User

    @GET("users/username/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): User

    @POST("users") suspend fun createUser(@Body user: User): User

    @PUT("users/{id}") suspend fun updateUser(@Path("id") id: String, @Body user: User): User

    // Device APIs
    @GET("devices")
    suspend fun getDevices(
            @Query("userId") userId: String? = null,
            @Query("status") status: String? = null
    ): List<Device>

    @GET("devices/{id}") suspend fun getDeviceById(@Path("id") id: String): Device

    @POST("devices") suspend fun createDevice(@Body device: Device): Device

    @PUT("devices/{id}")
    suspend fun updateDevice(@Path("id") id: String, @Body device: Device): Device

    @DELETE("devices/{id}") suspend fun deleteDevice(@Path("id") id: String)

    // Notification APIs
    @GET("notifications")
    suspend fun getNotifications(
            @Query("userId") userId: String? = null,
            @Query("isRead") isRead: Boolean? = null,
            @Query("type") type: String? = null
    ): List<Notification>

    @GET("notifications/{id}") suspend fun getNotificationById(@Path("id") id: String): Notification

    @GET("notifications/unread-count")
    suspend fun getUnreadCount(@Query("userId") userId: String): Long

    @POST("notifications")
    suspend fun createNotification(@Body notification: Notification): Notification

    @POST("notifications/mark-all-read") suspend fun markAllAsRead(@Query("userId") userId: String)

    // Sensor Data APIs
    @GET("sensor-data")
    suspend fun getSensorData(
            @Query("deviceId") deviceId: String? = null,
            @Query("start") start: String? = null,
            @Query("end") end: String? = null
    ): List<SensorData>

    @GET("sensor-data/{id}") suspend fun getSensorDataById(@Path("id") id: String): SensorData

    @GET("sensor-data/latest")
    suspend fun getLatestSensorData(@Query("deviceId") deviceId: String): SensorData

    @POST("sensor-data") suspend fun createSensorData(@Body sensorData: SensorData): SensorData

    // Threshold APIs
    @GET("thresholds")
    suspend fun getThresholds(@Query("userId") userId: String? = null): List<Threshold>

    @GET("thresholds/device/{deviceId}")
    suspend fun getThresholdByDeviceId(@Path("deviceId") deviceId: String): Threshold

    @POST("thresholds") suspend fun createThreshold(@Body threshold: Threshold): Threshold

    @PUT("thresholds/device/{deviceId}")
    suspend fun updateThreshold(
            @Path("deviceId") deviceId: String,
            @Body threshold: Threshold
    ): Threshold

    @DELETE("thresholds/device/{deviceId}")
    suspend fun deleteThreshold(@Path("deviceId") deviceId: String)
}
