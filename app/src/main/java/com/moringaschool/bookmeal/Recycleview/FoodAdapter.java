package com.moringaschool.bookmeal.Recycleview;

import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;

public class  FoodAdapter extends RecyclerView.Adapter<FoodAdapter.foodviewholder> {
    List<Food> mdata;
    RecyclerViewClickListener listener;

    public FoodAdapter(List<Food> mdata, RecyclerViewClickListener listener) {
        this.listener=listener;
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
        String name=mdata.get(position).getName();
        double price=mdata.get(position).getPrice();
        String description=mdata.get(position).getDescription();
        String imageURL=mdata.get(position).getImgUrl();
        String price_string=String.valueOf(price);
        holder.name.setText(name);
        holder.price.setText(price_string);
        holder.imageFood.setImageURI(Uri.parse(imageURL));
        Glide.with(holder.itemView.getContext())
                .load(mdata.get(position).getDrawableResource())
                .transforms(new CenterCrop(),new RoundedCorners(16))
                .into(holder.imageFood);

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
    public  void filterList(List<Food> fiteredList){
        mdata=fiteredList;
        notifyDataSetChanged();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }


    public class foodviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageFood;
        TextView name,price;
        Button order;
        public foodviewholder(@NonNull View itemView) {
            super(itemView);
            imageFood=itemView.findViewById(R.id.item_food_img);
            name=itemView.findViewById(R.id.item_food_name);
            price=itemView.findViewById(R.id.item_food_price);
            order=itemView.findViewById(R.id.item_food_order_btn);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
            if(view==itemView){
                List<Food> new_order=new ArrayList<>();
            }
        }
    }
}
