package com.tyler.book_monitor.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tyler.book_monitor.R;
import com.tyler.book_monitor.data.models.Book;

import java.io.File;
import java.util.List;

public class PersonalBookAdapter extends RecyclerView.Adapter<PersonalBookAdapter.ViewHolder> {

    public interface IPersonalBookClick {
        void onRemoveClick(int position, Book book);
        void onViewClick(String bookId);
    }

    private final LayoutInflater inflater;
    private final List<Book> books;
    private final IPersonalBookClick personalBookClick;

    public PersonalBookAdapter(Context context, List<Book> books, IPersonalBookClick personalBookClick) {
        this.inflater = LayoutInflater.from(context);
        this.books = books;
        this.personalBookClick = personalBookClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_personal_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalBookAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(books.get(position).getTitle());
        holder.tvAuthor.setText(books.get(position).getAuthor());

        if (books.get(position).getCover().contains("http")) {
            Picasso.get().load(books.get(position).getCover()).into(holder.ivCover);
        } else {
            Picasso.get().load(new File(books.get(position).getCover())).into(holder.ivCover);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvAuthor;
        private final ImageView ivCover;
        private final ImageButton ibRemove;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            ivCover = itemView.findViewById(R.id.iv_cover);
            ibRemove = itemView.findViewById(R.id.ib_remove);

            itemView.setOnClickListener(v -> {
                if (personalBookClick != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        personalBookClick.onViewClick(books.get(getAdapterPosition()).getId());
                    }
                }
            });

            ibRemove.setOnClickListener(v -> {
                if (personalBookClick != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        personalBookClick.onRemoveClick(getAdapterPosition(), books.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
