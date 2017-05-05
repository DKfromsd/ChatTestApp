package com.okldk.familychat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dk1.lee on 5/3/2017.
 */
public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {
  //  private String[] mDataset;
    List<Family> mFamily;
    String stEmail;
    Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvEmail;
        public ImageView ivUser;
        public Button btnChat;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmail = (TextView)itemView.findViewById(R.id.tvEmail);
            ivUser = (ImageView)itemView.findViewById(R.id.ivUser);
            btnChat= (Button) itemView.findViewById(R.id.btnChat);

        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public FamilyAdapter(List<Family> mFamily, Context context) {
        this.mFamily = mFamily;
        //this.stEmail=email;
        this.context= context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FamilyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_family, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvEmail.setText(mFamily.get(position).getEmail());

        String stPhoto = mFamily.get(position).getPhoto();
        if(TextUtils.isEmpty(stPhoto)) {
            Picasso.with(context)
                    .load(R.mipmap.ic_nophoto)
                    .fit()
                    .centerInside()
                    .into(holder.ivUser);

        } else {
            Picasso.with(context)
                    .load(stPhoto)
                    .fit()
                    .centerInside()
                    .into(holder.ivUser);
        }

        holder.btnChat.setOnClickListener(new View.OnClickListener(){

            //String stFamilyId;
            @Override
            public void onClick(View view) {
                String stFamilyId = mFamily.get(position).getKey();
                Intent in= new Intent(context, ChatActivity.class);
                in.putExtra("familyUid", stFamilyId);
                context.startActivity(in);
            }
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFamily.size();
    }
}