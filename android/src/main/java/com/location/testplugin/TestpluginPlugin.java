package com.location.testplugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * TestpluginPlugin
 */
public class TestpluginPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    Context context;

    private Activity activity;

    public static LocationService locationService = new LocationService();
    public static BinaryMessenger binaryMessenger;



    private void startLocationServices() {
        Intent intent = new Intent(activity.getApplicationContext(), locationService.getClass());
        intent.setAction(Constant.ACTION_START_LOCATION_SERVICE);
        try {
            activity.getApplicationContext().startService(intent);

        } catch (Exception e) {
            Log.d("sads", "startLocationServices: " + e.getMessage());
        }
    }


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        binaryMessenger = flutterPluginBinding.getBinaryMessenger();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_location_tracker");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("track")) {
            Log.d("TAG", "onMethodCall: Redda");
            if (activity == null) Log.d("TAGnull", "onMethodCall: activity Null");
            try {
                startLocationServices();
                Toast.makeText(activity.getApplication().getApplicationContext(), "Toast Fuck", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.d("TAG", "onMethodCall: Error" + e.getMessage());
            }
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
      activity = activityPluginBinding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
      activity = activityPluginBinding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {

    }
}
