package net.myanmarhub.collabra.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.appspot.myanhub_collabra.collabra.Collabra;
import com.appspot.myanhub_collabra.collabra.model.GCMDevice;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Tin Htoo Aung (Myanmar Hub) on 21/10/13.
 */
public class Utils {

    private static SharedPreferences settingsPref;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static String getSettings(Context context, String key){
        if(settingsPref == null){
            settingsPref = context.getSharedPreferences(Prefs.SETTINGS,  Context.MODE_PRIVATE);
        }
        return settingsPref.getString(key, null);
    }

    public static SharedPreferences getSettings(Context context){
        if(settingsPref == null){
            settingsPref = context.getSharedPreferences(Prefs.SETTINGS,  Context.MODE_PRIVATE);
        }
        return settingsPref;
    }

    public static Collabra getAPIService(){
        return new Collabra.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null).build();
    }

    public static Boolean isAccountExist(Context context, String accountName){
        Account account = new Account(accountName, Prefs.ACCOUNT_PROVIDER);
        AccountManager am = AccountManager.get(context);
        ArrayList<Account> accounts = new ArrayList<Account>
                (Arrays.asList(am.getAccountsByType(Prefs.ACCOUNT_PROVIDER)));
        return accounts.contains(account);
    }

    public static Boolean isAccountExist(Context context, Account account){
        AccountManager am = AccountManager.get(context);
        ArrayList<Account> accounts = new ArrayList<Account>
                (Arrays.asList(am.getAccountsByType(Prefs.ACCOUNT_PROVIDER)));
        return accounts.contains(account);
    }

    public static Account getAccount(Context context){
        return new Account(getSettings(context, Prefs.ACCOUNT_NAME), Prefs.ACCOUNT_PROVIDER);
    }

    public static Boolean checkPlayService(Activity activity){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("GCM:CHECK", "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public static void registerGCM(final Context context){
        String regId;
        final String SENDER_ID = "653266885491";

        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        regId = getRegistrationId(context);
        if(regId == null){
            new AsyncTask<Void, Integer, String>() {

                @Override
                protected String doInBackground(Void... objects) {
                    String msg = "";
                    String regId = null;
                    try {
                        regId = gcm.register(SENDER_ID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    msg = "Device registered, registration ID=" + regId;
                    Collabra collabra = Utils.getAPIService();
                    GCMDevice device = new GCMDevice();
                    device.setDeviceRegistrationID(regId);
                    try {
                        device.setDeviceInformation(URLEncoder
                                .encode(android.os.Build.MANUFACTURER
                                        + " "
                                        + android.os.Build.PRODUCT,
                                        "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    device.setTimestamp(Calendar.getInstance().getTime().getTime());

                    try{
                        collabra.gcm().device().insert(device).execute();
                        SharedPreferences pref = Utils.getSettings(context);
                        pref.edit()
                                .putString(Prefs.GCM_REGISTRATION_ID, regId)
                                .putInt(Prefs.GCM_APP_VERSION, getAppVersion(context))
                                .commit();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    return msg;
                }

                @Override
                protected void onPostExecute(String o) {}
            }.execute(null, null, null);
        }
    }

    private static String getRegistrationId(Context context){
        final SharedPreferences prefs = Utils.getSettings(context);
        String registrationId = prefs.getString(Prefs.GCM_REGISTRATION_ID, "");
        if (registrationId.length() == 0) {
            Log.i("GCM", "Registration not found.");
            return null;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(Prefs.GCM_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);

        if (registeredVersion != currentVersion) {
            Log.i("GCM", "App version changed.");
            return null;
        }
        return registrationId;
    }

    private static int getAppVersion(Context context){
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void clearData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
        Utils.getSettings(context).edit().clear().commit();
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


}
