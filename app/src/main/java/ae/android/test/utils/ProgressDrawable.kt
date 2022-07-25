package ae.android.test.utils

import android.graphics.*
import android.graphics.drawable.Drawable

class ProgressDrawable(var mForeground: Int, private val mBackground: Int) : Drawable() {
    private val mPaint = Paint()
    private val mSegment = RectF()
    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {
        val level = level / 10000f
        val b = bounds
        val gapWidth = b.height() / 2f
        val segmentWidth = (b.width() - (NUM_SEGMENTS - 1) * gapWidth) / NUM_SEGMENTS
        mSegment[0f, 0f, segmentWidth] = b.height().toFloat()
        mPaint.color = mForeground
        for (i in 0 until NUM_SEGMENTS) {
            val loLevel = i / NUM_SEGMENTS.toFloat()
            val hiLevel = (i + 1) / NUM_SEGMENTS.toFloat()
            if (level in loLevel..hiLevel) {
                val middle = mSegment.left + NUM_SEGMENTS * segmentWidth * (level - loLevel)
                canvas.drawRoundRect(mSegment.left, mSegment.top, middle, mSegment.bottom, RADIUS.px.toFloat(), RADIUS.px.toFloat(), mPaint)
                mPaint.color = mBackground
                canvas.drawRoundRect(middle, mSegment.top, mSegment.right, mSegment.bottom, RADIUS.px.toFloat(), RADIUS.px.toFloat(), mPaint)
            } else {
                canvas.drawRoundRect(mSegment, RADIUS.px.toFloat(), RADIUS.px.toFloat(), mPaint)
            }
            mSegment.offset(mSegment.width() + gapWidth, 0f)
        }
    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    companion object {
        private const val NUM_SEGMENTS = 4
        private const val RADIUS = 5
    }
}