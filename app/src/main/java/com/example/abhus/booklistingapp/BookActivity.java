package com.example.abhus.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    public static final String LOG_TAG = BookActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    private static final String BOOK_API = "https://www.googleapis.com/books/v1/volumes?q=the&orderBy=newest&maxResults=10";
    private BookAdapter mAdapter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setAdapter(mAdapter);
        mTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mTextView);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Book currentBook = mAdapter.getItem(i);
                Uri bookUri = Uri.parse(currentBook.getmUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(intent);
            }
        });
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            mTextView.setText("!!!NO INTERNET CONNECTION!!!");
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        return  new BookLoader(this, BOOK_API);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
