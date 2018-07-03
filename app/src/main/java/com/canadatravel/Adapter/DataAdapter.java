package com.canadatravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.canadatravel.R;
import com.canadatravel.model.Rows;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Rows> dataList;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txt_title,txt_description;
        public ImageView img_type;
        public ViewHolder(View v) {
            super(v);
            txt_title=(TextView)v.findViewById(R.id.txt_title);
            txt_description=(TextView)v.findViewById(R.id.txt_description);
            img_type=(ImageView) v.findViewById(R.id.image);


        }
    }
    public DataAdapter(List<Rows> logList, Context mContext) {
        this.dataList = logList;
        this.context = mContext;
    }
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Rows> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_text,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
         // update view
        if(dataList.get(position).getTitle()!=null){
            holder.txt_title.setText(dataList.get(position).getTitle());
            if(dataList.get(position).getDescription() != null){
                holder.txt_description.setText(dataList.get(position).getDescription());
            }
          else{
                holder.txt_description.setText("No Description");
            }
               try {
                    //Picasso.with(context).load(dataList.get(position).getImageHref()).into(holder.img_type);
                    Picasso.get().load(dataList.get(position).getImageHref()).placeholder(holder.img_type.getDrawable()).into(holder.img_type);
                }catch (Exception e){
                    Log.e("er",e.getMessage());
                }

        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
