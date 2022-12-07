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

import com.example.bikehire.AdminUpdateCategory;
import com.example.bikehire.DatabaseHelper;
import com.example.bikehire.Model.CategoryModel;
import com.example.bikehire.R;
import com.example.bikehire.UserViewBike;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterUserBikeCategory extends RecyclerView.Adapter<RecyclerViewAdapterUserBikeCategory.ViewHolder> implements Filterable {
    List<CategoryModel> categoryList;
    List<CategoryModel> categoryListFull;
    String userid,name,email;
    Bitmap photo;
    Context context;

    public RecyclerViewAdapterUserBikeCategory(List<CategoryModel> categoryList,String userid,String name,String email,Bitmap photo, Context context) {
        this.categoryList = categoryList;
        this.userid=userid;
        this.name=name;
        this.email=email;
        this.photo=photo;
        this.context = context;
        categoryListFull = new ArrayList<>(categoryList);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_view_bike_category,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ctname.setText(categoryList.get(position).getCatename());
        holder.ctpicture.setImageBitmap(categoryList.get(position).getCatepicture());

        holder.ctpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1=categoryList.get(position).getCatename().toString();

                Intent i = new Intent(view.getContext(), UserViewBike.class);
                i.putExtra("catename", str1);
                i.putExtra("userid",userid);
                i.putExtra("name",name);
                i.putExtra("email",email);
                i.putExtra("photo",photo);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public Filter getFilter() {
        return categoryFilter;
    }

    private Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CategoryModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(categoryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CategoryModel item : categoryListFull) {
                    if (item.getCatename().toLowerCase().contains(filterPattern)) {
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
            categoryList.clear();
            categoryList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ctname;
        ImageView ctpicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ctname = itemView.findViewById(R.id.b_catename);
            ctpicture = itemView.findViewById(R.id.b_cateimg);

        }
    }
}
