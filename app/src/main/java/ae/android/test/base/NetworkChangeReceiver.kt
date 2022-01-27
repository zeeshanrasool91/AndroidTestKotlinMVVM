package ae.android.test.base


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkChangeReceiver : BroadcastReceiver() {
    private var mContext: Context? = null
    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        val status = NetworkUtil.getConnectivityStatusString(context)
        //Log.e("Receiver ", "" + status);
        if (status == AppConstants.NOT_CONNECT) {
            //Log.e("Receiver ", "not connection");// your code when internet lost
            if (connectivityReceiverListener != null) {
                connectivityReceiverListener!!.onNetworkConnectionChanged(false)
            }
        } else {
            //Log.e("Receiver ", "connected to internet");//your code when internet connection come back
            if (connectivityReceiverListener != null) {
                connectivityReceiverListener!!.onNetworkConnectionChanged(true)
            }
        }
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}