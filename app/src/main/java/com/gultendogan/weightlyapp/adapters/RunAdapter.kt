package com.gultendogan.weightlyapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.db.Run
import com.bumptech.glide.Glide
import com.gultendogan.weightlyapp.other.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.ivRunImage
import kotlinx.android.synthetic.main.item_run.view.tvAvgSpeed
import kotlinx.android.synthetic.main.item_run.view.tvCalories
import kotlinx.android.synthetic.main.item_run.view.tvDate
import kotlinx.android.synthetic.main.item_run.view.tvDistance
import kotlinx.android.synthetic.main.item_run.view.tvTime
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter(private val onDeleteClickListener: (Run) -> Unit) :
    RecyclerView.Adapter<RunAdapter.RunViewHolder>(), ItemTouchHelperAdapter {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onItemSwiped(position: Int) {
        val run = differ.currentList[position]
        onDeleteClickListener.invoke(run)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        val itemView = holder.itemView

        holder.itemView.apply {
            Glide.with(this).load(run.img).into(ivRunImage)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = "Date : " + dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH} km/h"
            tvAvgSpeed.text = "Avg. Speed : " + avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f} km"
            tvDistance.text = "Distance : " + distanceInKm

            tvTime.text = "Time : " + TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned} kcal"
            tvCalories.text = "Calories : " + caloriesBurned

            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // Set the background color to indicate selection or touch feedback
                        itemView.setBackgroundColor(Color.LTGRAY)
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        // Reset the background color when touch is canceled
                        itemView.setBackgroundColor(Color.WHITE)
                    }
                    MotionEvent.ACTION_UP -> {
                        // Reset the background color when touch is released
                        itemView.setBackgroundColor(Color.WHITE)
                    }
                }
                true
            }
        }

    }


}