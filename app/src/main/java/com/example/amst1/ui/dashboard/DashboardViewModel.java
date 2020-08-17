package com.example.amst1.ui.dashboard;

import android.widget.TableLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private TableLayout tabla;
    private String query;
    private String server = "https://flightsregister.000webhostapp.com/queriTA5.php";

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}