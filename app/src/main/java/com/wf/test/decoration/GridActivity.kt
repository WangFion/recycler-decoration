package com.wf.test.decoration

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wf.recycle.decoration.SimpleRecyclerDecoration
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * SimpleRecycleDecoration -> com.wf.test.decoration -> GridActivity
 *
 * @Author: wf-pc
 * @Date: 2020-06-15 16:54
 */
class GridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        val list = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.addItemDecoration(
            SimpleRecyclerDecoration(
                Color.parseColor("#ff0000"),
                Color.parseColor("#000000"),
                20, 40
            )
        )
        recyclerView.adapter = object : CommonAdapter<Int>(this, R.layout.layout_item, list) {
            override fun convert(holder: ViewHolder, integer: Int, position: Int) {
                holder.setText(R.id.tv_item, "" + position)
            }
        }
    }
}