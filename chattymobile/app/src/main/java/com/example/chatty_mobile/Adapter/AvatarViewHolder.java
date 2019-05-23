package com.example.chatty_mobile.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatty_mobile.R;

public class AvatarViewHolder {

    public ImageView avatar;


    /**
     * Gets the view from the XML file
     * @param base
     */
    public AvatarViewHolder(View base) {
        avatar = (ImageView) base.findViewById(R.id.avatar);
    }
}
