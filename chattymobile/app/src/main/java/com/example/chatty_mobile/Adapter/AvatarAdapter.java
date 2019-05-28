package com.example.chatty_mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.chatty_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AvatarAdapter extends BaseAdapter {

    private Activity aContext;
    private List<String> _avatarUrl;
    private LayoutInflater aLayoutInflator = null;

    public AvatarAdapter(Activity context, List<String> avatarUrl) {
        this.aContext = context;
        this._avatarUrl = avatarUrl;
        aLayoutInflator = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * gets the size of the list
     *
     * @return size of the list
     */
    @Override
    public int getCount() {
        return _avatarUrl.size();
    }

    /**
     * Gets the item position
     *
     * @param position
     * @return position of the item
     */
    @Override
    public Object getItem(int position) {
        return _avatarUrl.get(position);
    }

    /**
     * Gets the item id on position
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * inflates the gridview with views and sets the views
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        AvatarViewHolder avHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.avatar_layout, null);
            avHolder = new AvatarViewHolder(v);
            v.setTag(avHolder);
        } else {
            avHolder = (AvatarViewHolder) v.getTag();
        }

        Picasso.get().load(_avatarUrl.get(position)).into(avHolder.avatar);
        return v;
    }
}
