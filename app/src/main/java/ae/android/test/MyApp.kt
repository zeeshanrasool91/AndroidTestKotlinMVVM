package ae.android.test

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class MyApp : Application() {

    companion object {
        var appContext: WeakReference<Context>? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = WeakReference<Context>(this)
    }

}