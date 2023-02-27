package io.my.testlearning

import androidx.test.espresso.DaggerBaseLayerComponent
import io.my.testlearning.di.DaggerAppComponent
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainActivityTest{

    @Before
    fun setUp(){
        val component = DaggerAppComponent.builder().build()
    }
}