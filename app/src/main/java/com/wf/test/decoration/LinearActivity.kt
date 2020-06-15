package com.wf.test.decoration

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wf.test.recycle.decoration.SimpleRecyclerDecoration
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * SimpleRecycleDecoration -> com.wf.test.decoration -> LinearActivity
 *
 * @Author: wf-pc
 * @Date: 2020-06-15 16:54
 */
open class LinearActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear)

        val direction = intent.getIntExtra("direction", LinearLayoutManager.VERTICAL)

        val list = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        val recyclerView: RecyclerView = findViewById(R.id.recycleView)

        if (direction == LinearLayoutManager.HORIZONTAL) {

            recyclerView.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
            )
            recyclerView.addItemDecoration(
                SimpleRecyclerDecoration(
                    Color.parseColor("#0000ff"),
                    20
                )
            )
            recyclerView.adapter = object : CommonAdapter<Int>(this, R.layout.layout_item2, list) {
                override fun convert(holder: ViewHolder, integer: Int, position: Int) {
                    holder.setText(R.id.tv_item, "" + position)
                }
            }

        } else {

            recyclerView.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
            recyclerView.addItemDecoration(
                SimpleRecyclerDecoration(Color.parseColor("#0000ff"), 20)
            )
            recyclerView.adapter = object : CommonAdapter<Int>(this, R.layout.layout_item, list) {
                override fun convert(holder: ViewHolder, integer: Int, position: Int) {
                    holder.setText(R.id.tv_item, "" + position)
                }
            }
        }

    }

}