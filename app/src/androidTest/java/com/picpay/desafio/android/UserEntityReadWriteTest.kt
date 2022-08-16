package com.picpay.desafio.android

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.usersFeature.data.User
import com.picpay.desafio.android.usersFeature.data.UserDao
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserEntityReadWriteTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user = User(1, "img", "name", "username")
        userDao.insertAll(listOf(user))
        val users = userDao.getAll()
        assertThat(users[0], equalTo(user))
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndDeleteAllUser() {
        val user = User(1, "img", "name", "username")
        userDao.insertAll(listOf(user))
        userDao.deleteAll()
        val users = userDao.getAll()
        assert(users.isEmpty())
    }
}
