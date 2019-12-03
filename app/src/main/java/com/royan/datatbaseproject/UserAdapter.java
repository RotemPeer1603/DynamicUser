package com.royan.datatbaseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter
{
    private List<User> lstUser;

    public UserAdapter(List<User> lstUser)
    {
        this.lstUser = lstUser;
    }

    @Override
    public int getCount()
    {
        return lstUser.size();
    }

    @Override
    public User getItem(int pos)
    {
        return lstUser.get(pos);
    }

    @Override
    public long getItemId(int pos)
    {
        return (long) pos;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        try
        {
            User user = getItem(position);
            if (view == null)
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
            }

            TextView lblName = view.findViewById(R.id.lblName);
            TextView lblAddress = view.findViewById(R.id.lblAddress);
            String name = user.getTitle() + " " + user.getFirst() + " " + user.getLast() + " (" + user.getGender() + ")";
            lblName.setText(name);
            lblAddress.setText(user.getStreet() + " " + user.getCity() + " " + user.getCountry() + " " + user.getPostcode());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }

    public void updateList(List<User> lstUser)
    {
        this.lstUser.clear();
        this.lstUser.addAll(lstUser);
    }
}

