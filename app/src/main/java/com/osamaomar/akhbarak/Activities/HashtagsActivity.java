package com.osamaomar.akhbarak.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.osamaomar.akhbarak.Adapters.HashtagesAdapter;
import com.osamaomar.akhbarak.Helper.SpacesItemDecoration;
import com.osamaomar.akhbarak.R;

public class HashtagsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    HashtagesAdapter hashtagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtags);

        recyclerView =findViewById(R.id.hashtages);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacesItemDecoration(7));
       // recyclerView.setLayoutManager(new GridLayoutManager(HashtagsActivity.this,2));
        hashtagesAdapter=new HashtagesAdapter(HashtagsActivity.this);
        recyclerView.setAdapter(hashtagesAdapter);


    }
}
