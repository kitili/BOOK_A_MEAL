package com.moringaschool.bookmeal.Recycleview;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.moringaschool.bookmeal.Model.Menu;
import com.moringaschool.bookmeal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private ArrayList<Menu> menuList;
    public MenuAdapter(ArrayList<Menu> menuList){
        this.menuList=menuList;
    }

    public static void filterList(List<Menu> filteredList) {
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFood;
        TextView name, price;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.item_food_img);
            name = itemView.findViewById(R.id.item_food_name);
            price = itemView.findViewById(R.id.item_food_price);
            //itemView.setOnClickListener(this);
        }
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_list_menu_item,parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        //binding food data here
        String name=menuList.get(position).getName();
        double price=menuList.get(position).getPrice();
        String description=menuList.get(position).getDescription();
        String imageURL=menuList.get(position).getMenuImage();
        String price_string=String.valueOf(price);
        holder.name.setText(name);
        holder.price.setText(price_string);
       // holder.imageFood.setImageURI(Uri.parse(imageURL));
        String TAG="MainActivity";
        Log.e(TAG,"msg====================================>"+imageURL);
       Picasso.get().load(imageURL).into(holder.imageFood);


    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


}
