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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterAdminCategory extends RecyclerView.Adapter<RecyclerViewAdapterAdminCategory.ViewHolder> implements Filterable {
    List<CategoryModel> categoryList;
    List<CategoryModel> categoryListFull;
    String name,email;
    Context context;

    public RecyclerViewAdapterAdminCategory(List<CategoryModel> categoryList,String name,String email, Context context) {
        this.categoryList = categoryList;
        this.name=name;
        this.email=email;
        this.context = context;
        categoryListFull = new ArrayList<>(categoryList);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_view_category,parent,false);
        ViewHolder holder= new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ctname.setText(categoryList.get(position).getCatename());
        holder.ctpicture.setImageBitmap(categoryList.get(position).getCatepicture());

        holder.ctupdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int sid=categoryList.get(position).getId();
                String str=String.valueOf(sid);
                String str1=categoryList.get(position).getCatename().toString();
                String str3=categoryList.get(position).getCatedesc().toString();
                holder.ctpicture.buildDrawingCache();
                Bitmap str4 = holder.ctpicture.getDrawingCache();

                Intent i = new Intent(view.getContext(), AdminUpdateCategory.class);
                i.putExtra("id", str);
                i.putExtra("name", str1);
                i.putExtra("desc", str3);
                i.putExtra("pic", str4);
                i.putExtra("adminname",name);
                i.putExtra("adminemail",email);
                context.startActivity(i);
            }
        });
        holder.ctdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db=new DatabaseHelper(view.getContext());
                boolean success = db.deleteCategory(categoryList.get(position).getId());
                if (success == true) {
                    Toast.makeText(view.getContext(), "Category Deleted Successfully...!", Toast.LENGTH_SHORT).show();

                } else {
                }
                categoryList.remove(position);
                notifyDataSetChanged();
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
        Button ctupdate,ctdelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ctname = itemView.findViewById(R.id.ctname);
            ctpicture = itemView.findViewById(R.id.img_cate);
            ctupdate = itemView.findViewById(R.id.update_cate);
            ctdelete = itemView.findViewById(R.id.delete_cate);

        }
    }
}
