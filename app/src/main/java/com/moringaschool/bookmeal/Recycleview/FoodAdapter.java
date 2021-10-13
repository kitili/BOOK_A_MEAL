package com.moringaschool.bookmeal.Recycleview;

import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.moringaschool.bookmeal.Model.Food;
import com.moringaschool.bookmeal.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.foodviewholder> {
    List<Food> mdata;

    public FoodAdapter(List<Food> mdata) {
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public foodviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.item_food,parent,false);
       return new foodviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull foodviewholder holder, int position) {
        //binding food data here
        Glide.with(holder.itemView.getContext())
                .load(mdata.get(position).getDrawableResource())
                .transforms(new CenterCrop(),new RoundedCorners(16))
                .into(holder.imageFood);

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class foodviewholder extends RecyclerView.ViewHolder{
        ImageView imageFood;
        TextView name,price;
        Button order;
        public foodviewholder(@NonNull View itemView) {
            super(itemView);
            imageFood=itemView.findViewById(R.id.item_food_img);
            name=itemView.findViewById(R.id.item_food_name);
            price=itemView.findViewById(R.id.item_food_price);
            order=itemView.findViewById(R.id.item_food_order_btn);

        }
    }
}
