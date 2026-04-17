package com.example.havadurumuuygulamasi

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.havadurumuuygulamasi.view.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testActivityLaunchesSuccessfully() {
        // Activity'nin çökmeden başlatılabildiğini test eder.
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        
        scenario.onActivity { activity ->
            assert(activity != null)
        }
    }
}
