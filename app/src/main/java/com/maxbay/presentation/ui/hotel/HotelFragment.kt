package com.maxbay.presentation.ui.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxbay.domain.hotel.models.AboutTheHotelAction
import com.maxbay.domain.hotel.models.Hotel
import com.gefest.utils.other.Constants
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.AboutHotelActionItemBinding
import com.maxbay.hotel.databinding.FragmentHotelBinding
import com.maxbay.hotel.databinding.HotelFragmentCommonDataItemBinding
import com.maxbay.hotel.databinding.HotelFragmentDetailDataItemBinding
import com.maxbay.hotel.databinding.PeculiarityItemBinding
import com.maxbay.hotel.databinding.PhotoItemBinding
import com.maxbay.hotel.databinding.RoundButtonItemBinding
import com.maxbay.presentation.ui.common.MyDividerItemDecoration
import com.maxbay.presentation.viewmodel.hotel.HotelViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

class HotelFragment: Fragment() {
    private var binding: FragmentHotelBinding? = null
    private val hotelViewModel: HotelViewModel by activityViewModels()
    private val groupieAdapter = GroupieAdapter()
    private val actions by lazy {
        initializeActions()
    }
    private var hotelName: String? = null

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
            actionBar.textViewTitle.text = getString(R.string.action_bar_title_hotel)

            hotelViewModel.hotelLiveData.observe(viewLifecycleOwner) { hotelWithNull ->
                progressBar.visibility = View.GONE

                if (hotelWithNull != null) {
                    populateAdapter(hotel = hotelWithNull)
                    recyclerView.let {
                        it.adapter = groupieAdapter
                        it.addItemDecoration(
                            MyDividerItemDecoration(
                                context = requireContext(),
                                orientation = DividerItemDecoration.VERTICAL
                            )
                        )
                    }

                    hotelName = hotelWithNull.name
                }else {
                    errorLayout.layout.visibility = View.VISIBLE
                }
            }

