package com.maxbay.presentation.ui.booking

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.maxbay.domain.booking.models.BookingDataDomain
import com.maxbay.domain.booking.models.TouristInfo
import com.maxbay.domain.booking.models.UserInfo
import com.gefest.utils.email.IsValidEmail
import com.gefest.utils.phone.GetFirstPhoneKeySymbolIndex
import com.gefest.utils.phone.GetInputSymbol
import com.gefest.utils.phone.GetLastNumberPosition
import com.gefest.utils.phone.IsFillPhoneNumber
import com.gefest.utils.other.Constants
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.BookingDataItemBinding
import com.maxbay.hotel.databinding.BookingHotelItemBinding
import com.maxbay.hotel.databinding.BookingItemBinding
import com.maxbay.hotel.databinding.FragmentBookingBinding
import com.maxbay.hotel.databinding.PriceDataItemBinding
import com.maxbay.hotel.databinding.PriceItemBinding
import com.maxbay.hotel.databinding.RoundButtonItemBinding
import com.maxbay.hotel.databinding.TouristDataItemBinding
import com.maxbay.hotel.databinding.TouristFooterItemBinding
import com.maxbay.hotel.databinding.TouristItemBinding
import com.maxbay.hotel.databinding.UserInfoItemBinding
import com.maxbay.presentation.ui.common.MyDividerItemDecoration
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
            recyclerViewBookingPrice.let {
                it.adapter = groupieAdapter
                it.addItemDecoration(
                    MyDividerItemDecoration(
                        context = requireContext(),
                        orientation = DividerItemDecoration.VERTICAL
                    ), -1
                )
            }

            bookingViewModel.bookingLiveData.observe(viewLifecycleOwner) { bookingListData ->
                progressBar.visibility = View.GONE

                if (bookingListData != null) {
                    populateAdapterBooking(bookingListData = bookingListData)
                }else {
                    errorLayout.layout.visibility = View.VISIBLE
                }
            }

            errorLayout.imageViewReload.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                errorLayout.layout.visibility = View.GONE
                bookingViewModel.getBookingPrice()
            }

            actionBar.imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        groupieAdapter.clear()
    }

    private fun isNotBlankEditTextAndFillErrorIfNeed(
        editText: EditText,
        txtInputLayout: TextInputLayout
    ): Boolean {
        val isBlank = editText.text.toString().isBlank()
        val color = if (isBlank) {
            ContextCompat.getColor(requireContext(), R.color.error_item)
        }else {
            ContextCompat.getColor(requireContext(), R.color.fragment_background)
        }
        txtInputLayout.setBackgroundColor(color)
        return !isBlank
    }

    private fun setEditTextFocusListener(
        editText: EditText,
        txtInputLayout: TextInputLayout
    ) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            val color = if (hasFocus || editText.text.toString().isNotBlank()) {
                ContextCompat.getColor(requireContext(), R.color.fragment_background)
            }else {
                ContextCompat.getColor(requireContext(), R.color.error_item)
            }
            txtInputLayout.setBackgroundColor(color)
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

        groupieAdapter.add(Section().apply {
            add(PayButtonItem(sumPrice = bookingViewModel.getSumPrice(price = bookingPrice)))
        })
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

    private inner class UserInfoItem : BindableItem<UserInfoItemBinding>() {
        private var currPhoneStr: String = Constants.Error.EMPTY_STRING
        private val strBuilder = StringBuilder()
        private var dataItemBinding: UserInfoItemBinding? = null

        override fun bind(viewBinding: UserInfoItemBinding, position: Int) {
            dataItemBinding = viewBinding

            with(viewBinding) {
                bookingViewModel.userInfoLiveData.observe(viewLifecycleOwner) { userInfoWithNull ->
                    userInfoWithNull?.let { userInfo ->
                        etPhoneNumber.setText(userInfo.phoneNumber, TextView.BufferType.EDITABLE)
                        etEmail.setText(userInfo.email, TextView.BufferType.EDITABLE)
                    }
                }

                etPhoneNumber.setOnFocusChangeListener { v, hasFocus ->
                    val isBlankPhone = etPhoneNumber.text.toString().isBlank()
                    if (hasFocus && isBlankPhone) {
                        etPhoneNumber.setText(Constants.Phone.TEMPLATE, TextView.BufferType.EDITABLE)
                    }

                    val isValidPhone = IsFillPhoneNumber.isFill(phoneNumber = etPhoneNumber.text.toString().trim())
                    val background = if (!hasFocus && !isValidPhone) {
                        ContextCompat.getColor(requireContext(), R.color.error_item)
                    }else {
                        ContextCompat.getColor(requireContext(), R.color.fragment_background)
                    }
                    txtInputLayoutPhoneNumber.setBackgroundColor(background)
                }

                etPhoneNumber.addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val currInputStr = s?.toString() ?: Constants.Error.EMPTY_STRING
                        if (
                            currInputStr != Constants.Error.EMPTY_STRING &&
                            currPhoneStr != Constants.Error.EMPTY_STRING &&
                            currInputStr != currPhoneStr
                        ) {
                            if (currInputStr.length > currPhoneStr.length) {
                                // Add
                                if (IsFillPhoneNumber.isFill(phoneNumber = currPhoneStr)) {
                                    etPhoneNumber.setText(currPhoneStr, TextView.BufferType.EDITABLE)
                                }else {
                                    val inputSymbol = GetInputSymbol.get(
                                        oldStr = currPhoneStr,
                                        newStr = currInputStr
                                    )
                                    val firstKeyPhoneSymbolPosition = GetFirstPhoneKeySymbolIndex.get(str = currPhoneStr)
                                    if (firstKeyPhoneSymbolPosition != Constants.Error.ERROR_INT) {
                                        val newStr = replaceSymbolInStrAndGet(
                                            index = firstKeyPhoneSymbolPosition,
                                            strForReplace = inputSymbol
                                        )
                                        if (newStr != Constants.Error.EMPTY_STRING) {
                                            currPhoneStr = newStr
                                            etPhoneNumber.setText(newStr, TextView.BufferType.EDITABLE)
                                        }
                                    }
                                }
                            }else {
                                // Delete
                                val lastNumberIndex = GetLastNumberPosition.get(str = currPhoneStr)
                                if (lastNumberIndex != Constants.Error.ERROR_INT) {
                                    val newStr = replaceSymbolInStrAndGet(
                                        index = lastNumberIndex,
                                        strForReplace = Constants.Phone.KEY_SYMBOL
                                    )

                                    if (newStr != Constants.Error.EMPTY_STRING) {
                                        currPhoneStr = newStr
                                        etPhoneNumber.setText(newStr, TextView.BufferType.EDITABLE)
                                    }
                                }else {
                                    etPhoneNumber.setText(currPhoneStr, TextView.BufferType.EDITABLE)
                                }
                            }
                        }else {
                            currPhoneStr = currInputStr
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        etPhoneNumber.setSelection(etPhoneNumber.text.toString().trim().length)
                    }
                })

                etEmail.setOnFocusChangeListener { v, hasFocus ->
                    val background = if (!hasFocus && !IsValidEmail.isValid(email = etEmail.text.toString().trim())) {
                        ContextCompat.getColor(requireContext(), R.color.error_item)
                    }else {
                        ContextCompat.getColor(requireContext(), R.color.fragment_background)
                    }
                    txtInputLayoutEmail.setBackgroundColor(background)
                }
            }
        }

        override fun getLayout() = R.layout.user_info_item

        override fun initializeViewBinding(view: View) = UserInfoItemBinding.bind(view)

        private fun replaceSymbolInStrAndGet(index: Int, strForReplace: String): String {
            strBuilder.clear()
            strBuilder.append(currPhoneStr)

            return strBuilder.replace(
                index,
                index + 1,
                strForReplace
            ).toString()
        }

        fun checkIsFillAllDataAndGet(): UserInfo? {
            val isAllFillSet = mutableSetOf<Boolean>()
            var phoneNumber = Constants.Error.EMPTY_STRING
            var email = Constants.Error.EMPTY_STRING

            dataItemBinding?.let { itemBinding ->
                with(itemBinding) {
                    email = etEmail.text.toString().trim()
                    phoneNumber = etPhoneNumber.text.toString().trim()

                    val isValidEmail = IsValidEmail.isValid(email = email)
                    val isValidPhone = IsFillPhoneNumber.isFill(phoneNumber = phoneNumber)

                    isAllFillSet.add(isValidEmail)
                    isAllFillSet.add(isValidPhone)

                    val colorError = ContextCompat.getColor(requireContext(), R.color.error_item)
                    if (!isValidEmail) {
                        txtInputLayoutEmail.setBackgroundColor(colorError)
                    }
                    if (!isValidPhone) {
                        txtInputLayoutPhoneNumber.setBackgroundColor(colorError)
                    }
                }
            }

            return if (!isAllFillSet.contains(false)) {
                UserInfo(
                    phoneNumber = phoneNumber,
                    email = email
                )
            }else {
                null
            }
        }
    }

    private inner class TouristItem : BindableItem<TouristItemBinding>() {
        private val groupieAdapter = GroupieAdapter()
        val section = Section()

        override fun bind(viewBinding: TouristItemBinding, position: Int) {
            bookingViewModel.touristsInfoLiveData.observe(viewLifecycleOwner) { touristsWithNull ->
                section.let {
                    it.setFooter(TouristFooter())

                    if (touristsWithNull.isNullOrEmpty()) {
                        it.add(TouristDataItem(touristNumber = section.itemCount))
                    }else {
                        touristsWithNull.forEachIndexed { index, touristInfo ->
                            it.add(TouristDataItem(
                                touristNumber = index + 1,
                                touristInfo = touristInfo
                            ))
                        }
                    }
                }
            }

            with(viewBinding) {
                groupieAdapter.add(section)

                recyclerViewTourists.let {
                    it.adapter = groupieAdapter
                    it.addItemDecoration(
                        MyDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                    )
                }
            }
        }

        override fun getLayout() = R.layout.tourist_item

        override fun initializeViewBinding(view: View) = TouristItemBinding.bind(view)

        inner class TouristDataItem(
            private val touristNumber: Int,
            private val touristInfo: TouristInfo? = null
        ): BindableItem<TouristDataItemBinding>() {
            private var isOpen: Boolean = true
            private var dataItemBinding: TouristDataItemBinding? = null
            override fun bind(viewBinding: TouristDataItemBinding, position: Int) {
                dataItemBinding = viewBinding

                with(viewBinding) {
                    touristInfo?.let { info ->
                        etFirstname.setText(info.firstName, TextView.BufferType.EDITABLE)
                        etSurname.setText(info.surname, TextView.BufferType.EDITABLE)
                        etBirthday.setText(info.birthday, TextView.BufferType.EDITABLE)
                        etCitizenship.setText(info.citizenShip, TextView.BufferType.EDITABLE)
                        etPassportNumber.setText(info.passportNumber, TextView.BufferType.EDITABLE)
                        etPassportValidity.setText(info.passportValidity, TextView.BufferType.EDITABLE)
                    }

                    textViewTouristNumber.text = getTouristNumberStringByNumber()

                    imageViewOpenClose.setOnClickListener {
                        isOpen = !isOpen

                        showHideLayoutDataByOpenState(viewBinding = viewBinding)

                        val imageId = getIdImageByOpenState()
                        Glide
                            .with(requireContext())
                            .load(imageId)
                            .into(imageViewOpenClose)
                    }

                    setEditTextFocusListener(editText = etFirstname, txtInputLayout = txtInputLayoutFirstname)
                    setEditTextFocusListener(editText = etSurname, txtInputLayout = txtInputLayoutSurname)
                    setEditTextFocusListener(editText = etBirthday, txtInputLayout = txtInputLayoutBirthday)
                    setEditTextFocusListener(editText = etCitizenship, txtInputLayout = txtInputLayoutCitizenship)
                    setEditTextFocusListener(editText = etPassportNumber, txtInputLayout = txtInputLayoutPassportNumber)
                    setEditTextFocusListener(editText = etPassportValidity, txtInputLayout = txtInputLayoutPassportValidity)
                }
            }

            override fun getLayout() = R.layout.tourist_data_item

            override fun initializeViewBinding(view: View) = TouristDataItemBinding.bind(view)

            private fun getIdImageByOpenState(): Int {
                return if (isOpen) {
                    R.drawable.icon_tourist_header_up
                }else {
                    R.drawable.icon_tourist_header_down
                }
            }

            private fun showHideLayoutDataByOpenState(viewBinding: TouristDataItemBinding) {
                if (isOpen) {
                    viewBinding.layoutInputData.visibility = View.VISIBLE
                }else {
                    viewBinding.layoutInputData.visibility = View.GONE
                }
            }

            fun checkIsFillAllDataAndGet(): TouristInfo? {
                val isAllFillSet = mutableSetOf<Boolean>()

                dataItemBinding?.let { itemBinding ->
                    with(itemBinding) {
                        isAllFillSet.add(isNotBlankEditTextAndFillErrorIfNeed(editText = etFirstname, txtInputLayout = txtInputLayoutFirstname))
                        isAllFillSet.add(isNotBlankEditTextAndFillErrorIfNeed(editText = etSurname, txtInputLayout = txtInputLayoutSurname))
                        isAllFillSet.add(isNotBlankEditTextAndFillErrorIfNeed(editText = etBirthday, txtInputLayout = txtInputLayoutBirthday))
                        isAllFillSet.add(isNotBlankEditTextAndFillErrorIfNeed(editText = etCitizenship, txtInputLayout = txtInputLayoutCitizenship))
                        isAllFillSet.add(isNotBlankEditTextAndFillErrorIfNeed(editText = etPassportNumber, txtInputLayout = txtInputLayoutPassportNumber))
                        isAllFillSet.add(isNotBlankEditTextAndFillErrorIfNeed(editText = etPassportValidity, txtInputLayout = txtInputLayoutPassportValidity))
                    }
                }

                return if (!isAllFillSet.contains(false)) {
                    with(dataItemBinding ?: return null) {
                        TouristInfo(
                            firstName = etFirstname.text.toString().trim(),
                            surname = etSurname.text.toString().trim(),
                            birthday = etBirthday.text.toString().trim(),
                            citizenShip = etCitizenship.text.toString().trim(),
                            passportNumber = etPassportNumber.text.toString().trim(),
                            passportValidity = etPassportValidity.text.toString().trim()
                        )
                    }
                }else {
                    null
                }
            }

            private fun getTouristNumberStringByNumber(): String {
                val touristNumbers = resources.getStringArray(R.array.tourist_numbers)
                return try {
                    "${touristNumbers[touristNumber - 1]} ${getString(R.string.tourist_item)}"
                }catch (e: IndexOutOfBoundsException) {
                    "$touristNumber ${getString(R.string.tourist_item)}"
                }
            }
        }

        private inner class TouristFooter : BindableItem<TouristFooterItemBinding>() {
            override fun bind(viewBinding: TouristFooterItemBinding, position: Int) {
                with(viewBinding) {
                    imageViewAddNewTourist.setOnClickListener {
                        section.add(TouristDataItem(touristNumber = section.itemCount))
                    }
                }
            }

            override fun getLayout() = R.layout.tourist_footer_item

            override fun initializeViewBinding(view: View) = TouristFooterItemBinding.bind(view)
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
                getString(R.string.booking_fragment_price_sum_charge_title) to getString(R.string.price_in_rubles, bookingViewModel.getSumPrice(price = price))
            )
        }

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
                        if (price.first == getString(R.string.booking_fragment_price_sum_charge_title)) {
                            textViewData.setTextColor(ContextCompat.getColor(requireContext(), R.color.hotel_address_text_view))
                        }
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

    private inner class PayButtonItem(
        private val sumPrice: Int
    ): BindableItem<RoundButtonItemBinding>() {
        override fun bind(viewBinding: RoundButtonItemBinding, position: Int) {
            val text = if (sumPrice != Constants.Error.ERROR_INT) {
                getString(R.string.booking_fragment_btn_pay_text_with_sum, sumPrice)
            }else {
                getString(R.string.booking_fragment_btn_pay_default_text)
            }

            val touristsInfo = mutableListOf<TouristInfo>()

            with(viewBinding) {
                btn.text = text
                btn.setOnClickListener {
                    var isAllFill = true
                    var userInfo: UserInfo? = null
                    touristsInfo.clear()

                    for (i in 0 until groupieAdapter.groupCount) {
                        val section = groupieAdapter.getGroupAtAdapterPosition(i)
                        for (j in 0 until section.itemCount) {
                            val item = section.getItem(j)
                            if (item is TouristItem) {
                                val sectionTourist = item.section
                                for (k in 0 until sectionTourist.itemCount) {
                                    val touristDataItem = sectionTourist.getItem(k)
                                    if (touristDataItem is TouristItem.TouristDataItem) {
                                        val touristInfo = touristDataItem.checkIsFillAllDataAndGet()
                                        if (touristInfo == null) {
                                            isAllFill = false
                                        }else {
                                            touristsInfo.add(touristInfo)
                                        }
                                    }
                                }
                            }else if (item is UserInfoItem) {
                                val userInfoData = item.checkIsFillAllDataAndGet()
                                if (userInfoData == null) {
                                    isAllFill = false
                                }else {
                                    userInfo = userInfoData
                                }
                            }
                        }
                    }

                    if (isAllFill) {
                        userInfo?.let { bookingViewModel.saveUserInfo(userInfo = it) }
                        if (touristsInfo.isNotEmpty()) {
                            bookingViewModel.saveTouristsInfo(tourists = touristsInfo)
                        }
                        findNavController().navigate(R.id.action_bookingFragment_to_paidFragment)
                    }else {
                        requireContext().showShortToast(message = getString(R.string.toast_error_not_fill_all_fields))
                    }
                }
            }
        }

        override fun getLayout() = R.layout.round_button_item

        override fun initializeViewBinding(view: View) = RoundButtonItemBinding.bind(view)
    }
}