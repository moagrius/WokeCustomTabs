package org.oreilly.chrometabapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startTabs();
  }

  @Override
  protected void onResume() {
    super.onResume();
    // doesn't always get disconnected when closed, let's clear also on resume of the activity directly beneath the tabs activity
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  private void startTabs() {
    String packageName = CustomTabsHelper.getPackageNameToUse(this);
    CustomTabsClient.bindCustomTabsService(this, packageName, mCustomTabsServiceConnection);
    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    builder.build().launchUrl(this, Uri.parse("https://www.google.com"));
  }

  CustomTabsServiceConnection mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
    @Override
    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
  };

}
