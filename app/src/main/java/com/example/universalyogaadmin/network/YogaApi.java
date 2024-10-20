package com.example.universalyogaadmin.network;

import com.example.universalyogaadmin.model.api.YogaCourseVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface YogaApi {
    @POST("/yoga/courses")
    Call<Void> sendYogaCourse(@Body YogaCourseVO yogaCourse);
}
