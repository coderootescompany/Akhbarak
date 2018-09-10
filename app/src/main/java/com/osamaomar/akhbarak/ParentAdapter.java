package com.osamaomar.akhbarak;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osamaomar.akhbarak.Activities.HashtagsActivity;
import com.osamaomar.akhbarak.Activities.LoginActivity;
import com.osamaomar.akhbarak.Activities.PostDetailsActivity;
import com.osamaomar.akhbarak.Activities.Register.Register1Activity;
import com.osamaomar.akhbarak.Activities.Register.Register5Activity;
import com.osamaomar.akhbarak.Activities.Register.Register6Activity;
import com.osamaomar.akhbarak.Activities.SettingActivity;
import com.osamaomar.akhbarak.Assymetric.AsymmetricRecyclerView;
import com.osamaomar.akhbarak.Assymetric.AsymmetricRecyclerViewAdapter;
import com.osamaomar.akhbarak.Assymetric.Utils;
import com.osamaomar.akhbarak.model.ItemList;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MyViewHolder> {
 
    private List<ItemList> mItemList;
    private Context mCon;
    private int mDisplay= 0;
    private int mTotal= 0;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle,description,gotologin;
        public AsymmetricRecyclerView recyclerView;
        LinearLayout like,comment,share;
 
        public MyViewHolder(View view) {
            super(view);
            tvTitle =  view.findViewById(R.id.tvTitle);
            description =  view.findViewById(R.id.description);
            recyclerView =  view.findViewById(R.id.recyclerView);

            like =view.findViewById(R.id.likelayout);
            gotologin =view.findViewById(R.id.gotologin);
          //  gotologin.setMovementMethod(LinkMovementMethod.getInstance());
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCon.startActivity(new Intent(mCon, LoginActivity.class));

                }
            });

            recyclerView.setRequestedColumnCount(3);
            recyclerView.setDebugging(true);
            recyclerView.setRequestedHorizontalSpacing(Utils.dpToPx(mCon, 3));
            recyclerView.addItemDecoration(
                    new SpacesItemDecoration(mCon.getResources().getDimensionPixelSize(R.dimen.recycler_padding)));
        }
    }
 
 
    public ParentAdapter(Context con, List<ItemList> moviesList, int max_display, int mTotal_size) {
        mCon = con;
        this.mItemList = moviesList;
        mDisplay = max_display;
        mTotal = mTotal_size;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parent_item, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemList item = mItemList.get(position);
        String title = mCon.getString(R.string.title, item.getItemName(),mTotal+"");
        holder.tvTitle.setText(Html.fromHtml(title));
        ChildAdapter adapter = new ChildAdapter(item.getImages(),mDisplay,mTotal);

            addReadMore("حادث مروع علي الطريق الدائري ادي الي الكثير من الخسائر",holder.description);
        holder.recyclerView.setAdapter(new AsymmetricRecyclerViewAdapter<>(mCon,holder.recyclerView, adapter));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCon.startActivity(new Intent(mCon, PostDetailsActivity.class));
            }
        });

        setFadeAnimation(holder.itemView);
        //setAnimation(holder.itemView,position);
    }
 
    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    private void addReadMore(final String text, final TextView textView) {

        SpannableString ss = new SpannableString(text.substring(0,23) +"  ... "+mCon.getResources().getString(R.string.readmore));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addReadLess(text, textView);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //   ds.setColor(getResources().getColor(R.color.color_primary, getTheme()));
                } else {
                    // ds.setColor(getResources().getColor(R.color.color_primary));
                }
            }
        };
        ss.setSpan(clickableSpan, ss.length() - 12, ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addReadLess(final String text, final TextView textView) {
        SpannableString ss = new SpannableString(text +" "+mCon.getResources().getString(R.string.readless));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addReadMore(text, textView);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //   ds.setColor(getResources().getColor(R.color.color_primary, getTheme()));
                } else {
                    //  ds.setColor(getResources().getColor(R.color.color_primary));
                }
            }
        };
        ss.setSpan(clickableSpan, ss.length() - 10, ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void setAnimation(View viewToAnimate,int position){
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mCon,android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }

    }
    private void setFadeAnimation(View viewToAnimate){

        AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(2000);
        viewToAnimate.startAnimation(animation);
    }
}