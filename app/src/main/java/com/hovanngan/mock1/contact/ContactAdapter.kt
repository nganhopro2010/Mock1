package com.hovanngan.mock1.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hovanngan.mock1.R
import com.hovanngan.mock1.databinding.ContactItemBinding
import com.hovanngan.mock1.model.Contact

class ContactAdapter :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private val data = ArrayList<Contact>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mBinding: ContactItemBinding? = null

        init {
            mBinding = DataBindingUtil.bind(itemView)
        }

    }

    fun setData(data: ArrayList<Contact>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.mBinding!!.apply {
            tvName.text = item.name
            tvPhoneNumber.text = item.phoneNumber
            if (item.img != null) {
                imgProfile.setImageBitmap(item.img)
            } else {
                imgProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.ic_contact_default_24dp
                    )
                )
            }
        }

    }

    override fun getItemCount(): Int = data.size
}

