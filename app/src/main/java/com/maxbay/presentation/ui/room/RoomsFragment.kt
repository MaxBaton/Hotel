package com.maxbay.presentation.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maxbay.hotel.databinding.FragmentRoomsBinding
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
            roomViewModel.roomsLiveData.observe(viewLifecycleOwner) { rooms ->
                val size = rooms.size
                textView.text = "size = $size"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}