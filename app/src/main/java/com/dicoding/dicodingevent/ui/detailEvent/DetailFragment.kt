package com.dicoding.dicodingevent.ui.detailEvent

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.dicodingevent.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DetailFragment : Fragment() {

    private var eventId: Int? = null
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //set button back
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        // Observe error messages from ViewModel
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Observe loading state from ViewModel
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading) // Show or hide loading based on LiveData
        }

        // Inflate the layout for this fragment
        return binding.root // Menggunakan binding.root di sini
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil ID dari bundle
        arguments?.let {
            eventId = it.getInt("eventId")
        }

        // Load detail event
        eventId?.let { viewModel.loadDetailEvent(it) }

        // Observe LiveData dari ViewModel untuk memperbarui UI
        viewModel.eventData.observe(viewLifecycleOwner) { event ->
            event?.let {
                // Update UI dengan data event
                binding.eventName.text = it.name
                binding.eventDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
                binding.eventSummary.text = it.summary
                binding.eventCategory.text = it.category
                binding.eventOwner.text = it.ownerName
                binding.eventLocation.text = it.cityName
                binding.eventQuota.text = it.quota.toString() + " Peserta"
                binding.eventBeginTime.text = formatDate(it.beginTime)
                binding.eventEndTime.text = formatDate(it.endTime)
                binding.eventQuotaValue.text = (it.quota - it.registrants).toString()

                // Memuat gambar menggunakan Glide
                Glide.with(this)
                    .load(it.mediaCover)
                    .apply(RequestOptions().transform(RoundedCorners(16)))
                    .into(binding.coverImage)

                // Button register click listener
                binding.eventButtonRegister.setOnClickListener {
                    val url = event.link
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
            }
        }
    }

    private fun formatDate(dateString: String): String {
        // Format input sesuai dengan format yang diterima
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        // Format output sesuai keinginan
        val outputFormat = SimpleDateFormat("dd MMMM yyyy 'Pukul' HH.mm", Locale("id", "ID"))

        return try {
            val date: Date = inputFormat.parse(dateString) ?: Date()
            outputFormat.format(date)
        } catch (e: Exception) {
            // Jika terjadi kesalahan dalam parsing, kembalikan string asli
            dateString
        }
    }

    // Handle loading state
    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp() // Navigasi kembali menggunakan NavController
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghindari memory leak
    }
}
