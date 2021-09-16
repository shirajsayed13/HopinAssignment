package com.shiraj.gui.search

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shiraj.gui.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest : TestCase() {

    private lateinit var scenario: FragmentScenario<SearchFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_TDDLearning)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testSearchKeywordWithSubmit() {
        val searchKeyword = "jakewharton"
        Espresso.onView(withId(R.id.edtLogin))
            .perform(ViewActions.typeText(searchKeyword))
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(R.id.btnSubmit)).perform(ViewActions.click())
    }
}