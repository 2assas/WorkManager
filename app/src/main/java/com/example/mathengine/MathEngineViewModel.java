package com.example.mathengine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.mathengine.Workers.MathEngineWorker;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MathEngineViewModel extends AndroidViewModel {
    public MathEngineViewModel(@NonNull Application application) {
        super(application);
    }
    WorkManager workManager = WorkManager.getInstance(getApplication().getApplicationContext());
    LiveData<List<WorkInfo>> outputWorkInfo =workManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT);
//    LiveData<List<WorkInfo>> pendingWorkInfo =workManager.getWorkInfosByTagLiveData(Constants.PENDING);

    public void setupWorkRequest(double num1, double num2, String operator, long timeDelay ){
        Data.Builder data = new Data.Builder();
        data.putDouble(Constants.NUM1, num1);
        data.putDouble(Constants.NUM2, num2);
        data.putString(Constants.OPERATOR, operator);
        OneTimeWorkRequest solveWorkRequest =
                new OneTimeWorkRequest.Builder(MathEngineWorker.class)
                        .setInitialDelay(timeDelay, TimeUnit.SECONDS)
                        .addTag(Constants.TAG_OUTPUT)
                        .setInputData(data.build())
                        .build();
        workManager.enqueue(solveWorkRequest);

    }
    public void clearAllWorkers(){
        workManager.pruneWork();
    }

}
