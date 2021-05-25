package com.hovanngan.mock1

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hovanngan.mock1.callhistory.CallHistoryFragment
import com.hovanngan.mock1.contact.ContactFragment
import com.hovanngan.mock1.model.Contact
import com.hovanngan.mock1.model.History

class PagerAdapter(private val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var contacts = ArrayList<Contact>()
    private var histories = ArrayList<History>()

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ContactFragment.newInstance(contacts)
            }
            else -> {
                CallHistoryFragment.newInstance(histories)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        var title = ""
        title = when (position) {
            0 -> {
                context.getString(R.string.danh_ba)
            }
            else -> {
                context.getString(R.string.nhat_ky)
            }
        }
        return title
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
    fun setData(contacts: ArrayList<Contact>, histories: ArrayList<History>) {
        this.contacts = contacts
        this.histories = histories
        notifyDataSetChanged()
    }
}

