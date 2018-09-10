package com.osamaomar.akhbarak.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.osamaomar.akhbarak.R;

import java.util.ArrayList;


/**
 * Created by hossam on 21/07/2018.
 */

public class ImagesAdapterForAddImages extends RecyclerView.Adapter<ImagesAdapterForAddImages.ViewHolder>  {

    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private ImageView ivGallery;
    ArrayList<Uri> mArrayUri;
    public ImagesAdapterForAddImages(Context ctx, ArrayList<Uri> mArrayUri) {
        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


     //
        // holder.ivGallery.setText(mValues.get(position).getName());

        holder.ivGallery.setImageURI(mArrayUri.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayUri.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

      //  public BloodModel.GetData mItem;

        public ImageView ivGallery;
        public ViewHolder(View view) {

            super(view);
            mView = view;

            ivGallery = (ImageView) itemView.findViewById(R.id.ivGallery);


        }
    }

}

