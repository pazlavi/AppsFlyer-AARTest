package com.paz.aartest;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MyApp extends Application implements AppsFlyerConversionListener {
    public final static String KEY = "4ux8wjmC9qP6qc3UWZ5Ldh";
    public final static String LOG_TAG = "AFApplication";


    @Override
    public void onCreate() {
        super.onCreate();
        AppsFlyerLib.getInstance().setDebugLog(true);
        AppsFlyerLib.getInstance().init(KEY, this, getApplicationContext());
        AppsFlyerLib.getInstance().start(this);

    }


    @Override
    public void onConversionDataFail(String errorMessage) {
        Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
    }


    @Override
    public void onAttributionFailure(String errorMessage) {
        Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
    }

    @Override
    public void onConversionDataSuccess(Map<String, Object> conversionData) {
        for (String attrName : conversionData.keySet())
            Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionData.get(attrName));
        if (!conversionData.containsKey("deep_link_value") && conversionData.containsKey("af_dp")) {
            Map<String, String> newMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
                newMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }

        }

        String status = Objects.requireNonNull(conversionData.get("af_status")).toString();
        if (status.equals("Non-organic")) {
            if (Objects.requireNonNull(conversionData.get("is_first_launch")).toString().equals("true")) {
                Log.d(LOG_TAG, "Conversion: First Launch");
                if (conversionData.containsKey("deep_link_value")) {
                    Log.d(LOG_TAG, "Conversion: This is deferred deep linking.");
                    Map<String, String> newMap = new HashMap<>();
                    for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
                        newMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                    onAppOpenAttribution(newMap);
                }
            } else {
                Log.d(LOG_TAG, "Conversion: Not First Launch");
            }
        } else {
            Log.d(LOG_TAG, "Conversion: This is an organic install.");
        }
    }

    @Override
    public void onAppOpenAttribution(Map<String, String> attributionData) {
        if (!attributionData.containsKey("is_first_launch"))
            Log.d(LOG_TAG, "onAppOpenAttribution: This is NOT deferred deep linking");
        for (String attrName : attributionData.keySet()) {
            String deepLinkAttrStr = attrName + " = " + attributionData.get(attrName);
            Log.d(LOG_TAG, "Deeplink attribute: " + deepLinkAttrStr);
        }
        Log.d(LOG_TAG, "onAppOpenAttribution: Deep linking into " + attributionData.get("deep_link_value"));
    }


}