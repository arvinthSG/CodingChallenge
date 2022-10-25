package com.example.codingchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codingchallenge.Betfanatics.CodingChallengeFragmentView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homepageFragment = CodingChallengeFragmentView.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_container,
                homepageFragment,
                CodingChallengeFragmentView.TAG
            )
            .commit()
    }
}