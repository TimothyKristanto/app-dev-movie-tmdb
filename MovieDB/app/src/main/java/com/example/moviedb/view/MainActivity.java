package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MoviesViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private Button btnSubmitMain;
    private MoviesViewModel moviesViewModel;
    private TextView txtShowDataMain;
    private TextInputLayout txtInputMovieIdMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVar();
        initClickListener();
    }

    private void initVar(){
        btnSubmitMain = findViewById(R.id.btnSubmitMain);
        txtShowDataMain = findViewById(R.id.txtShowDataMain);
        moviesViewModel = new ViewModelProvider(MainActivity.this).get(MoviesViewModel.class);
        txtInputMovieIdMain = findViewById(R.id.txtInputMovieIdMain);
    }

    private void initClickListener(){
        btnSubmitMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieId = txtInputMovieIdMain.getEditText().getText().toString();

                if(!TextUtils.isEmpty(movieId)){
                    moviesViewModel.getMovieById(movieId);
                    moviesViewModel.getMovieByIdResult().observe(MainActivity.this, showGetMoviesByIdResult);
                }else{
                    Toast.makeText(MainActivity.this, "Movie ID tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Observer<Movies> showGetMoviesByIdResult = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if(movies != null){
                String title = movies.getTitle();
                txtShowDataMain.setText(title);
            }else{
                txtShowDataMain.setText("Movie is not found!");
            }
        }
    };
}