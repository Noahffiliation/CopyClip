package com.example.copyclip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class fragment_buffer extends Fragment {
    private CustomListAdapter adapter;
    private ArrayList<String> buffer;
    public static final String bufferTag = "buffer";

    public fragment_buffer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buffer, container, false);

        final Shared shared = new Shared(getActivity().getApplicationContext());
        this.buffer = shared.getList(bufferTag);
        this.adapter = new CustomListAdapter();

        ListView listView = view.findViewById(R.id.card_list);
        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                shared.copyToNativeClipboard(buffer.get(arg2));
                Toast.makeText(getActivity(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class CustomListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (buffer.size() != 0) {
                return buffer.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return buffer.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.card_item, null);
                holder.text = convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;
            holder.text.setText(buffer.get(holder.ref));

            return convertView;
        }

        private class ViewHolder {
            TextView text;
            int ref;
        }
    }
}
