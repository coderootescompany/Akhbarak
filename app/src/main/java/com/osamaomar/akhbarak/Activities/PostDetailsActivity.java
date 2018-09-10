package com.osamaomar.akhbarak.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.osamaomar.akhbarak.Adapters.PostDetailsAdapter;
import com.osamaomar.akhbarak.R;

public class PostDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostDetailsAdapter postDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        recyclerView=findViewById(R.id.images);
        postDetailsAdapter = new PostDetailsAdapter(PostDetailsActivity.this);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(PostDetailsActivity.this));
        recyclerView.setAdapter(postDetailsAdapter);

    }
}
