package ae.android.test.base

import aae.android.test.R
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity : AppCompatActivity(),
    NetworkChangeReceiver.ConnectivityReceiverListener {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(
            true
        )
    }

    val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    private lateinit var intentFilter: IntentFilter
    private lateinit var receiver: NetworkChangeReceiver
    var isInternetConnected: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentFilter = IntentFilter()
        intentFilter.addAction(CONNECTIVITY_ACTION)
        receiver = NetworkChangeReceiver()
        isInternetConnected = NetworkUtil.isNetworkAvailable(this)
    }


    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isInternetConnected = isConnected
    }



    internal fun showSnackBar(text: String?) {
        val msg = when {
            text == null -> {
                getString(R.string.failed_to_parse_response)
            }
            TextUtils.isEmpty(text) -> {
                getString(R.string.invalid_response)
            }
            else -> {
                text
            }
        }
        val container = this.findViewById<View>(android.R.id.content)
        runOnUiThread {
            Snackbar.make(
                container,
                msg,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }



    open fun setUpToolbar(toolbar: Toolbar, title: String? = null) {
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(true)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setDisplayShowTitleEnabled(true)
            val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back)
            supportActionBar.setHomeAsUpIndicator(upArrow)
            title?.let {
                toolbar.title = title
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}