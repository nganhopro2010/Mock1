package com.hovanngan.mock1.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hovanngan.mock1.R
import com.hovanngan.mock1.databinding.FragmentContactBinding
import com.hovanngan.mock1.model.Contact


class ContactFragment : Fragment() {
    private var mBinding: FragmentContactBinding? = null
    private lateinit var contactAdapter: ContactAdapter
    private var data = ArrayList<Contact>()

    companion object {
        fun newInstance(contacts: ArrayList<Contact>) = ContactFragment().apply {
            data = contacts
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactAdapter = ContactAdapter()
        mBinding!!.rvPhoneBook.adapter = contactAdapter
        contactAdapter.setData(data)
        if (data.size == 0) {
            mBinding!!.tvNoData.visibility = View.VISIBLE
            mBinding!!.rvPhoneBook.visibility = View.GONE
        } else {
            mBinding!!.tvNoData.visibility = View.GONE
            mBinding!!.rvPhoneBook.visibility = View.VISIBLE

        }

    }

}

