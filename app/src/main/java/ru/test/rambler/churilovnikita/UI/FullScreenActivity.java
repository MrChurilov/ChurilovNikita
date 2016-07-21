package ru.test.rambler.churilovnikita.UI;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.test.rambler.churilovnikita.R;

public class FullScreenActivity extends AppCompatActivity {
    @BindView(R.id.img_full_photo)
    ImageView mFullImgV;

    private final static String EXTRA_URL_PHOTO = "ru.test.rambler.churilovnikita.fullScreen_url_photo";

    public static Intent newInstance(Context context, String urlPhoto) {
        Intent i = new Intent(context, FullScreenActivity.class);
        i.putExtra(EXTRA_URL_PHOTO, urlPhoto);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        ButterKnife.bind(this);

        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_URL_PHOTO)).into(mFullImgV);
    }
}
