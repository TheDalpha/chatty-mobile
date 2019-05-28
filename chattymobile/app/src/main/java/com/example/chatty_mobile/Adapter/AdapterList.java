package com.example.chatty_mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.chatty_mobile.R;
import com.example.chatty_mobile.models.Message;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterList extends BaseAdapter {

    private Activity aContext;
    private List<Message> _messageList;
    private LayoutInflater aLayoutInflator = null;

    public AdapterList(Activity context, List<Message> messageList) {
        this.aContext = context;
        this._messageList = messageList;
        aLayoutInflator = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gets the total size of the list
     *
     * @return zize of the list
     */
    @Override
    public int getCount() {
        return _messageList.size();
    }

    /**
     * Gets the item position
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return _messageList.get(position);
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
     * Inflates the listview with views and sets the views
     *
     * @param position    position of the textview
     * @param convertView textview
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ListViewHolder lvHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_layout, null);
            lvHolder = new ListViewHolder(v);
            v.setTag(lvHolder);
        } else {
            lvHolder = (ListViewHolder) v.getTag();
        }
        if (_messageList.get(position).getIsFile()) {
            Picasso.get().load(_messageList.get(position).getMessage()).into(lvHolder.imgMessenge);
            lvHolder.imgMessenge.setVisibility(View.VISIBLE);
            lvHolder.aTVItem.setVisibility(View.GONE);
        } else {
            lvHolder.aTVItem.setVisibility(View.VISIBLE);
            lvHolder.imgMessenge.setVisibility(View.GONE);
            lvHolder.aTVItem.setText(_messageList.get(position).getMessage());
        }
        lvHolder.userName.setText(_messageList.get(position).getUser().getUsername());
        Picasso.get().load(_messageList.get(position).getUser().getAvatar()).into(lvHolder.avatar);
        return v;
    }
}
