package com.tyler.book_monitor.ui.main;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tyler.book_monitor.data.models.Author;
import com.tyler.book_monitor.data.models.Book;
import com.tyler.book_monitor.utils.Constants;

import java.util.List;
import java.util.Vector;

public class MainModel implements MainContract.Model {

    private final FirebaseFirestore db;

    public MainModel() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void loadContent(OnLoadContentListener listener) {
        Task<QuerySnapshot> task1 = db.collection("books")
                .whereEqualTo("isPublished", true)
                .limit(Constants.AUTHOR_LIMIT)
                .get();

        Task<QuerySnapshot> task2 = db.collection("authors")
                .limit(Constants.BOOK_LIMIT)
                .get();

        Tasks.whenAllSuccess(task1, task2).addOnSuccessListener(objects -> {
            List<Book> books = new Vector<>();
            ((QuerySnapshot)objects.get(0)).getDocuments().forEach(document -> {
                books.add(new Book(
                        document.getId(),
                        document.get("name").toString(),
                        document.get("author").toString(),
                        document.get("avatar").toString(),
                        document.get("introduction").toString(),
                        (List<String>)document.get("categories")
                ));
            });

            List<Author> authors = new Vector<>();
            ((QuerySnapshot)objects.get(1)).getDocuments().forEach(document -> {
                authors.add(new Author(
                        document.getId(),
                        document.get("name").toString(),
                        document.get("avatar").toString(),
                        document.get("introduction").toString()
                ));
            });

            listener.onSuccess(books, authors);
        });
    }
}
