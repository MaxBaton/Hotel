package com.maxbay.presentation.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maxbay.domain.other.Constants
import com.maxbay.domain.room.models.Room
import com.maxbay.hotel.R
import com.maxbay.hotel.databinding.FragmentRoomsBinding
import com.maxbay.hotel.databinding.PhotoItemBinding
import com.maxbay.hotel.databinding.RoomItemBinding
import com.maxbay.hotel.databinding.RoomPeculiarityItemBinding
import com.maxbay.presentation.ui.common.showShortToast
import com.maxbay.presentation.viewmodel.room.RoomViewModel

class RoomsFragment: Fragment() {
    private var binding: FragmentRoomsBinding? = null
    private val roomViewModel: RoomViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding ?: return) {
            val hotelName = arguments?.getString(Constants.Arguments.ARGUMENT_HOTEL_NAME) ?: Constants.Error.EMPTY_STRING
            actionBar.textViewTitle.text = hotelName

            roomViewModel.roomsLiveData.observe(viewLifecycleOwner) { rooms ->
                progressBar.visibility = View.GONE

                if (rooms.isNotEmpty()) {
                    recyclerViewRooms.let {
                        it.adapter = RoomsAdapter(rooms = rooms)
                        it.addItemDecoration(
                            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                        )
                    }
                }else {
                    requireContext().showShortToast(message = getString(R.string.toast_error_load_rooms_info))
                }
            }

            actionBar.imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private inner class RoomsAdapter(
        private val rooms: List<Room>
    ): RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {
        private inner class RoomsViewHolder(
            private val roomItemBinding: RoomItemBinding
        ): RecyclerView.ViewHolder(roomItemBinding.root) {
            fun bind(room: Room) {
                with(roomItemBinding) {
                    textViewRoomName.text = room.name
                    textViewPrice.text = getString(R.string.rooms_fragment_price, room.price)
                    textViewPricePer.text = room.pricePer
                    recyclerViewRoomPeculiarities.adapter = RoomPeculiaritiesAdapter(peculiarities = room.peculiarities)
                    viewPagerPhotos.adapter = RoomPhotosAdapter(photos = room.imageUrls)

                    btnSelectRoom.setOnClickListener {
                        findNavController().navigate(R.id.action_roomsFragment_to_bookingFragment)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
            return RoomsViewHolder(roomItemBinding = RoomItemBinding.inflate(layoutInflater, parent, false))
        }

        override fun getItemCount(): Int {
            return rooms.size
        }

        override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
            val room = rooms[position]
            holder.bind(room)
        }

        private inner class RoomPeculiaritiesAdapter(
            private val peculiarities: List<String>
        ): RecyclerView.Adapter<RoomPeculiaritiesAdapter.RoomPeculiaritiesViewHolder>() {
            private inner class RoomPeculiaritiesViewHolder(
                private val roomPeculiarityItemBinding: RoomPeculiarityItemBinding
            ): RecyclerView.ViewHolder(roomPeculiarityItemBinding.root) {
                fun bind(peculiarity: String) {
                    roomPeculiarityItemBinding.textViewPeculiarity.text = peculiarity
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomPeculiaritiesViewHolder {
                return RoomPeculiaritiesViewHolder(
                    roomPeculiarityItemBinding = RoomPeculiarityItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return peculiarities.size
            }

            override fun onBindViewHolder(holder: RoomPeculiaritiesViewHolder, position: Int) {
                val peculiarity = peculiarities[position]
                holder.bind(peculiarity = peculiarity)
            }
        }

        private inner class RoomPhotosAdapter(
            private val photos: List<String>
        ): RecyclerView.Adapter<RoomPhotosAdapter.RoomPhotosViewHolder>() {
            private inner class RoomPhotosViewHolder(
                private val photoItemBinding: PhotoItemBinding
            ): RecyclerView.ViewHolder(photoItemBinding.root) {
                fun bind(peculiarity: String) {
                    Glide
                        .with(requireContext())
                        .load(peculiarity)
                        .into(photoItemBinding.imageView)
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomPhotosViewHolder {
                return RoomPhotosViewHolder(
                    photoItemBinding = PhotoItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            override fun getItemCount(): Int {
                return photos.size
            }

            override fun onBindViewHolder(holder: RoomPhotosViewHolder, position: Int) {
                val photo = photos[position]
                holder.bind(peculiarity = photo)
            }
        }
    }
}