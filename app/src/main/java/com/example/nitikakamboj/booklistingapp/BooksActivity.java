package com.example.nitikakamboj.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import java.util.ArrayList;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {
    private BooksAdapter mAdapter;
    private TextView mEmptyTextView;
    private EditText mEditText;
    public String searchString;

    public static final String LOG_TAG = BooksActivity.class.getName();
    public static final int LOADER_ID = 1;
    public static final String BOOKS_URL =  "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_books);
        mEditText=(EditText)findViewById(R.id.search_keyword);
        Button searchButton=(Button)findViewById(R.id.search_button);
        final ListView booksListView = (ListView) findViewById(R.id.listView);
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyTextView);
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());
        booksListView.setAdapter(mAdapter);
      // mAdapter.notifyDataSetChanged();
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Books currentBook = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentBook.getmUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.clearFocus();
               //mAdapter.notifyDataSetChanged();

                String searchQuery = mEditText.getText().toString().replaceAll(" ", "+");
                if (searchQuery != null && !searchQuery.equals("")) {

                    searchString = BOOKS_URL + searchQuery;
                    ConnectivityManager connMangr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMangr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        getLoaderManager().restartLoader(LOADER_ID,null,BooksActivity.this);
                    } else {
                        View loadingIndicator = findViewById(R.id.loading_indicator);
                        loadingIndicator.setVisibility(View.GONE);
                        mEmptyTextView.setText("No Internet Connection");
                    }

                }
            }
        });

    }
    public Loader<List<Books>> onCreateLoader(int i,Bundle bundle)
    {
     return  new BooksLoader(this,searchString);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
           mAdapter.clear();
        mEmptyTextView.setText(R.string.noDataFound);
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        mAdapter.clear();
    }
}