package com.example.mathengine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.WorkInfo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mathengine.adapters.CompletedAdapter;
import com.example.mathengine.adapters.InProgressAdapter;
import com.example.mathengine.databinding.ActivityMainBinding;
import com.example.mathengine.pojo.EquationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MathEngineViewModel mathEngineViewModel;
    String num1="";
    String num2="";
    String operator="";
    ArrayList<EquationModel> inProgressEquationList= new ArrayList<>();
    ArrayList<EquationModel> completedEquationList= new ArrayList<>();
    InProgressAdapter inProgressAdapter;
    CompletedAdapter completedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mathEngineViewModel = new ViewModelProvider(this).get(MathEngineViewModel.class);
        mathEngineViewModel.outputWorkInfo.observe(this, observeOutput());
        buttonClicks();
        calculate();
        clearWorkers();
        setupRecyclerViews(inProgressEquationList, completedEquationList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mathEngineViewModel.outputWorkInfo.observe(this, observeOutput());
        setupRecyclerViews(inProgressEquationList, completedEquationList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mathEngineViewModel.outputWorkInfo.observe(this, observeOutput());
        setupRecyclerViews(inProgressEquationList, completedEquationList);
    }

    @SuppressLint("SetTextI18n")
    private void buttonClicks(){
        binding.calculatorLayout.button0.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"0");});
        binding.calculatorLayout.button1.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"1");});
        binding.calculatorLayout.button2.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"2");});
        binding.calculatorLayout.button3.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"3");});
        binding.calculatorLayout.button4.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"4");});
        binding.calculatorLayout.button5.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"5");});
        binding.calculatorLayout.button6.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"6");});
        binding.calculatorLayout.button7.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"7");});
        binding.calculatorLayout.button8.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"8");});
        binding.calculatorLayout.button9.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"9");});
        binding.calculatorLayout.buttonDot.setOnClickListener(v -> {binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+".");});

        binding.calculatorLayout.buttonAdd.setOnClickListener(v -> {
           if(singleOperatorCheck()){
               binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"+");
           }else{
              showAlertDialog("Attention!","Only one operator allowed");
           }
        });
        binding.calculatorLayout.buttonMinus.setOnClickListener(v -> {
            if(singleOperatorCheck()){
                binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"-");
            }else{
                showAlertDialog("Attention!","Only one operator allowed");
            }
        });
        binding.calculatorLayout.buttonMul.setOnClickListener(v -> {
            if(singleOperatorCheck()){
                binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"*");
            }else{
                showAlertDialog("Attention!","Only one operator allowed");
            }
        });
        binding.calculatorLayout.buttonDiv.setOnClickListener(v -> {
            if(singleOperatorCheck()){
                binding.mathQuestion.setText( binding.mathQuestion.getText().toString()+"/");
            }else{
                showAlertDialog("Attention!","Only one operator allowed");
            }
        });
        binding.calculatorLayout.buttonClear.setOnClickListener(v -> {binding.mathQuestion.setText("");});
    }
    private boolean singleOperatorCheck(){
        return !binding.mathQuestion.getText().toString().contains("+") && !binding.mathQuestion.getText().toString().contains("-") && !binding.mathQuestion.getText().toString().contains("*")
                && !binding.mathQuestion.getText().toString().contains("/");
    }
    private void showAlertDialog(String title, String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }
    private boolean validateEquation(){
            if(singleOperatorCheck()){
                showAlertDialog("Error!","No operator found.");
                return false;
            }else if(binding.mathQuestion.getText().toString().isEmpty()){
                showAlertDialog("Error!","No equation found.");
                return false;
            }else if(binding.mathQuestion.getText().toString().endsWith("*")||binding.mathQuestion.getText().toString().endsWith("+")||
                    binding.mathQuestion.getText().toString().endsWith("-")||binding.mathQuestion.getText().toString().endsWith("/")){
                showAlertDialog("Error!","This equation is not valid.");
                return false;
            }else {
                String[] equation;
                if(binding.mathQuestion.getText().toString().contains("+")){
                    equation = binding.mathQuestion.getText().toString().split("\\+");
                    num1=equation[0];
                    num2=equation[1];
                    operator="+";
                }else if(binding.mathQuestion.getText().toString().contains("*")){
                    equation = binding.mathQuestion.getText().toString().split("\\*");
                    num1=equation[0];
                    num2=equation[1];
                    operator="*";
                }else if(binding.mathQuestion.getText().toString().contains("-")){
                    equation = binding.mathQuestion.getText().toString().split("-");
                    num1=equation[0];
                    num2=equation[1];
                    operator="-";
                }else {
                    equation = binding.mathQuestion.getText().toString().split("/");
                    num1=equation[0];
                    num2=equation[1];
                    operator="/";
                }
                return true;
            }
    }
    private void calculate(){
            binding.calculatorLayout.buttonCalculate.setOnClickListener(v -> {
                if(validateEquation()) {
                    long timeDelay = 0;
                    if (!binding.calculatorLayout.timeDelay.getText().toString().isEmpty()) {
                        timeDelay = Long.parseLong(binding.calculatorLayout.timeDelay.getText().toString());
                    }
                    mathEngineViewModel.setupWorkRequest(Double.parseDouble(num1), Double.parseDouble(num2), operator, timeDelay);
//                    binding.mathQuestion.setText("");

                }
            });

        }

    private androidx.lifecycle.Observer<? super List<WorkInfo>> observeOutput(){
       return (Observer<List<WorkInfo>>) workInfos -> {
            inProgressEquationList=new ArrayList<>();
            completedEquationList=new ArrayList<>();
            for(int i=0; i<workInfos.size(); i++){
                EquationModel model = new EquationModel(workInfos.get(i).getOutputData().getDouble(Constants.RESULT_DATA_NUM1,0),
                        workInfos.get(i).getOutputData().getDouble(Constants.RESULT_DATA_NUM2,0),
                        workInfos.get(i).getOutputData().getDouble(Constants.RESULT_DATA,0),
                        workInfos.get(i).getOutputData().getString(Constants.RESULT_DATA_OP),
                        workInfos.get(i).getId().toString());

                if(workInfos.get(i).getState()== WorkInfo.State.ENQUEUED){
                    inProgressEquationList.add(model);
                    setupRecyclerViews(inProgressEquationList, completedEquationList);
                }else if(workInfos.get(i).getState()== WorkInfo.State.SUCCEEDED) {
                    inProgressEquationList.remove(model);
                    completedEquationList.add(model);
                    setupRecyclerViews(inProgressEquationList, completedEquationList);
                }
            }
       };
    }

    private void setupRecyclerViews(ArrayList<EquationModel> inProgressEquationList, ArrayList<EquationModel> completedEquationList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        if(inProgressEquationList.size()==0){
            binding.inProgressTxt.setVisibility(View.GONE);
            binding.view.setVisibility(View.GONE);
            binding.inProgressRV.setVisibility(View.GONE);
        }else{
            binding.inProgressTxt.setVisibility(View.VISIBLE);
            binding.view.setVisibility(View.VISIBLE);
            binding.inProgressRV.setVisibility(View.VISIBLE);
            inProgressAdapter = new InProgressAdapter(inProgressEquationList);
            binding.inProgressRV.setHasFixedSize(true);
            binding.inProgressRV.setLayoutManager(layoutManager);
            binding.inProgressRV.setAdapter(inProgressAdapter);
            inProgressAdapter.notifyDataSetChanged();
        }
        if(completedEquationList.size()==0){
            binding.view2.setVisibility(View.GONE);
            binding.completedTxt.setVisibility(View.GONE);
            binding.completedRV.setVisibility(View.GONE);
        }else{
            binding.view2.setVisibility(View.VISIBLE);
            binding.completedTxt.setVisibility(View.VISIBLE);
            binding.completedRV.setVisibility(View.VISIBLE);
            completedAdapter = new CompletedAdapter(completedEquationList);
            binding.completedRV.setHasFixedSize(true);
            binding.completedRV.setLayoutManager(layoutManager2);
            binding.completedRV.setAdapter(completedAdapter);
            completedAdapter.notifyDataSetChanged();
        }



    }

    private void clearWorkers(){
        binding.clear.setOnClickListener(v ->
                { mathEngineViewModel.clearAllWorkers();
                    inProgressEquationList= new ArrayList<>();
                    completedEquationList = new ArrayList<>();
                    setupRecyclerViews(inProgressEquationList, completedEquationList);
                });

    }

}