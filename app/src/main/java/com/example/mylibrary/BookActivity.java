package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "BookId";

    private TextView txtBookName, authorTxt, txtPages, txtDescription;
    private Button btnAddToCurrentlyReading, btnAddToWantToRead, btnAddToAlreadyRead, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        String longDescription = "The taxi’s radio was tuned to a classical FM broadcast. Janáček’s " +
//                "Sinfonietta—probably not the ideal music to hear in a taxi caught in traffic. The middle-aged " +
//                "driver didn’t seem to be listening very closely, either. With his mouth clamped shut, he stared " +
//                "straight ahead at the endless line of cars stretching out on the elevated expressway, like a " +
//                "veteran fisherman standing in the bow of his boat, reading the ominous confluence of two currents. " +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music." +
//                "Aomame settled into the broad back seat, closed her eyes, and listened to the music.";
//
//        //TODO: Get the data from recycler view in here
//        Book book = new Book(1,"1Q84","Haruki Murakami",1350,"https://vnwriter.net/wp-content/uploads/2017/11/sach-1q84.jpg",
//                "A work of maddening brilliance",longDescription);

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            Log.d("Intent","onBindViewHolder: Called, bookId = "+bookId);
            if(bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);

                }
            }
        }
    }

    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existFavoriteBooks = false;

        for(Book b: favoriteBooks) {
            if(b.getId() == book.getId()) {
                existFavoriteBooks = true;
            }
        }

        if(existFavoriteBooks) {
            btnAddToFavorite.setEnabled(false);
        }else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addFavoriteBook(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        Intent intent = new Intent(BookActivity.this, FavoriteBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(final Book book) {
        ArrayList<Book> currentReadBook = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existCurrentReadBooks = false;

        for(Book b: currentReadBook) {
            if(b.getId() == book.getId()) {
                existCurrentReadBooks = true;
            }
        }

        if(existCurrentReadBooks) {
            btnAddToCurrentlyReading.setEnabled(false);
        }else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addCurrentlyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existWantToReadBooks = false;

        for(Book b: wantToReadBooks) {
            if(b.getId() == book.getId()) {
                existWantToReadBooks = true;
            }
        }

        if(existWantToReadBooks) {
            btnAddToWantToRead.setEnabled(false);
        }else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    /**
     * Enable and Disable button
     * Add the book to Already Read Book ArrayList
     * @param book
     */

    private void handleAlreadyRead(final Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for(Book b: alreadyReadBooks) {
            if(b.getId() == book.getId()) {
                existInAlreadyReadBooks = true;
            }
        }

        if(existInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false);
        }else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        authorTxt.setText(book.getAuthour());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);

    }

    private void initViews() {
        authorTxt = findViewById(R.id.authorTxt);
        txtBookName = findViewById(R.id.txtBookName);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);

        bookImage = findViewById(R.id.bookImage);
    }
}