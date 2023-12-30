package com.southernsunrise.notesapp.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.utils.BitmapUtils.convertBitmapToString
import kotlin.math.abs


class DrawingView(context: Context, attrs: android.util.AttributeSet) : View(context, attrs) {

    companion object {
        private const val TOUCH_TOLERANCE = 1f
    }

    private var mPaint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = context.getColor(R.color.color_secondary)
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
    }

    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mPath: Path = Path()
    private val mBitmapPaint: Paint = Paint(Paint.DITHER_FLAG)
    private val circlePaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.MITER
        strokeWidth = 4f
    }
    private val circlePath: Path = Path()
    private var mX = 0f
    private var mY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint)
       // mBitmap!!.convertBitmapToString().toLog("bitmap in onDraw::::::")
        canvas.drawPath(mPath, mPaint)
        canvas.drawPath(circlePath, circlePaint)
    }

    private fun actionTouchDown(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun actionTouchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
            circlePath.reset()
            circlePath.addCircle(mX, mY, 3f, Path.Direction.CW)

        }
    }

    private fun actionTouchUp() {
        mPath.lineTo(mX, mY)
        circlePath.reset()
        // commit the path to our offscreen
        mCanvas?.drawPath(mPath, mPaint)
        // kill this so we don't double draw
        mPath.reset()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                actionTouchDown(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                actionTouchMove(x, y)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                actionTouchUp()
                invalidate()
            }
        }
        return true
    }

    fun setDrawWidth(width: Float) {
        mPaint.strokeWidth = width
    }

    fun setPaintColor(@ColorInt color: Int) {
        mPaint.color = color
    }

    fun getDrawingBitmapString(): String? = mBitmap?.convertBitmapToString()

    fun setBitmap(bitmap: Bitmap) {
        mBitmap = bitmap
        invalidate()
    }

    fun clearDrawing() {
        mBitmap?.eraseColor(Color.TRANSPARENT)
        invalidate()
    }

}