package com.moringaschool.bookmeal.Recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.bookmeal.Model.Data;
import com.moringaschool.bookmeal.Model.LoginResponse;
import com.moringaschool.bookmeal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<LoginResponse> userList;
    UserAdapter.RecyclerViewClickListener listener;
    public UserAdapter(ArrayList<LoginResponse> userList, UserAdapter.RecyclerViewClickListener listener){
        this.userList=userList;
        this.listener=listener;
    }

    public void filterList(ArrayList<LoginResponse> fiteredList){
        userList= fiteredList;
        notifyDataSetChanged();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public class UserViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        ImageView image_user;
        TextView first_name,other_name, email,id;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            //change
            image_user = itemView.findViewById(R.id.item_user_img);
            first_name = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.item_user_email);
            id=itemView.findViewById(R.id.item_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
            if(view==itemView){
                List<Data> new_user=new ArrayList<>();
            }


        }
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                //change
                .inflate(R.layout.r_list_user_item,parent,false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String first_name= (String) userList.get(position).getData().getFirstName();
        String other_name= (String) userList.get(position).getData().getOtherName();
        String email= userList.get(position).getData().getEmail();
        String imageURL=userList.get(position).getData().getUserImage();
        String ids=userList.get(position).getData().getId();
        String name= first_name+" "+other_name;
        holder.first_name.setText(name);
        holder.email.setText(email);
        holder.id.setText(ids);
        Picasso.get().load(imageURL).into(holder.image_user);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
