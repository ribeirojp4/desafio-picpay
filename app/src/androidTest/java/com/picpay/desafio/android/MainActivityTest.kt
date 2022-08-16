package com.picpay.desafio.android

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.usersFeature.MainActivity
import com.picpay.desafio.android.usersFeature.data.UserVO
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldDisplayTitle() {
        onView(withText(context.getString(R.string.title))).check(matches(isDisplayed()))
    }

    @Test
    fun whenServerResponseIsSuccessThenShouldDisplayListItem() {
        runMockServer("/users") {
            onData(
                allOf(
                    `is`(instanceOf(UserVO::class.java)),
                    hasEntry(equalTo("Eduardo Santos"), `is`("@eduardo.santos"))
                )
            )
        }
    }

    @Test
    fun whenServerResponseIsErrorThenShouldHideRecyclerView() {
        runMockServer(null) {
            onView(withId(R.id.usersRecyclerView)).check(matches(not(isDisplayed())))
        }
    }
}
