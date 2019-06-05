package com.example.copyclip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Shared {
    public static final String bufferTag = "buffer";
    public static final String pinnedTag = "pinned";
    private static final String maxBufferTag = "maxBuffer";
    private static final String maxPinnedTag = "maxPinned";
    private static final int defaultMaxBuffer = 5;
    private static final int defaultMaxPinned = 5;

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

    public int getMaxPinned(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPrefs.contains(maxPinnedTag)) {
            return sharedPrefs.getInt(maxPinnedTag, defaultMaxPinned);
        } else {
            return defaultMaxPinned;
        }
    }

    public void setMaxPinned(int max){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt(maxPinnedTag, max);
        editor.apply();
    }

    public void copyToNativeClipboard(String item){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(item, item);
        clipboard.setPrimaryClip(clip);
    }

    private class OnePointerQueue {
        private class Node {
            String item;
            Node next;
        }

        private Node last;
        private int size = 0;
        private int enqueueCount = 0;
        private int dequeueCount = 0;

        public OnePointerQueue() {

        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void enqueue(String item) {
            Node old = last;
            last = new Node();
            last.item = item;
            if (isEmpty()) {
                last.next = last;
            } else {
                last.next = old.next;
                old.next = last;
            }

            size++;
        }

        public String dequeue() {
            if (isEmpty()) throw new NullPointerException("Queue is empty");

            String item = last.next.item;
            if (last == last.next) {
                last = null;
            } else {
                last.next = last.next.next;
            }

            size--;

            return item;
        }

        public int size() {
            return size;
        }

        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private Node current = last.next;
                private int counter = 0;
                private int numEnqueue = enqueueCount;
                private int numDequeue = dequeueCount;

                @Override
                public boolean hasNext() {
                    if ((numEnqueue != enqueueCount) || (numDequeue != dequeueCount)) {
                        throw new ConcurrentModificationException();
                    }

                    return counter <= size;
                }

                @Override()
                public String next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    if ((numEnqueue != enqueueCount) || (numDequeue != dequeueCount)) {
                        throw new ConcurrentModificationException();
                    }

                    String item = current.item;
                    current = current.next;
                    ++counter;
                    return item;
                }
            };
        }

        public void removeOld(int maxSize) {
            if (size >= maxSize) {
                this.dequeue();
            }
        }
    }

    OnePointerQueue bufferQueue = new OnePointerQueue();
    OnePointerQueue pinnedQueue= new OnePointerQueue();
}
