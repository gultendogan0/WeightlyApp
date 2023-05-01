package com.gultendogan.weightlyapp.api

import com.gultendogan.weightlyapp.model.FoodModel
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

//https://raw.githubusercontent.com/gulten27/food/main/food.json
interface FoodAPI {
    @GET("gulten27/food/main/food.json")
    fun getData() : Observable<List<FoodModel>>
}