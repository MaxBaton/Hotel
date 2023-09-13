package com.maxbay.presentation.ui.paid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gefest.utils.other.GetRandomOrderNumber
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.FragmentPaidBinding

class PaidFragment: Fragment() {
    private var binding: FragmentPaidBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPaidBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {
            actionBar.textViewTitle.text = getString(R.string.action_bar_title_paid)
            textViewMessage.text = getString(R.string.paid_fragment_message, GetRandomOrderNumber.get())

            actionBar.imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSuper.setOnClickListener {
                findNavController().popBackStack(destinationId = R.id.hotelFragment, inclusive = false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}