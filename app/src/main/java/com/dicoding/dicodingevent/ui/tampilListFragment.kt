package com.dicoding.dicodingevent.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.FragmentTampilListBinding
import com.dicoding.dicodingevent.services.data.FavoriteDao
import com.dicoding.dicodingevent.services.data.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class tampilListFragment : Fragment() {

    private var _binding: FragmentTampilListBinding? = null
    private val binding get()= _binding!!
    private lateinit var fAvoriteDAO: FavoriteDao
    private lateinit var dB: FavoriteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTampilListBinding.inflate(inflater,container,false)
        binding.fav.layoutManager = LinearLayoutManager(requireContext())
        dB = FavoriteDatabase.getDatabase(requireContext())
        fAvoriteDAO = dB.favoriteDao()
        CoroutineScope(Dispatchers.IO).launch {
            val data = fAvoriteDAO.getAll()
            withContext(Dispatchers.Main){
                val aDapter = AdapterFav (data)
                binding.fav.adapter = aDapter
            }
        }

        return binding.root
    }
}