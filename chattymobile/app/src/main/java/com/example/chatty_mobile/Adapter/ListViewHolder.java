package com.example.chatty_mobile.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatty_mobile.R;

public class ListViewHolder {

    public TextView aTVItem;
    public TextView userName;
    public ImageView avatar;
    public ImageView imgMessenge;

    /**
     * Gets the textview from the XML document
     * @param base textview
     */
    public ListViewHolder(View base) {
        aTVItem = (TextView) base.findViewById(R.id.listTV);
        userName = (TextView) base.findViewById(R.id.userName);
        avatar = (ImageView) base.findViewById(R.id.avatarImage);
        imgMessenge = (ImageView) base.findViewById(R.id.imgmsg);
    }
}
