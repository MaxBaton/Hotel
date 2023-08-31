package com.maxbay.presentation.ui.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.FragmentHotelBinding
import com.maxbay.hotel.databinding.HotelPhotoItemBinding
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
            actionBar.textViewTitle.text = getString(R.string.action_bar_title_hotel)

            hotelViewModel.hotelLiveData.observe(viewLifecycleOwner) { hotelNull ->
                progressBar.visibility = View.GONE

                if (hotelNull != null) {
                    showAllViews()

                    hotelNull.let {
                        // Photos
                        val hotelPhotoAdapter = HotelPhotoAdapter(imageUrls = it.imageUrls)
                        viewPager.adapter = hotelPhotoAdapter
                        // Rating
                        textViewRating.text = it.rating.toString()
                        textViewRatingName.text = it.ratingName
                        textViewHotelName.text = it.name
                        textViewHotelAddress.text = it.address
                    }
                }else {
                    requireContext().showShortToast(message = getString(R.string.toast_error_load_hotel_info))
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

    private fun showAllViews() {
        with(binding ?: return) {
            starLinearLayout.visibility = View.VISIBLE
        }
    }

    private inner class HotelPhotoAdapter(
        private val imageUrls: List<String>
    ): RecyclerView.Adapter<HotelPhotoAdapter.HotelPhotoViewHolder>() {
        private inner class HotelPhotoViewHolder(
            private val hotelPhotoItemBinding: HotelPhotoItemBinding
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
                hotelPhotoItemBinding = HotelPhotoItemBinding.inflate(layoutInflater, parent, false)
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