package com.example.chatty_mobile.Adapter;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatty_mobile.R;

public class ListViewHolder {

    public TextView aTVItem;

    /**
     * Gets the textview from the XML document
     * @param base textview
     */
    public ListViewHolder(View base) {
        aTVItem = (TextView) base.findViewById(R.id.listTV);
    }
}
