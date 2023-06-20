package com.gultendogan.weightlyapp.ui.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.api.FoodAPI
import com.gultendogan.weightlyapp.databinding.FragmentListBinding
import com.gultendogan.weightlyapp.model.FoodModel
import com.gultendogan.weightlyapp.utils.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Math.ceil
import java.util.Collections


class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListViewModel by viewModels()
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var foodModels: ArrayList<FoodModel>? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var sonucc : Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val weight = ListFragmentArgs.fromBundle(it).currentWeight
            sonucc = gnl_kalori_kullanici(weight.toDouble(), 170, 23, "kadın", "orta")
            binding.dailyEneryText.setText("Daily energy needs ${sonucc} calories")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(FoodAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(foodList: List<FoodModel>) {
        foodModels = ArrayList(foodList)
        Collections.shuffle(foodModels)
        val dailyCalorie = (sonucc - 764) / 2
        var totalCalorie = 0
        var mealCount = 0
        var afternoonMealString = ""
        var eveningMealString = ""

        val sharedPrefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val savedAfternoonMealString = sharedPrefs.getString("afternoonMealString", "")
        val savedEveningMealString = sharedPrefs.getString("eveningMealString", "")

        if (savedAfternoonMealString.isNullOrEmpty() || isTimeToUpdate(sharedPrefs, "afternoonMealUpdateTime")) {
            for (foodModel in foodModels!!) {
                if (totalCalorie + foodModel.calorie.toInt() <= dailyCalorie) {
                    totalCalorie += foodModel.calorie.toInt()
                    mealCount++
                    println("• ${foodModel.food} (${foodModel.gr} gr)\n")
                    afternoonMealString += "• ${foodModel.food} (${foodModel.gr} gr)\n"
                }
                if (totalCalorie >= dailyCalorie) {
                    break
                }
            }
            binding.afternoonMeal.setText(afternoonMealString)

            editor.putString("afternoonMealString", afternoonMealString)
            editor.putLong("afternoonMealUpdateTime", System.currentTimeMillis())
            editor.apply()
        } else {
            afternoonMealString = savedAfternoonMealString
            binding.afternoonMeal.setText(afternoonMealString)
        }

        Collections.shuffle(foodModels)
        totalCalorie = 0
        mealCount = 0

        if (savedEveningMealString.isNullOrEmpty() || isTimeToUpdate(sharedPrefs, "eveningMealUpdateTime")) {
            for (foodModel in foodModels!!) {
                if (totalCalorie + foodModel.calorie.toInt() <= dailyCalorie) {
                    totalCalorie += foodModel.calorie.toInt()
                    mealCount++
                    println("• ${foodModel.food} (${foodModel.gr} gr)\n")
                    eveningMealString += "• ${foodModel.food} (${foodModel.gr} gr)\n"
                }
                if (totalCalorie >= dailyCalorie) {
                    break
                }
            }
            binding.eveningMeal.setText(eveningMealString)

            editor.putString("eveningMealString", eveningMealString)
            editor.putLong("eveningMealUpdateTime", System.currentTimeMillis())
            editor.apply()
        } else {
            eveningMealString = savedEveningMealString.toString()
            binding.eveningMeal.setText(eveningMealString)
        }
    }

    private fun isTimeToUpdate(sharedPrefs: SharedPreferences, key: String): Boolean {
        val lastUpdateTime = sharedPrefs.getLong(key, 0)
        val currentTime = System.currentTimeMillis()
        //val oneHourInMillis = 60 * 60 * 1000 // 1 saat
        //val oneHourInMillis = 6 * 1000 // 1 dakika
        val oneHourInMillis = 24 * 60 * 60 * 1000 // 24 saat

        return currentTime - lastUpdateTime >= oneHourInMillis
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    fun gnl_kalori_kullanici(kilo: Double, boy: Int, yas: Int, cinsiyet: String, aktivite: String): Double {
        val gnl_kalori : Double
        if (cinsiyet == "erkek") {
            if (aktivite == "düşük") {
                gnl_kalori = 1.2 * ((10 * kilo) + (6.25 * boy) - (5 * yas) + 5)
            } else if (aktivite == "orta") {
                gnl_kalori = 1.55 * ((10 * kilo) + (6.25 * boy) - (5 * yas) + 5)
            } else if (aktivite == "yüksek") {
                gnl_kalori = 1.9 * ((10 * kilo) + (6.25 * boy) - (5 * yas) + 5)
            } else {
                return 0.0
            }
        } else if (cinsiyet == "kadın") {
            if (aktivite == "düşük") {
                gnl_kalori = 1.2 * ((10 * kilo) + (6.25 * boy) - (5 * yas) - 161)
            } else if (aktivite == "orta") {
                gnl_kalori = 1.55 * ((10 * kilo) + (6.25 * boy) - (5 * yas) - 161)
            } else if (aktivite == "yüksek") {
                gnl_kalori = 1.9 * ((10 * kilo) + (6.25 * boy) - (5 * yas) - 161)
            } else {
                return 0.0
            }
        } else {
            return 0.0
        }
        return ceil(gnl_kalori)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}