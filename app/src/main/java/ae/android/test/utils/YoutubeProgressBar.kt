package ae.android.test.utils

import ae.android.test.R
import ae.android.test.ui.activities.MainActivity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.widget.ProgressBar
import androidx.core.content.ContextCompat

/**
 * Created by Eljo Prifti on 1/3/2018.
 */
class YoutubeProgressBar @JvmOverloads constructor(
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

    fun startProgress(mForeground: Int, mBackground: Int) {
        this.foregroundColor = if (mForeground == 0) ContextCompat.getColor(context, R.color.red) else mForeground
        this.background = if (background == 0) ContextCompat.getColor(context, R.color.border_color) else background
        val progressDrawable = ProgressDrawable(this.foregroundColor, this.background)
        this@YoutubeProgressBar.progressDrawable = progressDrawable

        val intervalTime = 2000L
        var progress = 0
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress == 100) {
                    progress = 0
                    this@YoutubeProgressBar.setProgress(progress, true)
                }
                progress += 25
                if (progress == 50) {
                    progressDrawable.mForeground = ContextCompat.getColor(context, R.color.palm_green)
                } else if (progress == 75) {
                    progressDrawable.mForeground = ContextCompat.getColor(context, R.color.teal_700)
                }
                this@YoutubeProgressBar.setProgress(progress, true)
                handler.postDelayed(this, intervalTime)
            }
        }, intervalTime)
    }

}