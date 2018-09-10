package com.osamaomar.akhbarak.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osamaomar.akhbarak.R;


/**
 * Created by hossam on 21/07/2018.
 */

public class HashtagesAdapter extends RecyclerView.Adapter<HashtagesAdapter.ViewHolder>  {

    private Context context;

    public HashtagesAdapter(FragmentActivity activity) {
        context=activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hashtag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



 /*if (position==0)
          holder.hashname.setText("عدد الاشعارات الاكثر طولا");
*/
    }

    @Override
    public int getItemCount() {
        return 7;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

      //  public BloodModel.GetData mItem;

        public ImageView doctorImage;
        LinearLayout doctorLayout;
        TextView hashname;
        public ViewHolder(View view) {

            super(view);
            mView = view;
hashname=mView.findViewById(R.id.hashname);
        }
    }

}

