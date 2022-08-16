package com.picpay.desafio.android.usersFeature.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.AppDatabase
import com.picpay.desafio.android.usersFeature.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val userType = object : TypeToken<List<User>>() {}.type
                        val users: List<User> = Gson().fromJson(jsonReader, userType)

                        AppDatabase.getInstance(applicationContext).apply {
                            userDao().insertAll(users)
                        }

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error user database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error user database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "UserDatabaseWorker"
        const val KEY_FILENAME = "USER_DATA_FILENAME"
    }
}
