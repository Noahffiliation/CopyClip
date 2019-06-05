package com.example.copyclip;

import android.content.Context;
import android.net.Uri;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomListAdapter adapter;
    private ArrayList<String> buffer;
    public static final String bufferTag = "buffer";

    private OnFragmentInteractionListener mListener;

    public fragment_buffer() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static fragment_buffer newInstance(String param1, String param2) {
        fragment_buffer fragment = new fragment_buffer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume(){
        super.onResume();
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
