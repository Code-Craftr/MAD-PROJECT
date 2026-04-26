package com.example.qmanageapplication.network;

import com.example.qmanageapplication.network.responses.OutletResponse;
import com.example.qmanageapplication.network.responses.MenuResponse;
import com.example.qmanageapplication.models.Order;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("outlets")
    Call<OutletResponse> getAllOutlets();

    @GET("outlets/{id}/menu")
    Call<MenuResponse> getOutletMenu(@Path("id") int outletId);
}
