package com.example.mathengine.Workers;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.example.mathengine.Constants;

import io.reactivex.rxjava3.core.Single;

import static java.util.jar.Pack200.Packer.PROGRESS;

public class MathEngineWorker extends RxWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public MathEngineWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        setProgressAsync( new Data.Builder()
                .putDouble(Constants.PROGRESS_NUM1, getInputData().getDouble(Constants.NUM1,0))
                .putDouble(Constants.PROGRESS_NUM2, getInputData().getDouble(Constants.NUM2,0))
                .putString(Constants.PROGRESS_OP, getInputData().getString(Constants.OPERATOR))
                .build());
    }
    @NonNull
    @Override
    public Single<Result> createWork() {
       Result result;
       try{
           Log.e("SolveEquation ", solveEquation()+"");
            Data outputData = new Data.Builder()
                    .putDouble(Constants.RESULT_DATA, solveEquation())
                    .putDouble(Constants.RESULT_DATA_NUM1, getInputData().getDouble(Constants.NUM1,0))
                    .putDouble(Constants.RESULT_DATA_NUM2, getInputData().getDouble(Constants.NUM2,0))
                    .putString(Constants.RESULT_DATA_OP, getInputData().getString(Constants.OPERATOR))
                    .build();
            result= Result.success(outputData);
        }catch (Exception e){
           result= Result.failure();
        }
        return Single.just(result);
    }

    public double solveEquation(){
        double num1= getInputData().getDouble(Constants.NUM1,0);
        double num2= getInputData().getDouble(Constants.NUM2,0);
        String symbol = getInputData().getString(Constants.OPERATOR);

        switch (symbol){
            case "+": return num1+num2;
            case "-": return num1-num2;
            case "*": return num1*num2;
            case "/": return num1/num2;
            default:return 0;
        }
    }
}
