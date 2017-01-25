package com.example.vele_.nearbyrestaurantsfoodanddrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vele_.nearbyrestaurantsfoodanddrinks.Places.Result;

import java.util.ArrayList;

/**
 * Created by veleb on 23.01.2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Result> places;

    public PlacesAdapter(Context context,ArrayList<Result> places){
        this.context = context;
        this.places = places;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_item_layout,parent,false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).name.setText(places.get(position).getName());
        ((Item)holder).raiting.setText(places.get(position).getRating()+"");
        ((Item) holder).mRootView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlaceActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imgSrc",places.get(position).getPhotos().get(0).getPhotoReference());
                intent.putExtra("name",places.get(position).getName());
                intent.putExtra("vicinity",places.get(position).getVicinity());
                intent.putExtra("rating",places.get(position).getRating()+"");
                if (places.get(position).getOpeningHours()!=null)
                    intent.putExtra("openNow",places.get(position).getOpeningHours().isOpenNow()+"");
                else
                    intent.putExtra("openNow","NA");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(places==null)
            return 0;
        else
            return places.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        public View mRootView;
        TextView name;
        TextView raiting;
        public Item(View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.PlaceName);
            raiting = (TextView) itemView.findViewById(R.id.ratingView);
            mRootView = itemView;
        }

    }
}
