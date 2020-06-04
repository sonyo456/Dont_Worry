package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class RFModel {
    //private final Attribute attribute_HR = new Attribute("HR");
    private final Attribute attribute_LAT = new Attribute("LAT");
    private final Attribute attribute_LON = new Attribute("LON");
    private final Attribute attribute_SVM = new Attribute("SVM");
    private final Attribute attribute_SPEED = new Attribute("SPEED");
    private final List<String> actClassList = new ArrayList<String>() {
        {
            add("WALKING");
            add("RUNNING");
            add("STATIONARY");
            add("IN_VEHICLE");
            add("IN_CAR");
        }
    };

    private ArrayList<Attribute> attributeList;
    private Instances dataUnpredicted;
    private Classifier classifier;


//    public String result2;
    public LocationInformation location;

    public acc acc;

    public RFModel(){
        this.attributeList = new ArrayList<Attribute>(2) {
            {
                //add(attribute_HR);
                add(attribute_LAT);
                add(attribute_LON);
                add(attribute_SVM);
                add(attribute_SPEED);
                Attribute attributeClass = new Attribute("@@class@@", actClassList);
                add(attributeClass);
            }
        };

        this.dataUnpredicted = new Instances("CurrentInstance",
                attributeList, 1);

        int attnum = this.dataUnpredicted.numAttributes();
        this.dataUnpredicted.setClassIndex(attnum - 1);
        Log.d("actrecog", attnum+"");

        this.location = new LocationInformation();

        //AssetManager assetManager = getAssets();
        try {
            //cls = (Classifier) weka.core.SerializationHelper.read(getAssets().open("REAL_MODEL.model"));

            classifier = (Classifier) weka.core.SerializationHelper.read("/data/user/0/com.example.myapplication/model/svm_model.model");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (classifier == null) {
            Log.d("actrecog", "Classifier is Null");
        }
    }

    public void setLocation (double latitude, double longitude, double speed) {
        this.location.setLocationInformation(latitude, longitude, speed);
    }

    public void setLocation (LocationInformation locationinformation) {
        this.location = locationinformation;
    }

    public String classifyActtype() {
        String acttype="";

        DenseInstance currentInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                setValue(attribute_LAT, location.getLatitude());
                setValue(attribute_LON, location.getLongitude());
                setValue(attribute_SVM, 2.742259335);
                setValue(attribute_SPEED,location.getSpeed());
            }
        };
        currentInstance.setDataset(dataUnpredicted);

        // 모델에 예측할 instance

        try {
            double result = classifier.classifyInstance(currentInstance);
            acttype = actClassList.get(new Double(result).intValue());
            Log.d("actrecog", "result behavior: " + acttype);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acttype;
    }
}
