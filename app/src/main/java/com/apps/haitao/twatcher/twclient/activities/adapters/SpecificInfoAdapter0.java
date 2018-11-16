package com.apps.haitao.twatcher.twclient.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.tables.User_Specific_Infos;

import java.util.List;

public class SpecificInfoAdapter0 extends ArrayAdapter<User_Specific_Infos>{

    private int resourceId;

    public SpecificInfoAdapter0(@NonNull Context context, int resource, @NonNull List<User_Specific_Infos> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        User_Specific_Infos userSpecificInfos = (User_Specific_Infos)getItem(position);
        View view;
        SpecificInfoViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new SpecificInfoViewHolder();
            viewHolder.specificAttibute = (TextView)view.findViewById(R.id.specific_attribute);
            viewHolder.specificInfo = (TextView)view.findViewById(R.id.specific_info);
            viewHolder.specificAttibute.setText(userSpecificInfos.getSpecific_attribute());
            viewHolder.specificInfo.setText(userSpecificInfos.getSpecific_info());
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (SpecificInfoViewHolder)view.getTag();
        }
        return view;
    }

    class SpecificInfoViewHolder {
        TextView specificAttibute;

        TextView specificInfo;
    }
}
