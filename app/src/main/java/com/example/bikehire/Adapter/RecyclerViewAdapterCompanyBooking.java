package com.example.bikehire.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikehire.CompanyBookingStatus;
import com.example.bikehire.CompanyViewFeedback;
import com.example.bikehire.DatabaseHelper;
import com.example.bikehire.Model.BikeModel;
import com.example.bikehire.Model.BookingModel;
import com.example.bikehire.Model.UserModel;
import com.example.bikehire.R;
import com.example.bikehire.UserBookingStatus;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterCompanyBooking extends RecyclerView.Adapter<RecyclerViewAdapterCompanyBooking.ViewHolder> implements Filterable {
    List<BookingModel> bookingList;
    List<BookingModel> bookingListFull;
    Context context;

    public RecyclerViewAdapterCompanyBooking(List<BookingModel> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
        bookingListFull = new ArrayList<>(bookingList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_company_view_booking,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int userid = bookingList.get(position).getBookingCustomerId();
        int bikeid = bookingList.get(position).getBookingBikeId();
        DatabaseHelper dbhelper = new DatabaseHelper(context.getApplicationContext());
        List<UserModel> all=dbhelper.viewOneUser(userid);
        List<BikeModel> all1=dbhelper.viewBike(bikeid);
        holder.bookingId.setText("Booking ID : "+bookingList.get(position).getBookingId());
        holder.customerName.setText("Customer Name : "+all.get(0).getName());
        holder.bikeName.setText("Bike Name : "+all1.get(0).getBikename());
        holder.customerPhno.setText("Customer Mobile No. : "+all.get(0).getPhoneno());
        holder.bookingDate.setText("Booking Date : "+bookingList.get(position).getBookingDate());
        holder.journeyDate.setText("Journey Date : "+bookingList.get(position).getBookingJourneyDate());
        holder.returnDate.setText("Return Date : "+bookingList.get(position).getBookingReturnDate());
        holder.bookingAmount.setText("Amount : \u20B9 "+bookingList.get(position).getBookingAmount());
        holder.bikeImg.setImageBitmap(all1.get(0).getBikeimage());
        int st  = bookingList.get(position).getBookingStatus();
        if(st == 1) {
            holder.viewfeedback.setVisibility(View.INVISIBLE);
            holder.bookingstatus.setText("Booking Pending");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));
        }else if(st == 2){
            holder.viewfeedback.setVisibility(View.INVISIBLE);
            holder.bookingstatus.setText("Booking Confirmed");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.green));
        }else if(st == 3){
            holder.viewfeedback.setVisibility(View.INVISIBLE);
            holder.bookingstatus.setText("Booking Cancelled");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.red));
        }else if(st == 4) {
            holder.viewfeedback.setVisibility(View.VISIBLE);
            holder.bookingstatus.setText("Booking Completed");
            holder.bookingstatus.setTextColor(context.getResources().getColor(R.color.green));
        }


        holder.continuebooking.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                int status = bookingList.get(position).getBookingStatus();
                String bookingstatus=String.valueOf(status);
                int id=bookingList.get(position).getBookingId();
                String bookingid=String.valueOf(id);
                String bookingdate=bookingList.get(position).getBookingDate();
                String address=bookingList.get(position).getBookingDeliveryAddress();
                double d=bookingList.get(position).getBookingAmount();
                String amt=String.valueOf(d);
                Intent intent = new Intent(context.getApplicationContext(), CompanyBookingStatus.class);
                intent.putExtra("bookingid",bookingid);
                intent.putExtra("bookingdate",bookingdate);
                intent.putExtra("bookingStatus",bookingstatus);
                intent.putExtra("address",address);
                intent.putExtra("amount",amt);
                context.startActivity(intent);
            }
        });

        holder.viewfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=bookingList.get(position).getBookingId();
                String bookingid=String.valueOf(id);
                Intent intent = new Intent(context.getApplicationContext(), CompanyViewFeedback.class);
                intent.putExtra("bookingId",bookingid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    @Override
    public Filter getFilter() {
        return requestFilter;
    }

    private Filter requestFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BookingModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(bookingListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BookingModel item : bookingListFull) {
                    if (item.getBookingDate().toLowerCase().contains(filterPattern)) {
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
            bookingList.clear();
            bookingList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingId,bookingDate,bikeName,customerName,customerPhno,journeyDate,returnDate,bookingAmount,bookingstatus;
        ImageView bikeImg;
        Button viewfeedback;
        RelativeLayout continuebooking;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingId = itemView.findViewById(R.id.comp_bookingid);
            bookingDate = itemView.findViewById(R.id.comp_bookingdate);
            bikeImg = itemView.findViewById(R.id.viewbike_img);
            bikeName = itemView.findViewById(R.id.comp_bikename);
            customerName = itemView.findViewById(R.id.comp_customername);
            customerPhno = itemView.findViewById(R.id.comp_customerphno);
            journeyDate = itemView.findViewById(R.id.comp_journeydate);
            returnDate = itemView.findViewById(R.id.comp_returndate);
            bookingAmount = itemView.findViewById(R.id.comp_bookingamount);
            bookingstatus = itemView.findViewById(R.id.viewbooking_status);
            continuebooking = itemView.findViewById(R.id.continuebooking1);
            viewfeedback = itemView.findViewById(R.id.comp_viewfeedback);

        }
    }

}

