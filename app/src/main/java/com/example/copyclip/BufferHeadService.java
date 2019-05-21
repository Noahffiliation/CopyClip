package com.example.copyclip;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BufferHeadService extends Service {
    private WindowManager windowManager;
    private View bufferHead;
    private LayoutInflater inflater;
    private ArrayList<String> buffer;
    private CustomListAdapter adapter;
    private Shared shared;
    boolean usingPinned;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.usingPinned = false;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bufferHead = inflater.inflate(R.layout.buffer_overlay, null);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.x = 0;
        params.y = 100;

        this.shared = new Shared(getApplicationContext());
        this.buffer = shared.getList("buffer");
        this.adapter = new CustomListAdapter();
        ListView listView = bufferHead.findViewById(R.id.list);
        listView.setAdapter(this.adapter);

        final Button buttonRecent = bufferHead.findViewById(R.id.buttonRecent);
        final Button buttonPinned = bufferHead.findViewById(R.id.buttonPinned);
        buttonRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buffer = shared.getList("buffer");
                adapter.notifyDataSetChanged();
                buttonRecent.setTextColor(getResources().getColor(R.color.colorPrimary));
                buttonPinned.setTextColor(getResources().getColor(R.color.black));
            }
        });

        buttonPinned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buffer = shared.getList("pinned");
                adapter.notifyDataSetChanged();
                buttonRecent.setTextColor(getResources().getColor(R.color.black));
                buttonPinned.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        Button buttonClose = bufferHead.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });

        windowManager.addView(bufferHead, params);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bufferHead != null) windowManager.removeView(bufferHead);
    }

    private class CustomListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (buffer.size() != 0) {
                return buffer.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return buffer.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.card_item, null);
                holder.text = convertView.findViewById(R.id.text);
                holder.pin = convertView.findViewById(R.id.pin);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;
            holder.text.setText(buffer.get(holder.ref));

            if(shared.getList("pinned").contains(buffer.get(holder.ref))){
                holder.pin.setColorFilter(getColor(R.color.colorPrimary));
            }else{
                holder.pin.setColorFilter(getColor(R.color.iconGray));
            }


            holder.pin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(shared.getList("pinned").contains(buffer.get(holder.ref))){
                        adapter.notifyDataSetChanged();
                    }else{
                        shared.updateList(getApplicationContext(), "pinned", buffer.get(holder.ref));
                        adapter.notifyDataSetChanged();
                    }

                }
            });

            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shared.copyToNativeClipboard(buffer.get(holder.ref));
                    onDestroy();
                }
            });


            return convertView;
        }

        private class ViewHolder {
            TextView text;
            android.support.v7.widget.AppCompatImageButton pin;
            int ref;
        }


    }
}
