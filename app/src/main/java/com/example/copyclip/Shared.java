package com.example.copyclip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Shared {
    public static final String bufferTag = "buffer";
//    public static final String pinnedTag = "pinned";
    private static final String maxBufferTag = "maxBuffer";
//    private static final String maxPinnedTag = "maxPinned";
    private static final int defaultMaxBuffer = 10;
//    private static final int defaultMaxPinned = 10;

    private Context context;

    public Shared(Context context){
        this.context = context;
    }

    public ArrayList<String> getList(String tag){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        ArrayList<String> list;
        if (sharedPrefs.contains(tag)) {
            Gson gson = new Gson();
            String json = sharedPrefs.getString(tag, null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            list = gson.fromJson(json, type);
        } else {
            list = new ArrayList<>();
        }

        return list;
    }

    public void updateList(Context context, String tag, String item){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        ArrayList<String> currentList = getList(tag);
        if (!currentList.contains(item)) {
            currentList.add(item);
        }

        Gson gson = new Gson();
        String json = gson.toJson(currentList);

        editor.putString(tag, json);
        editor.apply();
    }

    public void remove(Context context, String tag, String item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<String> currentList = getList(tag);
        if (currentList.contains(item)) {
            currentList.remove(item);
            Log.d("Shared", "remove: " + item);
        }

        Gson gson = new Gson();
        String json = gson.toJson(currentList);

        editor.putString(tag, json);
        editor.apply();
    }

    public int getMaxBuffer(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPrefs.contains(maxBufferTag)) {
            return sharedPrefs.getInt(maxBufferTag, defaultMaxBuffer);
        } else {
            return defaultMaxBuffer;
        }
    }

    public void setMaxBuffer(int max){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt(maxBufferTag, max);
        editor.apply();
    }

//    public int getMaxPinned(){
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//        if (sharedPrefs.contains(maxPinnedTag)) {
//            return sharedPrefs.getInt(maxPinnedTag, defaultMaxPinned);
//        } else {
//            return defaultMaxPinned;
//        }
//    }
//
//    public void setMaxPinned(int max){
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//
//        editor.putInt(maxPinnedTag, max);
//        editor.apply();
//    }

    public void copyToNativeClipboard(String item){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(item, item);
        clipboard.setPrimaryClip(clip);
    }
}