            errorLayout.imageViewReload.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                errorLayout.layout.visibility = View.GONE
                hotelViewModel.getHotelInfo()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        groupieAdapter.clear()
    }

    private fun populateAdapter(hotel: Hotel) {
        groupieAdapter.add(
            Section().apply {
                add(HotelCommonDataItem(hotel = hotel))
            }
        )
        groupieAdapter.add(
            Section().apply {
                add(HotelDetailDataItem(hotel = hotel))
            }
        )
        groupieAdapter.add(
            Section().apply {
                add(SelectRoomItem())
            }
        )
    }

    private fun initializeActions(): List<AboutTheHotelAction> {
        return listOf(
            AboutTheHotelAction(
                icon = R.drawable.icon_conveniences,
                title = getString(R.string.hotel_fragment_action_conveniences_title),
                subtitle = getString(R.string.hotel_fragment_action_subtitle)
            ),
            AboutTheHotelAction(
                icon = R.drawable.icon_what_include,
                title = getString(R.string.hotel_fragment_action_what_includes_title),
                subtitle = getString(R.string.hotel_fragment_action_subtitle)
            ),
            AboutTheHotelAction(
                icon = R.drawable.icon_what_not_include,
                title = getString(R.string.hotel_fragment_action_what_not_includes_title),
                subtitle = getString(R.string.hotel_fragment_action_subtitle)
            )
        )
    }

    private inner class HotelCommonDataItem(
        private val hotel: Hotel
    ): BindableItem<HotelFragmentCommonDataItemBinding>() {
        override fun bind(viewBinding: HotelFragmentCommonDataItemBinding, position: Int) {
            with(viewBinding) {
                showAllViews(binding = viewBinding)

                hotel.let {
                    // Photos
                    val hotelPhotoAdapter = HotelPhotoAdapter(imageUrls = it.imageUrls)
                    viewPager.adapter = hotelPhotoAdapter
                    viewPagerIndicator.setViewPager(viewPager)
                    hotelPhotoAdapter.registerAdapterDataObserver(viewPagerIndicator.adapterDataObserver)
                    // Rating
                    textViewRating.text = it.rating.toString()
                    textViewRatingName.text = it.ratingName
                    textViewHotelName.text = it.name
                    textViewHotelAddress.text = it.address
                    textViewMinPrice.text = getString(R.string.hotel_fragment_min_price, it.minimalPrice)
                    textViewPriceForIt.text = it.priceForIt.lowercase()
                }
            }
        }

        override fun getLayout() = R.layout.hotel_fragment_common_data_item

        override fun initializeViewBinding(view: View) = HotelFragmentCommonDataItemBinding.bind(view)

        private fun showAllViews(binding: HotelFragmentCommonDataItemBinding) {
            binding.starLinearLayout.visibility = View.VISIBLE
        }

        private inner class HotelPhotoAdapter(
            private val imageUrls: List<String>
        ): RecyclerView.Adapter<HotelPhotoAdapter.HotelPhotoViewHolder>() {
            private inner class HotelPhotoViewHolder(
                private val hotelPhotoItemBinding: PhotoItemBinding
            ): RecyclerView.ViewHolder(hotelPhotoItemBinding.root) {
                fun bind(imageUrl: String) {
                    Glide
                        .with(requireContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.icon_update)
                        .into(hotelPhotoItemBinding.imageView)
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelPhotoViewHolder {
                return HotelPhotoViewHolder(
                    hotelPhotoItemBinding = PhotoItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return imageUrls.size
            }

            override fun onBindViewHolder(holder: HotelPhotoViewHolder, position: Int) {
                val imageUrl = imageUrls[position]
                holder.bind(imageUrl = imageUrl)
            }
        }
    }

    private inner class HotelDetailDataItem(
        private val hotel: Hotel
    ): BindableItem<HotelFragmentDetailDataItemBinding>() {

        override fun bind(viewBinding: HotelFragmentDetailDataItemBinding, position: Int) {
            with(viewBinding) {
                hotel.let {
                    textViewDescription.text = it.aboutTheHotel.description
                    // Peculiarities
                    recyclerViewPeculiarities.adapter = PeculiaritiesAdapter(
                        peculiarities = it.aboutTheHotel.peculiarities
                    )
                    // Actions
                    recyclerViewActions.adapter = AboutHotelActionsAdapter(actions = actions)
                }
            }
        }

        override fun getLayout() = R.layout.hotel_fragment_detail_data_item

        override fun initializeViewBinding(view: View) = HotelFragmentDetailDataItemBinding.bind(view)

        private inner class PeculiaritiesAdapter(
            private val peculiarities: List<String>
        ): RecyclerView.Adapter<PeculiaritiesAdapter.PeculiaritiesViewHolder>() {
            private inner class PeculiaritiesViewHolder(
                private val peculiarityItemBinding: PeculiarityItemBinding
            ): RecyclerView.ViewHolder(peculiarityItemBinding.root) {
                fun bind(peculiarity: String) {
                    peculiarityItemBinding.textViewPeculiarity.text = peculiarity
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeculiaritiesViewHolder {
                return PeculiaritiesViewHolder(
                    peculiarityItemBinding = PeculiarityItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return peculiarities.size
            }

            override fun onBindViewHolder(holder: PeculiaritiesViewHolder, position: Int) {
                val peculiarity = peculiarities[position]
                holder.bind(peculiarity = peculiarity)
            }
        }

        private inner class AboutHotelActionsAdapter(
            private val actions: List<AboutTheHotelAction>
        ): RecyclerView.Adapter<AboutHotelActionsAdapter.AboutHotelActionsViewHolder>() {
            private inner class AboutHotelActionsViewHolder(
                private val aboutHotelActionItemBinding: AboutHotelActionItemBinding
            ): RecyclerView.ViewHolder(aboutHotelActionItemBinding.root) {
                fun bind(action: AboutTheHotelAction) {
                    with(aboutHotelActionItemBinding) {
                        Glide
                            .with(requireContext())
                            .load(action.icon)
                            .into(imageViewAction)
                        textViewTitle.text = action.title
                        textViewSubtitle.text = action.subtitle
                    }
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutHotelActionsViewHolder {
                return AboutHotelActionsViewHolder(
                    aboutHotelActionItemBinding = AboutHotelActionItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return actions.size
            }

            override fun onBindViewHolder(holder: AboutHotelActionsViewHolder, position: Int) {
                val action = actions[position]
                holder.bind(action = action)
            }
        }
    }

    private inner class SelectRoomItem: BindableItem<RoundButtonItemBinding>() {
        override fun bind(viewBinding: RoundButtonItemBinding, position: Int) {
            with(viewBinding) {
                btn.text = getString(R.string.hotel_fragment_select_room_button)
                btn.setOnClickListener {
                    val name = hotelName ?: Constants.Error.EMPTY_STRING
                    val bundle = bundleOf(Constants.Arguments.ARGUMENT_HOTEL_NAME to name)
                    findNavController().navigate(R.id.action_hotelFragment_to_roomsFragment, bundle)
                }
            }
        }


        override fun getLayout() = R.layout.round_button_item

        override fun initializeViewBinding(view: View) = RoundButtonItemBinding.bind(view)
    }
}