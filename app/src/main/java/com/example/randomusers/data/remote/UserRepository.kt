package com.example.randomusers.data.remote

import com.example.randomusers.data.local.UserDao
import com.example.randomusers.data.local.UserEntity.Companion.fromUser
import com.example.randomusers.data.model.User
import com.example.randomusers.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    suspend fun getUsers(forceRefresh: Boolean = false): List<User> {
        return try {
            if (forceRefresh) {
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    userDao.clear()
                    val users = (response.body()?.results ?: emptyList()).map { it.toUser() }
                    userDao.insertAll(users.map { fromUser(it) })
                    users
                } else {
                    throw HttpException(response)
                }
            } else {
                val usersFromDb = userDao.getUsersSimpleInfo()
                if (usersFromDb.isNotEmpty()) {
                    usersFromDb.map { it.toUserSimpleInfo() }
                } else {
                    getUsers(forceRefresh = true)
                }
            }
        } catch (e: IOException) {
            val usersFromDb = userDao.getUsersSimpleInfo()
            if (usersFromDb.isNotEmpty()) {
                usersFromDb.map { it.toUserSimpleInfo() }
            } else {
                throw e
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUser(userId: String): User? {
        return userDao.getUserById(userId)?.toUser()
    }
}