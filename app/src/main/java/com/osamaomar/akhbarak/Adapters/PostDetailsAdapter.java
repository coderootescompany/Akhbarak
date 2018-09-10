package com.osamaomar.akhbarak.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.osamaomar.akhbarak.R;

/**
 * Created by hossam on 21/07/2018.
**/

public class PostDetailsAdapter extends RecyclerView.Adapter<PostDetailsAdapter.ViewHolder>  {

    private Context context;
    public PostDetailsAdapter(FragmentActivity activity) {
        context=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.postdetailsitem, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

      /*Glide.with(context).load("http://sfc-oman.com/library/doctors/" +
                mValues.get(position).getPhoto()).into(holder.doctorImage);
        */
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

      //  public BloodModel.GetData mItem;

        public ImageView doctorImage;
        LinearLayout postlayout;
        public ViewHolder(View view) {

            super(view);
            mView = view;
           // postlayout=mView.findViewById(R.id.);

        }
    }

}

