package com.example.bikehire.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikehire.DatabaseHelper;
import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.CompanyModel;
import com.example.bikehire.R;
import com.example.bikehire.UserBikeBooking;
import com.example.bikehire.UserViewBike;
import com.example.bikehire.UserViewBikeDetails;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterUserViewBike extends RecyclerView.Adapter<RecyclerViewAdapterUserViewBike.ViewHolder> implements Filterable {
    List<BikeModel> bikeList;
    List<BikeModel> bikeListFull;
    Context context;

    public RecyclerViewAdapterUserViewBike(List<BikeModel> bikeList, Context context) {
        this.bikeList = bikeList;
        this.context = context;
        bikeListFull = new ArrayList<>(bikeList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_view_bike,parent,false);
        ViewHolder holder= new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DatabaseHelper db = new DatabaseHelper(context);
        String cname =db.getCompanyName(bikeList.get(position).getCompanyid());
        String bikeid=String.valueOf(bikeList.get(position).getBikeNo());
        holder.bikename.setText(bikeList.get(position).getBikename());
        holder.bikecompany.setText(cname);
        holder.bikerent.setText("Rent:\n\u20B9 "+bikeList.get(position).getBikehourrent()+"/Hour\n\u20B9"+bikeList.get(position).getBikedayrent()+"/Day\n\u20B9"+bikeList.get(position).getBikeweekrent()+"/Week");
        holder.bikedeposit.setText("Deposit : \u20B9 "+bikeList.get(position).getDeposit());
        holder.bikeImg.setImageBitmap(bikeList.get(position).getBikeimage());
        int st=bikeList.get(position).getBikestatus();


        Intent intent = ((Activity) context).getIntent();
        String str = intent.getStringExtra("userid");
        String str2 = intent.getStringExtra("name");
        String str1 = intent.getStringExtra("email");
        Bitmap str3 = intent.getParcelableExtra("photo");

        if (st == 0){
            holder.status.setText("Not Available");
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
            holder.rent.setVisibility(View.INVISIBLE);

        }
        else{
            holder.status.setText("Available");
        }

        holder.rent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserViewBikeDetails.class);
                i.putExtra("userid",str);
                i.putExtra("name",str2);
                i.putExtra("email",str1);
                i.putExtra("photo",str3);
                i.putExtra("bikeid",bikeid);
                context.startActivity(i);
            }

        });

    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    @Override
    public Filter getFilter() {
        return bikeFilter;
    }

    private Filter bikeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BikeModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(bikeListFull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BikeModel item : bikeListFull) {
                    if (item.getBikename().toLowerCase().contains(filterPattern) || item.getBikecategory().toLowerCase().contains(filterPattern) ) {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bikeList.clear();
            bikeList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bikename,bikecompany,bikerent,bikedeposit,status;
        ImageView bikeImg;
        Button rent;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bikename = itemView.findViewById(R.id.b_name);
            bikecompany = itemView.findViewById(R.id.b_company);
            bikerent = itemView.findViewById(R.id.b_rent);
            bikeImg = itemView.findViewById(R.id.b_img);
            bikedeposit = itemView.findViewById(R.id.b_deposit);
            status = itemView.findViewById(R.id.status);
            rent = itemView.findViewById(R.id.rent);


        }
    }

}
