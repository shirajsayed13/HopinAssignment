package com.shiraj.gui.result

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shiraj.gui.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchResultFragmentTest : TestCase() {

    private lateinit var scenario: FragmentScenario<SearchResultFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_TDDLearning)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testSearchResult() {

    }

}