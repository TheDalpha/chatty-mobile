package com.example.chatty_mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.chatty_mobile.R;

import java.util.List;

public class AdapterList extends BaseAdapter {

    private Activity aContext;
    private List<String> aList;
    private LayoutInflater aLayoutInflator = null;

    public AdapterList(Activity context, List<String> list) {
        this.aContext = context;
        this.aList = list;
        aLayoutInflator = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gets the total size of the list
     * @return zize of the list
     */
    @Override
    public int getCount() {
        return aList.size();
    }

    /**
     * Gets the item position
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return aList.get(position);
    }

    /**
     * Gets the item id on position
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Inflates the listview with a textview
     * @param position position of the textview
     * @param convertView textview
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ListViewHolder lvHolder;
        if( convertView == null ) {
            LayoutInflater li = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_layout, null);
            lvHolder = new ListViewHolder(v);
            v.setTag(lvHolder);
        } else {
            lvHolder = (ListViewHolder) v.getTag();
        }
        lvHolder.aTVItem.setText(aList.get(position));
        return v;
    }
}
