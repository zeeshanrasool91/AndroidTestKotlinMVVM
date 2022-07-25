package ae.android.test.base.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by gurleensethi on 30/07/17.
 * Convenient RecyclerAdapter with generic data type list.
 * Create an instance and specify the type of data being passed.
 * Implement callbacks to handle the required functions
 * https://medium.com/androiddevelopers/android-data-binding-recyclerview-db7c40d9f0e4
 * Motivation: Create a recycler adapter in minimal lines of code yet
 * providing high customisation and complete control
 */
//https://blog.devgenius.io/android-generic-recyclerview-adapter-67eb8f826cad
open class RecyclerAdapterUtil<T>(
    private val context: Context,
    //This list will serve as the main data list for the Recycler Adapter
    var itemList: MutableList<T>,
    //The id of layout resource that is to be inflated when onCreateViewHolder is called
    private val viewHolderLayoutRecourse: Int
) : RecyclerView.Adapter<RecyclerAdapterUtil<T>.ViewHolder>() {

    /**
     * List containing all the views that are contained in the single item layout file
     * There are retained before hand to improve performance and avoiding unnecessary calls
     * to findViewById every time data is bound
     * */
    private var mViewsList: List<Int> = mutableListOf()

    /**
     * Listener to bind the data with the single item view.
     * The View and item of type T provided by the user are passed as the arguments
     * Any view contained in the single view of the RecyclerView can be obtained
     * from itemView, and the required data can be set from item
     **/
    private var mOnDataBindListener: ((itemView: View, item: T, position: Int, innerViews: Map<Int, View>) -> Unit)? =
        null

    /**
     * Lambda for handling the onClick callback for a view.
     * The position of click and the corresponding item of type T from the itemList
     * are passed as arguments
     * */
    private var mOnClickListener: ((item: T, position: Int) -> Unit)? = null

    /**
     * Lambda for handling the onLongClick callback for a view.
     * The position of long click and the corresponding item of type T from the itemList
     * are passed as arguments
     * */
    private var mOnLongClickListener: ((item: T, position: Int) -> Unit)? = null

    /**
     * Setters for views list
     * */
    fun addViewsList(vararg viewsList: Int) {
        mViewsList = viewsList.asList()
    }

    fun addViewsList(viewsList: List<Int>) {
        mViewsList = viewsList;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewHolderLayoutRecourse, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {

        /**
         * Mutable Map holding all the bindings to inner views provided by the user
         * */
        private var viewMap: MutableMap<Int, View> = mutableMapOf()

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

            for (view in mViewsList) {
                viewMap[view] = itemView.findViewById(view)
            }
        }

        fun bindData(position: Int) {
            mOnDataBindListener?.invoke(itemView, itemList[position], position, viewMap)
        }

        override fun onClick(view: View?) {
            mOnClickListener?.invoke(itemList[absoluteAdapterPosition], absoluteAdapterPosition)
        }

        override fun onLongClick(view: View?): Boolean {
            mOnLongClickListener?.invoke(itemList[absoluteAdapterPosition], absoluteAdapterPosition)
            return true
        }
    }

    fun addOnDataBindListener(listener: (itemView: View, item: T, position: Int, innerViews: Map<Int, View>) -> Unit) {
        mOnDataBindListener = listener
    }

    fun addOnClickListener(listener: (item: T, position: Int) -> Unit) {
        mOnClickListener = listener
    }

    fun addOnLongClickListener(listener: (item: T, position: Int) -> Unit) {
        mOnLongClickListener = listener
    }

    /**
     * Builder class for setting up recycler adapter
     */
    class Builder<T>(context: Context, val itemList: ArrayList<T>, viewHolderLayoutRecourse: Int) {

        private var mRecyclerAdapter: RecyclerAdapterUtil<T> =
            RecyclerAdapterUtil(context, itemList, viewHolderLayoutRecourse)

        fun bindView(listener: (itemView: View, item: T, position: Int, innerViews: Map<Int, View>) -> Unit): Builder<T> {
            mRecyclerAdapter.addOnDataBindListener(listener)
            return this
        }

        fun addClickListener(listener: (item: T, position: Int) -> Unit): Builder<T> {
            mRecyclerAdapter.addOnClickListener(listener)
            return this
        }

        fun addLongClickListener(listener: (item: T, position: Int) -> Unit): Builder<T> {
            mRecyclerAdapter.addOnLongClickListener(listener)
            return this
        }

        fun viewsList(viewsList: List<Int>): Builder<T> {
            mRecyclerAdapter.addViewsList(viewsList)
            return this
        }

        fun viewsList(vararg viewsList: Int): Builder<T> {
            mRecyclerAdapter.addViewsList(viewsList.asList())
            return this
        }


        /*
        * Return the created adapter
        * */
        fun build(): RecyclerAdapterUtil<T> = mRecyclerAdapter

        /*
        * Set the adapter to the recycler view
        * */
        fun into(recyclerView: RecyclerView) {
            recyclerView.adapter = mRecyclerAdapter
        }

    }

    fun addUpdateItemsList(updatedList: ArrayList<T>) {
        itemList.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun resetItemsList(updatedList: ArrayList<T>) {
        itemList = ArrayList()
        itemList.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun clearItemsList() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun addItems(itemList: ArrayList<T>) {
        this.itemList.addAll(itemList)
    }


}

class ViewItem<T : View>(val viewId: Int)