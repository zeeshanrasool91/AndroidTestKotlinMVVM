package ae.android.test

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.lang.ref.WeakReference


class MyApp : Application() {

    companion object {
        var appContext: WeakReference<Context>? = null
    }

    override fun onCreate() {
        super.onCreate()
        ae.android.test.MyApp.Companion.appContext = WeakReference<Context>(this)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            androidFileProperties()
        }
    }

}