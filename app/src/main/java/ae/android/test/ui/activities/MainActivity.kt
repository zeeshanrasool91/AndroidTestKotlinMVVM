package ae.android.test.ui.activities


import ae.android.test.R
import ae.android.test.base.BaseActivity
import ae.android.test.base.adapter.RecyclerAdapterUtil
import ae.android.test.databinding.ActivityMainBinding
import ae.android.test.databinding.ItemMostPopularBinding
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MostPopularResponse
import ae.android.test.utils.hide
import ae.android.test.utils.show
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"

    }

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        prepareToolbar()
        //viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.callListApi()
        //binding.progressBar.progressDrawable = ProgressDrawable(ContextCompat.getColor(this, R.color.red), ContextCompat.getColor(this, R.color.border_color))
        /*val intervalTime = 2000L
        var progress = 0
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progress == 100) {
                    progress = 0
                    binding.progressBar.setProgress(progress,true)
                }
                progress += 25
                Log.d(TAG, "run: $progress")
                binding.progressBar.setProgress(progress,true)
                handler.postDelayed(this, intervalTime)
            }
        }, intervalTime)*/

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataToView.collectLatest { response ->
                    when (response) {
                        is ResultWrapper.Exception -> {
                            response.responseExceptionBody.printStackTrace()
                            //binding.progressBar.visibility = View.GONE
                        }
                        is ResultWrapper.Success -> {
                            //binding.progressBar.visibility = View.GONE
                            createAdapter(response.responseBody.results)
                        }
                        is ResultWrapper.Failed -> {
                            response.responseErrorBody?.let {
                                //binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private var adapter: RecyclerAdapterUtil<MostPopularResponse>? = null
    private fun createAdapter(rates: MutableList<MostPopularResponse>) {
        adapter = RecyclerAdapterUtil(this, rates, R.layout.item_most_popular)
        adapter!!.addOnDataBindListener { itemView, item, position, _ ->
            val binding = DataBindingUtil.bind<ItemMostPopularBinding>(itemView)
            binding?.let {
                binding.responseModel = item
                if (position == rates.lastIndex) {
                    it.bottomSeparator.hide()
                } else {
                    it.bottomSeparator.show()
                }
                binding.executePendingBindings()
            }

        }
        adapter!!.addOnClickListener { item, position ->
            Toast.makeText(
                this@MainActivity, "Position: $position\nTitle: ${item.title}", Toast.LENGTH_SHORT
            ).show()
        }
        binding.recyclerView.adapter = adapter
    }

    private fun prepareToolbar() {
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_menu_white_24dp
                )
            )
            it.title = resources.getString(R.string.main_activity_title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_more -> {
                Toast.makeText(this@MainActivity, "More Action clicked", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_search -> {
                Toast.makeText(this@MainActivity, "Search Action clicked", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}