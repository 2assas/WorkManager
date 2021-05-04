package com.example.mathengine.pojo;

public class EquationModel {
    double num1=0;
    double num2=0;
    double result=0;
    String operator="";
    String id="";

    public EquationModel() {
    }

    public EquationModel(double num1, double num2, double result, String operator, String id) {
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
        this.operator = operator;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
