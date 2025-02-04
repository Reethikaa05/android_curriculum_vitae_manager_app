package com.example.cv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final Context context;
    private final List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textViewTitle.setText(book.getTitle());
        holder.textViewPublisher.setText(book.getPublisher());
        holder.textViewMonthYear.setText(book.getMonthYear());
        holder.textViewEdition.setText(book.getEdition());
        holder.textViewIsbn.setText(book.getIsbnNo());
        holder.textViewPages.setText(book.getNoOfPages());
        holder.textViewAuthor1.setText(book.getAuthor1());
        holder.textViewAuthor2.setText(book.getAuthor2());
        holder.textViewAuthor3.setText(book.getAuthor3());

        // Set onClickListener to view details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewBookActivity.class);
            intent.putExtra("BOOK_ID", book.getBookId()); // Assuming you have a getter for bookId
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewPublisher;
        TextView textViewMonthYear;
        TextView textViewEdition;
        TextView textViewIsbn;
        TextView textViewPages;
        TextView textViewAuthor1;
        TextView textViewAuthor2;
        TextView textViewAuthor3;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.book_title);
            textViewPublisher = itemView.findViewById(R.id.book_publisher);
            textViewMonthYear = itemView.findViewById(R.id.book_month_year);
            textViewEdition = itemView.findViewById(R.id.book_edition);
            textViewIsbn = itemView.findViewById(R.id.book_isbn);
            textViewPages = itemView.findViewById(R.id.book_pageno);
            textViewAuthor1 = itemView.findViewById(R.id.book_author1);
            textViewAuthor2 = itemView.findViewById(R.id.book_author2);
            textViewAuthor3 = itemView.findViewById(R.id.book_author3);
        }
    }

}
