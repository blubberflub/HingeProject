package com.example.hingeproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hingeproject.databinding.ProfileLayoutBinding
import com.example.hingeproject.model.Testing

class BasicListAdapter : ListAdapter<Testing ,BasicListAdapter.ProfileViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = ProfileLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ProfileViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    object DiffCallback : DiffUtil.ItemCallback<Testing>() {
        override fun areItemsTheSame(oldItem: Testing, newItem: Testing): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Testing, newItem: Testing): Boolean {
            return oldItem == newItem
        }
    }

    class ProfileViewHolder(val profileLayoutBinding: ProfileLayoutBinding) : RecyclerView.ViewHolder(profileLayoutBinding.root) {
        fun bind(testing: Testing) {
            profileLayoutBinding.testingTextView.text = testing.test
        }
    }
}
