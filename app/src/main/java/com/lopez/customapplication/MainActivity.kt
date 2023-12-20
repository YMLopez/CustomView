package com.lopez.customapplication

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.lopez.customapplication.view.DragRectView


class MainActivity : AppCompatActivity() {

    private lateinit var drawLayout: RelativeLayout
    private lateinit var dragRectView: DragRectView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawLayout = findViewById(R.id.draw_view)
        dragRectView = findViewById(R.id.drag_rect_view)

        val lp = RelativeLayout.LayoutParams(200, 200)
        lp.setMargins(50, 50, 0, 0)

        dragRectView.layoutParams = lp;
        dragRectView.isClickable = true
        dragRectView.isSelected = true
        dragRectView.setMyTouchListener { dragRectView.isSelected = true }
        drawLayout.setOnClickListener { dragRectView.isSelected = false }

        findViewById<View>(R.id.create).setOnClickListener {
            val dragRectView = DragRectView(this@MainActivity)
            val lp = RelativeLayout.LayoutParams(200, 200)
            lp.setMargins(50, 50, 0, 0)
            dragRectView.layoutParams = lp
            dragRectView.isClickable = true
            dragRectView.isSelected = true
            drawLayout.addView(dragRectView)
        }
    }

}