package com.moringaschool.bookmeal.Recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.bookmeal.Model.Orders;
import com.moringaschool.bookmeal.R;

import java.util.ArrayList;
import java.util.List;

public class OrderUserAdapter1 extends RecyclerView.Adapter<OrderUserAdapter1.OrderViewHolder> {
    private ArrayList<Orders> orderList;
    MenuAdapter.RecyclerViewClickListener listener;
    String user_id;
    public OrderUserAdapter1(ArrayList<Orders> orderList, MenuAdapter.RecyclerViewClickListener listener,String user_id){
        this.orderList=orderList;
        this.listener=listener;
        this.user_id=user_id;

    }

    public void filterList(ArrayList<Orders> fiteredList){
        orderList= fiteredList;
        notifyDataSetChanged();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView id,price,status,first_name,other_name, email;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            //change
            price = itemView.findViewById(R.id.total);
            status = itemView.findViewById(R.id.status);
            first_name = itemView.findViewById(R.id.customer);
            id=itemView.findViewById(R.id.id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
            if(view==itemView){
                List<Orders> new_order=new ArrayList<>();
            }


        }
    }
    @NonNull
    @Override
    public OrderUserAdapter1.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                //change
                .inflate(R.layout.r_list_orders_item,parent,false);
        return new OrderUserAdapter1.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        //SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       // String user_id=sharedPreferences.getString("idKey","");

        String first_name= (String) orderList.get(position).getCustomer().getFirstName();
        String other_name= (String) orderList.get(position).getCustomer().getOtherName();
        String the_id= (String) orderList.get(position).getCustomer().getId();
        Boolean isOpen= orderList.get(position).getIsOpen();
        Integer price=orderList.get(position).getTotalAmount();
        String ids=orderList.get(position).getId();
        String name=first_name+" "+other_name;
        if((the_id.equals(user_id)) && (!isOpen)){
            holder.first_name.setText(name);
            if (isOpen == true) {
                holder.status.setText("Not Complete");
            } else {
                holder.status.setText("Complete");
            }
            holder.price.setText(String.valueOf(price));
            holder.id.setText(ids);
        }
        else{
            holder.status.setText("No Completed orders yet");
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


}
