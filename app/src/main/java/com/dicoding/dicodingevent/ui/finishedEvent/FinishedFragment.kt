package com.dicoding.dicodingevent.ui.finishedEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FinishedAdapter
    private lateinit var viewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

        // Inisialisasi adapter dengan listener klik untuk navigasi ke halaman detail
        adapter = FinishedAdapter { eventId ->
            val bundle = Bundle().apply {
                putInt("eventId", eventId)
            }
            findNavController().navigate(R.id.action_navigation_finished_event_to_detailFragment, bundle)
        }

        // Set up RecyclerView
        setupRecyclerView()

        // Memanggil loadEvents() saat fragment pertama kali dibuat
        viewModel.loadEvents()

        // Observe events from ViewModel
        viewModel.events.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events) // Update the adapter with new data
        }

        // Observe error messages from ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Observe loading state from ViewModel
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading) // Show or hide loading based on the LiveData
        }

        // Tambahkan listener pada SearchView
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // Tidak perlu melakukan apa-apa saat submit
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // Jika query kosong, tampilkan semua data
                    viewModel.loadEvents()
                } else {
                    // Panggil fungsi searchEvents di ViewModel
                    viewModel.searchEvents(newText)
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvListFinished.layoutManager = LinearLayoutManager(context)
        binding.rvListFinished.adapter = adapter
    }

    // Handle loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFinished.visibility = View.VISIBLE
        } else {
            binding.progressBarFinished.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

