package com.paz.aartest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MaterialButton button;
    public final static String TAG = "AFApplication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> logEvent());
    }

    private void logEvent() {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put(AFInAppEventParameterName.CONTENT_ID, new String[]{"123", "988", "399"});
        eventData.put(AFInAppEventParameterName.QUANTITY, new int[]{2, 1, 1});
        eventData.put(AFInAppEventParameterName.PRICE, new int[]{25, 50, 10});
        eventData.put(AFInAppEventParameterName.CURRENCY, "USD");
        eventData.put(AFInAppEventParameterName.REVENUE, 110);
        eventData.put("CUID", "\uD83E\uDD14\uD83E\uDD14\uD83D\uDCBB✅✅✅✅");

        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventData, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: IAE");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d(TAG, "onError: IAE");

            }
        });
    }
}