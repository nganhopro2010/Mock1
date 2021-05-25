package com.hovanngan.mock1.callhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hovanngan.mock1.R
import com.hovanngan.mock1.databinding.FragmentHistoryBinding
import com.hovanngan.mock1.model.History

class CallHistoryFragment : Fragment() {
    private var data = ArrayList<History>()
    private var mBinding: FragmentHistoryBinding? = null
    private lateinit var historyAdapter: CallHistoryAdapter

    companion object {
        fun newInstance(histories: ArrayList<History>) = CallHistoryFragment().apply {
            data = histories
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyAdapter = CallHistoryAdapter()
        mBinding!!.rvHistory.adapter = historyAdapter
        historyAdapter.setData(data)
        if (data.size == 0) {
            mBinding!!.tvNoData.visibility = View.VISIBLE
            mBinding!!.rvHistory.visibility = View.GONE
        } else {
            mBinding!!.tvNoData.visibility = View.GONE
            mBinding!!.rvHistory.visibility = View.VISIBLE

        }

    }

}

