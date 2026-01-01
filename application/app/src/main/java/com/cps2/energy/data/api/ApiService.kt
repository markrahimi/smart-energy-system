package com.cps2.energy.data.api

import com.cps2.energy.data.models.Device
import com.cps2.energy.data.models.Notification
import com.cps2.energy.data.models.SensorData
import com.cps2.energy.data.models.User
import retrofit2.http.*

interface ApiService {
    
    // User APIs
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User

    @GET("users/username/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): User

    @POST("users")
    suspend fun createUser(@Body user: User): User

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): User


    // Device APIs
    @GET("devices")
    suspend fun getDevices(
        @Query("userId") userId: Long? = null,
        @Query("status") status: String? = null
    ): List<Device>

    @GET("devices/{id}")
    suspend fun getDeviceById(@Path("id") id: Long): Device

    @POST("devices")
    suspend fun createDevice(@Body device: Device): Device

    @PUT("devices/{id}")
    suspend fun updateDevice(@Path("id") id: Long, @Body device: Device): Device

    @DELETE("devices/{id}")
    suspend fun deleteDevice(@Path("id") id: Long)


    // Notification APIs
    @GET("notifications")
    suspend fun getNotifications(
        @Query("userId") userId: Long? = null,
        @Query("isRead") isRead: Boolean? = null,
        @Query("type") type: String? = null
    ): List<Notification>

    @GET("notifications/{id}")
    suspend fun getNotificationById(@Path("id") id: Long): Notification

    @GET("notifications/user/{userId}/unread-count")
    suspend fun getUnreadCount(@Path("userId") userId: Long): Long

    @POST("notifications")
    suspend fun createNotification(@Body notification: Notification): Notification

    @PUT("notifications/user/{userId}/read-all")
    suspend fun markAllAsRead(@Path("userId") userId: Long)


    
    // Sensor Data APIs 
    @GET("sensor-data")
    suspend fun getSensorData(
        @Query("deviceId") deviceId: Long? = null,
        @Query("start") start: String? = null,
        @Query("end") end: String? = null
    ): List<SensorData>

    @GET("sensor-data/{id}")
    suspend fun getSensorDataById(@Path("id") id: Long): SensorData

    @GET("sensor-data/device/{deviceId}/latest")
    suspend fun getLatestSensorData(@Path("deviceId") deviceId: Long): SensorData

    @POST("sensor-data")
    suspend fun createSensorData(@Body sensorData: SensorData): SensorData
}
