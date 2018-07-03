package com.canadatravel;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.canadatravel.api.ApiServices;
import com.canadatravel.api.CounteryServices;
import com.canadatravel.model.DataModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
     @Test
     public void useAppContext() {
         // Context of the app under test.
         Context appContext = InstrumentationRegistry.getTargetContext();

         assertEquals("com.canadatravel", appContext.getPackageName());
     }
    @Test
   public  void TestApi(){

       ApiServices apiService = CounteryServices.getClient().create(ApiServices.class);
       Call<DataModel> call = apiService.getFeed();
       call.enqueue(new Callback<DataModel>() {
           @Override
           public void onResponse(Call<DataModel> call, Response<DataModel> response) {
               Log.v("response",response.message());
               assertEquals("About Canada", response.body().getTitle());
           }

           @Override
           public void onFailure(Call<DataModel> call, Throwable t) {
                assertFalse("Falied" ,false);
           }
       });
   }
}
