package com.maxbay.presentation.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.BookingDataItemBinding
import com.maxbay.hotel.databinding.BookingHotelItemBinding
import com.maxbay.hotel.databinding.BookingItemBinding
import com.maxbay.hotel.databinding.FragmentBookingBinding
import com.maxbay.hotel.databinding.PriceDataItemBinding
import com.maxbay.hotel.databinding.PriceItemBinding
import com.maxbay.hotel.databinding.TouristDataItemBinding
import com.maxbay.hotel.databinding.TouristHeaderItemBinding
import com.maxbay.hotel.databinding.TouristItemBinding
import com.maxbay.hotel.databinding.UserInfoItemBinding
import com.maxbay.presentation.ui.common.showShortToast
import com.maxbay.presentation.viewmodel.booking.BookingViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

class BookingFragment: Fragment() {
    private var binding: FragmentBookingBinding? = null
    private val bookingViewModel: BookingViewModel by activityViewModels()
    private val groupieAdapter = GroupieAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding ?: return) {
            actionBar.textViewTitle.text = getString(R.string.action_bar_title_booking)
            recyclerViewBookingPrice.adapter = groupieAdapter

            bookingViewModel.bookingLiveData.observe(viewLifecycleOwner) { bookingListData ->
                progressBar.visibility = View.GONE

                if (bookingListData != null) {
                    showAllViews()
                    populateAdapterBooking(bookingListData = bookingListData)
                }else {
                    requireContext().showShortToast(message = getString(R.string.toast_error_load_booking_info))
                }
            }

            actionBar.imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnPay.setOnClickListener {
                findNavController().navigate(R.id.action_bookingFragment_to_paidFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        groupieAdapter.clear()
    }

    private fun showAllViews() {
        with(binding ?: return) {
            btnPay.visibility = View.VISIBLE
        }
    }

    private fun populateAdapterBooking(bookingListData: List<BookingDataDomain>) {
        var bookingPrice: BookingDataDomain.Price? = null
        bookingListData.forEach { bookingDomainData ->
            when(bookingDomainData) {
                is BookingDataDomain.Hotel -> {
                    groupieAdapter.add(
                        Section().apply {
                            add(BookingHotelItem(hotel = bookingDomainData))
                        }
                    )
                }
                is BookingDataDomain.Booking -> {
                    groupieAdapter.add(
                        Section().apply {
                            add(BookingItem(booking = bookingDomainData))
                        }
                    )
                }
                is BookingDataDomain.Price -> {
                    bookingPrice = bookingDomainData
                }
            }
        }

        groupieAdapter.add(Section().apply { add(UserInfoItem()) })

        groupieAdapter.add(Section().apply { add(TouristItem()) })

        bookingPrice?.let {
            groupieAdapter.add(
                Section().apply{
                    add(BookingPriceItem(price = it))
                }
            )
        }
    }

    private inner class BookingHotelItem(
        private val hotel: BookingDataDomain.Hotel
    ): BindableItem<BookingHotelItemBinding>() {
        override fun bind(viewBinding: BookingHotelItemBinding, position: Int) {
            showAllItems(binding = viewBinding)

            with(viewBinding) {
                hotel.let {
                    textViewHotelName.text = it.hotelName
                    textViewRating.text = it.hotRating.toString()
                    textViewRatingName.text = it.ratingName
                    textViewHotelAddress.text = it.hotelAddress
                }
            }
        }

        override fun getLayout() = R.layout.booking_hotel_item

        override fun initializeViewBinding(view: View) = BookingHotelItemBinding.bind(view)

        private fun showAllItems(binding: BookingHotelItemBinding) {
            binding.starLinearLayout.visibility = View.VISIBLE
        }
    }

    private inner class BookingItem(
        private val booking: BookingDataDomain.Booking
    ): BindableItem<BookingItemBinding>() {
        override fun bind(viewBinding: BookingItemBinding, position: Int) {
            with(viewBinding) {
                recyclerViewBooking.adapter = BookingItemsAdapter(listData = getListData())
            }
        }

        override fun getLayout() = R.layout.booking_item

        override fun initializeViewBinding(view: View) = BookingItemBinding.bind(view)

        private fun getListData(): List<Pair<String, String>> {
            return listOf(
                getString(R.string.booking_fragment_departure_title) to booking.departure,
                getString(R.string.booking_fragment_arrival_country_title) to booking.arrivalCountry,
                getString(R.string.booking_fragment_arrival_tour_dates_title) to "${booking.tourDateStart} - ${booking.tourDateStop}",
                getString(R.string.booking_fragment_arrival_number_of_nights_title) to getString(R.string.booking_fragment_arrival_number_of_nights_data, booking.numberOfNights),
                getString(R.string.booking_fragment_arrival_hotel_title) to booking.hotelName,
                getString(R.string.booking_fragment_arrival_room_title) to booking.room,
                getString(R.string.booking_fragment_arrival_nutrition_title) to booking.nutrition
            )
        }

        private inner class BookingItemsAdapter(
            private val listData: List<Pair<String, String>>
        ): RecyclerView.Adapter<BookingItemsAdapter.BookingItemsViewHolder>() {
            private inner class BookingItemsViewHolder(
                private val bookingDataItemBinding: BookingDataItemBinding
            ): RecyclerView.ViewHolder(bookingDataItemBinding.root) {
                fun bind(bookingData: Pair<String, String>) {
                    with(bookingDataItemBinding) {
                        textViewTitle.text = bookingData.first
                        textViewData.text = bookingData.second
                    }
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingItemsViewHolder {
                return BookingItemsViewHolder(
                    bookingDataItemBinding = BookingDataItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return listData.size
            }

            override fun onBindViewHolder(holder: BookingItemsViewHolder, position: Int) {
                val data = listData[position]
                holder.bind(data)
            }
        }
    }

    private inner class UserInfoItem(): BindableItem<UserInfoItemBinding>() {
        override fun bind(viewBinding: UserInfoItemBinding, position: Int) {
            with(viewBinding) {

            }
        }

        override fun getLayout() = R.layout.user_info_item

        override fun initializeViewBinding(view: View) = UserInfoItemBinding.bind(view)
    }

    private inner class TouristItem(): BindableItem<TouristItemBinding>() {
        private val groupieAdapter = GroupieAdapter()
        private val section = Section()

        override fun bind(viewBinding: TouristItemBinding, position: Int) {
            with(viewBinding) {
                section.let {
                    it.setHideWhenEmpty(true)
                    it.setHeader(TouristHeader(touristNumber = 1))
                    it.add(TouristDataItem())
                }
                groupieAdapter.add(section)

                recyclerViewTourists.let {
                    it.adapter = groupieAdapter
                    it.addItemDecoration(
                        DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                    )
                }
            }
        }

        override fun getLayout() = R.layout.tourist_item

        override fun initializeViewBinding(view: View) = TouristItemBinding.bind(view)

        private inner class TouristHeader(
            private val touristNumber: Int
        ): BindableItem<TouristHeaderItemBinding>() {
            override fun bind(viewBinding: TouristHeaderItemBinding, position: Int) {
                with(viewBinding) {
                    textViewTouristNumber.text = "$touristNumber Турист"
                }
            }

            override fun getLayout() = R.layout.tourist_header_item

            override fun initializeViewBinding(view: View) = TouristHeaderItemBinding.bind(view)
        }

        private inner class TouristDataItem(): BindableItem<TouristDataItemBinding>() {
            override fun bind(viewBinding: TouristDataItemBinding, position: Int) {
                with(viewBinding) {

                }
            }

            override fun getLayout() = R.layout.tourist_data_item

            override fun initializeViewBinding(view: View) = TouristDataItemBinding.bind(view)
        }
    }

    private inner class BookingPriceItem(
        private val price: BookingDataDomain.Price
    ): BindableItem<PriceItemBinding>() {
        override fun bind(viewBinding: PriceItemBinding, position: Int) {
            with(viewBinding) {
                recyclerViewBookingPrice.adapter = PriceItemsAdapter(listData = getListData())
            }
        }

        override fun getLayout() = R.layout.price_item

        override fun initializeViewBinding(view: View) = PriceItemBinding.bind(view)

        private fun getListData(): List<Pair<String, String>> {
            return listOf(
                getString(R.string.booking_fragment_price_tour_price_title) to getString(R.string.price_in_rubles, price.tourPrice),
                getString(R.string.booking_fragment_price_fuel_charge_title) to getString(R.string.price_in_rubles, price.fuelCharge),
                getString(R.string.booking_fragment_price_service_charge_title) to getString(R.string.price_in_rubles, price.serviceCharge),
                getString(R.string.booking_fragment_price_sum_charge_title) to getString(R.string.price_in_rubles, getSumPrice())
            )
        }

        private fun getSumPrice() = price.tourPrice + price.fuelCharge + price.serviceCharge

        private inner class PriceItemsAdapter(
            private val listData: List<Pair<String, String>>
        ): RecyclerView.Adapter<PriceItemsAdapter.PriceItemsViewHolder>() {
            private inner class PriceItemsViewHolder(
                private val priceDataItemBinding: PriceDataItemBinding
            ): RecyclerView.ViewHolder(priceDataItemBinding.root) {
                fun bind(price: Pair<String, String>) {
                    with(priceDataItemBinding) {
                        textViewTitle.text = price.first
                        textViewData.text = price.second
                    }
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceItemsViewHolder {
                return PriceItemsViewHolder(
                    priceDataItemBinding = PriceDataItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return listData.size
            }

            override fun onBindViewHolder(holder: PriceItemsViewHolder, position: Int) {
                val data = listData[position]
                holder.bind(data)
            }
        }
    }
}