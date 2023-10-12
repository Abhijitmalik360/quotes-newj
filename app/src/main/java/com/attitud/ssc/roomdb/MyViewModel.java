package com.attitud.ssc.roomdb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel  extends AndroidViewModel {

    public LiveData<List<FavoriteData>> allData;

    public MyViewModel(@NonNull Application application) {
        super(application);
         MyDatabase database = MyDatabase.getInstance(application.getApplicationContext());
       MyDao  myDao = database.myDao();
        allData = myDao.getAllFavoriteData();

    }

}
