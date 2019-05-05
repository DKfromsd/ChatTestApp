package com.okldk.familychat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dk1.lee on 5/3/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private List<Chat> mChat;
    Context context;
    String stEmail;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.mtextView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Chat> mChat, String email, Context context) {
        this.mChat = mChat;
        this.stEmail=email;
        this.context= context;
    }

    @Override
    public int getItemViewType(int position) {

        if(mChat.get(position).getEmail().equals(stEmail)){
            return 1;
        }else{
            return 2;
        }

    }
    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v;
        if(viewType==1){
            v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.right_text_view, parent, false);

        }else{
            v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
    //public void onBindViewHolder(ViewHolder holder, final String stEmail) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //TODO 2019 04 24
        holder.mTextView.setText(mChat.get(position).getText());
        //holder.mTextView.setText(stEmail);

        holder.mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context,stEmail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mChat.size();
    }
}