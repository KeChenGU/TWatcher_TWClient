package com.apps.haitao.twatcher.twclient.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.viewholders.ParentInfoViewHolder;
import com.apps.haitao.twatcher.twclient.activities.gsons.MyParents;

import java.util.List;

public class ParentInfoAdapter extends RecyclerView.Adapter<ParentInfoViewHolder> {

    private List<MyParents> myParents;

    public ParentInfoAdapter(List<MyParents> myParents) {
        this.myParents = myParents;
    }

    @NonNull
    @Override
    public ParentInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.parent_dialog_item, viewGroup, false);
        return new ParentInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentInfoViewHolder parentInfoViewHolder, int i) {
        MyParents parents = myParents.get(i);
        parentInfoViewHolder.parentIcon.setImageResource(parents.getSex().equals("male") ?
                R.drawable.parent_father : R.drawable.parent_mother);
        parentInfoViewHolder.parentName.setText(parents.getParentName());
        parentInfoViewHolder.parentPhone.setText(parents.getParentPhone());
    }

    @Override
    public int getItemCount() {
        return myParents == null ? 0 : myParents.size();
    }
}
