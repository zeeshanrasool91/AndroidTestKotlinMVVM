package ae.android.test.utils

import ae.android.test.R
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat

/**
 * Created by Zeeshan Rasool on 25/07/2022.
 */
class SegmentedProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.progressBarStyleHorizontal
) :
    ProgressBar(context, attrs, defStyle) {

    var foregroundColor: Int = 0
    var background: Int = 0

    init {
        progress = 0
        secondaryProgress = 0
        max = 100
        startProgress(0, 0)
    }

    fun startProgress(foregroundColor: Int, background: Int) {
        this.foregroundColor = if (foregroundColor == 0) ContextCompat.getColor(context, R.color.pg_red) else foregroundColor
        this.background = if (background == 0) ContextCompat.getColor(context, R.color.border_color) else background
        val segmentedProgressDrawable = SegmentedProgressDrawable(this.foregroundColor, this.background)
        this@SegmentedProgressBar.progressDrawable = segmentedProgressDrawable

        val intervalTime = 2000L
        var progress = 0
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress == 100) {
                    progress = 0
                    segmentedProgressDrawable.mForeground = this@SegmentedProgressBar.foregroundColor
                    this@SegmentedProgressBar.setProgress(progress, true)
                }
                progress += 25
                when (progress) {
                    25 -> {
                        segmentedProgressDrawable.mForeground = ContextCompat.getColor(context, R.color.pg_red)
                    }
                    50 -> {
                        segmentedProgressDrawable.mForeground = ContextCompat.getColor(context, R.color.pg_orange)
                    }
                    75 -> {
                        segmentedProgressDrawable.mForeground = ContextCompat.getColor(context, R.color.pg_green)
                    }
                    100 -> {
                        segmentedProgressDrawable.mForeground = ContextCompat.getColor(context, R.color.pg_full)
                    }
                }
                this@SegmentedProgressBar.setProgress(progress, true)
                handler.postDelayed(this, intervalTime)
            }
        }, intervalTime)
    }

}