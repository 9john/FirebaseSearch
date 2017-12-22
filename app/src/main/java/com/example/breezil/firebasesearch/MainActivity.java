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

    //firebase
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

        //firebase database reference of content we are search
        mContentRef = FirebaseDatabase.getInstance().getReference().child("Content");

        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the text from the edit text as string into a variable searchText
                String searchText = mSearchText.getText().toString();

                //pass the variable search text in the method searchText
                firebaseSearch(searchText);
            }
        });



    }


    //here we declear a method to carryout the search and display it in recycler view
    //using firebase ui recycler adapater
    private void firebaseSearch(String searchText) {


        //declear a firebase query to retrieve the search text,
        //start at the letter typed and end at the letter typed
        //
        Query searchQuery = mContentRef.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");

        /*
        * FirebaseRecycler add accept the model claa and view holder class
        * also we pass in four arguments:
        * the model class , the single item layout, the view Holder class and the search query
         */
        FirebaseRecyclerAdapter<Search, ContentViewHolder> fireAdapter = new FirebaseRecyclerAdapter<Search, ContentViewHolder>(
                Search.class,
                R.layout.item_layout,
                ContentViewHolder.class,
                searchQuery


        ) {
            @Override
            protected void populateViewHolder(ContentViewHolder viewHolder, Search model, int position) {

                viewHolder.setContents(model.getTitle(),model.getDesc());
            }
        };

        mList.setAdapter(fireAdapter);
    }


    //View holder class
    //we set the contents to the view widget

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
