package com.shiraj.gui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchGithubUserActivityTest : TestCase() {

    private lateinit var scenario: ActivityScenario<SearchGithubUserActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.STARTED)
    }


    @Test
    fun testAddAndViewSpend() {
        val searchKeyword = "jakewharton"
        Espresso.onView(withId(R.id.edtLogin))
            .perform(ViewActions.typeText(searchKeyword))
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(R.id.btnSubmit)).perform(ViewActions.click())
    }
}