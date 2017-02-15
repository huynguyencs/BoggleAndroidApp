package com.example.mymac.boggle;

/**
 * Created by MyMac on 2/1/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

class ResultsAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_BOLD_ITEM = 2;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> rData = new TreeSet<Integer>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;
    public int counter = 0;

    public ResultsAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    public void addBoldItem(final String item) {
        mData.add(item);
        rData.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(sectionHeader.contains(position)) return TYPE_SEPARATOR;
        else if (rData.contains(position)) return TYPE_BOLD_ITEM;
        else return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.snippet_item1, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.snippet_item2, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
                case TYPE_BOLD_ITEM:
                    convertView = mInflater.inflate(R.layout.snippet_item3, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textBold);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

}