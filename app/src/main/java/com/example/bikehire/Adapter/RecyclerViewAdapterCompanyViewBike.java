package com.example.bikehire.Adapter;

import android.annotation.SuppressLint;
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

import com.example.bikehire.CompanyUpdateBike;
import com.example.bikehire.DatabaseHelper;
import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.CompanyModel;
import com.example.bikehire.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterCompanyViewBike extends RecyclerView.Adapter<RecyclerViewAdapterCompanyViewBike.ViewHolder> implements Filterable {
    List<BikeModel> bikeList;
    List<BikeModel> bikeListFull;
    String name,email;
    Bitmap photo;
    Context context;

    public RecyclerViewAdapterCompanyViewBike(List<BikeModel> bikeList,String name,String email,Bitmap photo, Context context) {
        this.bikeList = bikeList;
        this.context = context;
        this.name=name;
        this.email=email;
        this.photo=photo;
        bikeListFull = new ArrayList<>(bikeList);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_company_view_bike,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bikeName.setText("Bike Name : "+bikeList.get(position).getBikename());
        holder.bikeCategory.setText("Category  : "+bikeList.get(position).getBikecategory());
        holder.bikeRate.setText("Rent : \n \u20B9 "+bikeList.get(position).getBikehourrent()+"/Hour  \u20B9 "+bikeList.get(position).getBikedayrent()+"/Day  \u20B9 "+bikeList.get(position).getBikeweekrent()+"/Week");
        holder.bikeDeposit.setText(String.valueOf("Deposite : "+bikeList.get(position).getDeposit()));
        holder.bikeImg.setImageBitmap(bikeList.get(position).getBikeimage());

        holder.bikeUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int id=bikeList.get(position).getBikeNo();
                String str=String.valueOf(id);

                Intent i = new Intent(view.getContext(), CompanyUpdateBike.class);
                i.putExtra("id", str);
                i.putExtra("companyname",name);
                i.putExtra("companyemail",email);
                i.putExtra("photo",photo);
                context.startActivity(i);
            }
        });
        holder.bikeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db=new DatabaseHelper(view.getContext());
                boolean success = db.deleteBike(bikeList.get(position).getBikeNo());
                if (success == true) {
                    Toast.makeText(view.getContext(), "Bike Deleted Successfully...!", Toast.LENGTH_SHORT).show();

                } else {
                }
                bikeList.remove(position);
                notifyDataSetChanged();
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
        TextView bikeName,bikeRate,bikeDeposit,bikeCategory;
        ImageView bikeImg;
        Button bikeUpdate,bikeDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bikeName = itemView.findViewById(R.id.view_bname);
            bikeCategory = itemView.findViewById(R.id.view_bcate);
            bikeRate = itemView.findViewById(R.id.view_brate);
            bikeDeposit = itemView.findViewById(R.id.view_bdeposit);
            bikeImg = itemView.findViewById(R.id.view_bimg);
            bikeUpdate = itemView.findViewById(R.id.view_bupdate);
            bikeDelete = itemView.findViewById(R.id.view_bdelete);


        }
    }
}
