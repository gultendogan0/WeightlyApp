package com.gultendogan.weightlyapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.base.BaseListAdapter
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel

class WeightHistoryAdapter(private val onClickWeight: ((weight: WeightUIModel) -> Unit)?) :
    BaseListAdapter<WeightUIModel>(
        itemsSame = { old, new -> old.uid == new.uid },
        contentsSame = { old, new -> old == new }
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_weight_history, parent, false)
        return WeightHistoryViewHolder(view, onClickWeight)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeightHistoryViewHolder -> {
                getItem(position)?.let { item -> holder.bind(item) }
            }
        }
    }
}