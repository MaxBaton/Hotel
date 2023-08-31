package com.maxbay.presentation.ui.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.FragmentHotelBinding
import com.maxbay.presentation.ui.common.showShortToast
import com.maxbay.presentation.viewmodel.hotel.HotelViewModel

class HotelFragment: Fragment() {
    private var binding: FragmentHotelBinding? = null
    private val hotelViewModel: HotelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding ?: return) {
            hotelViewModel.hotelLiveData.observe(viewLifecycleOwner) { hotelNull ->
                progressBar.visibility = View.GONE

                if (hotelNull != null) {
                    textView.text = "${hotelNull.name}\n${hotelNull.address}"
                }else {
                    requireContext().showShortToast(message = getString(R.string.toast_error_load__hotel_info))
                }
            }

            btnNext.setOnClickListener {
                findNavController().navigate(R.id.action_hotelFragment_to_roomsFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}