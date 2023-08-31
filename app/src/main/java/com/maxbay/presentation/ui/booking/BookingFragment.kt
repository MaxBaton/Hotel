package com.maxbay.presentation.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.FragmentBookingBinding
import com.maxbay.presentation.ui.common.showShortToast
import com.maxbay.presentation.viewmodel.booking.BookingViewModel

class BookingFragment: Fragment() {
    private var binding: FragmentBookingBinding? = null
    private val bookingViewModel: BookingViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding ?: return) {
            bookingViewModel.bookingLiveData.observe(viewLifecycleOwner) { bookingNull ->
                progressBar.visibility = View.GONE

                if (bookingNull != null) {
                    textViewBooking.text = "hotel name = ${bookingNull.hotelName}"
                }else {
                    requireContext().showShortToast(message = getString(R.string.toast_error_load_booking_info))
                }
            }

            bookingViewModel.priceLiveData.observe(viewLifecycleOwner) { priceNull ->
                if (priceNull != null) {
                    textViewPrice.text = "tour price = ${priceNull.tourPrice}"
                }else {
                    requireContext().showShortToast(message = getString(R.string.toast_error_load_price_info))
                }
            }

            btnNext.setOnClickListener {
                findNavController().navigate(R.id.action_bookingFragment_to_paidFragment)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}