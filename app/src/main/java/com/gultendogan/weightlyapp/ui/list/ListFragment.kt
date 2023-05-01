package com.gultendogan.weightlyapp.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentListBinding
import com.gultendogan.weightlyapp.utils.viewBinding
import java.lang.Math.ceil


class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)

    private val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val weight = ListFragmentArgs.fromBundle(it).currentWeight
            val sonuc: Any = gnl_kalori_kullanici(weight.toDouble(), 170, 23, "kadın", "orta")
            binding.dailyEneryText.setText("Daily energy needs ${sonuc} calories")

            println("GELEN KİLO: ${weight} - SONUC: ${sonuc}")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)

    }
    
    fun gnl_kalori_kullanici(kilo: Double, boy: Int, yas: Int, cinsiyet: String, aktivite: String): Any {
        val gnl_kalori : Double
        if (cinsiyet == "erkek") {
            if (aktivite == "düşük") {
                gnl_kalori = 1.2 * ((10 * kilo) + (6.25 * boy) - (5 * yas) + 5)
            } else if (aktivite == "orta") {
                gnl_kalori = 1.55 * ((10 * kilo) + (6.25 * boy) - (5 * yas) + 5)
            } else if (aktivite == "yüksek") {
                gnl_kalori = 1.9 * ((10 * kilo) + (6.25 * boy) - (5 * yas) + 5)
            } else {
                return "Geçersiz aktivite bilgisi"
            }
        } else if (cinsiyet == "kadın") {
            if (aktivite == "düşük") {
                gnl_kalori = 1.2 * ((10 * kilo) + (6.25 * boy) - (5 * yas) - 161)
            } else if (aktivite == "orta") {
                gnl_kalori = 1.55 * ((10 * kilo) + (6.25 * boy) - (5 * yas) - 161)
            } else if (aktivite == "yüksek") {
                gnl_kalori = 1.9 * ((10 * kilo) + (6.25 * boy) - (5 * yas) - 161)
            } else {
                return "Geçersiz aktivite bilgisi"
            }
        } else {
            return "Geçersiz cinsiyet bilgisi"
        }
        return ceil(gnl_kalori)
    }
}