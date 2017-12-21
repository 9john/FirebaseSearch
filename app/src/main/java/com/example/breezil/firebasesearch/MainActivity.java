package com.example.breezil.firebasesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchText;
    private ImageButton mSearchbtn;

    private RecyclerView mList;
    private DatabaseReference mContentRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchText = (EditText) findViewById(R.id.searchTextView);
        mSearchbtn = (ImageButton) findViewById(R.id.searchBtn);
        mList = (RecyclerView) findViewById(R.id.listView);
        mList.setHasFixedSize(true);
        mList.setLayoutManager( new LinearLayoutManager(this));

        mContentRef = FirebaseDatabase.getInstance().getReference().child("Content");

        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchText.getText().toString();
                firebaseSearch(searchText);
            }
        });



    }

    private void firebaseSearch(String searchText) {


        Query searchQuery = mContentRef.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Search, ContentViewHolder> fireAdapter = new FirebaseRecyclerAdapter<Search, ContentViewHolder>(
                Search.class,
                R.layout.item_layout,
                ContentViewHolder.class,
                mContentRef


        ) {
            @Override
            protected void populateViewHolder(ContentViewHolder viewHolder, Search model, int position) {

                viewHolder.setContents(model.getTitle(),model.getDesc());
            }
        };

        mList.setAdapter(fireAdapter);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ContentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setContents(String title,String desc){
            TextView titleText = (TextView) mView.findViewById(R.id.titleText);
            TextView descText = (TextView) mView.findViewById(R.id.DescText);

            titleText.setText(title);
            descText.setText(desc);
        }


    }
}
