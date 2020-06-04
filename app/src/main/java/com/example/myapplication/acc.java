package com.example.myapplication;

public class acc {
    private double x;
    private double y;
    private double z;
    private double acc;
    private  double test;
    public acc(){
        this(0, 0, 0);
    }

    public acc(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setZ(double z){
        this.z = z;
    }
    public void setAcc(double acc){
        //svm 계산
        this.acc = acc;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getZ(){
        return this.z;
    }
    public double getAcc(){
        return this.acc;
    }

    public void setAccelerometer(double x, double y, double z){
        this.x = x;
        this.y=y;
        this.z = z;

    }

}
