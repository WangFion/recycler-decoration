package com.wf.test.decoration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun horizontalDivider(view: View?) {
        val intent = Intent(this, LinearActivity::class.java)
        intent.putExtra("direction", LinearLayoutManager.VERTICAL)
        startActivity(intent)
    }

    fun verticalDivider(view: View?) {
        val intent = Intent(this, LinearActivity::class.java)
        intent.putExtra("direction", LinearLayoutManager.HORIZONTAL)
        startActivity(intent)
    }

    fun gridDivider(view: View?) {
        val intent = Intent(this, GridActivity::class.java)
        startActivity(intent)
    }
}