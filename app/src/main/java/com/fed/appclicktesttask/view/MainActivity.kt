package com.fed.appclicktesttask.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fed.appclicktesttask.R


class MainActivity : AppCompatActivity() {

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = supportFragmentManager.findFragmentById(R.id.activity_container)
        if (fragment == null) {
            fragment = AdFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.activity_container, fragment)
                    .commit()
        }
    }
}
