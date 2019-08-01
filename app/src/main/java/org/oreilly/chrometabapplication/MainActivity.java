package org.oreilly.chrometabapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;

public class MainActivity extends Activity {

  private CustomTabsClient mClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    String packageName = CustomTabsHelper.getPackageNameToUse(this);
//    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
//    customTabsIntent.intent.setPackage(packageName);
//    String url = "http://www.oreilly.com";
//    customTabsIntent.launchUrl(this, Uri.parse(url));
    startTabs();
  }

  @Override
  protected void onResume() {
    super.onResume();
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  private PowerManager.WakeLock mWakeLock;

  private void startTabs() {
    // Binds to the service.
    String packageName = CustomTabsHelper.getPackageNameToUse(this);
    CustomTabsClient.bindCustomTabsService(this, packageName, new CustomTabsServiceConnection() {
      @Override
      public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
        // mClient is now valid.
        mClient = client;
        Log.d("MyApp", "connected");
        Log.d("MyApp", "component? " + name);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");
//        mWakeLock.acquire();

      }

      @Override
      public void onServiceDisconnected(ComponentName name) {
        // mClient is no longer valid. This also invalidates sessions.
        mClient = null;
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        mWakeLock.release();
      }
    });

    // With a valid mClient.
    //mClient.warmup(0);

    // With a valid mClient.
//    CustomTabsSession session = mClient.newSession(new CustomTabsCallback());
//    session.mayLaunchUrl(Uri.parse("https://www.google.com"), null, null);

    // Shows the Custom Tab
    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    builder.build().launchUrl(this, Uri.parse("https://www.google.com"));

  }
}
