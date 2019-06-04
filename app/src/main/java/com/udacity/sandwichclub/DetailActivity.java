package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView aName,desc,origin,ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        aName = findViewById(R.id.also_known_tv);
        desc = findViewById(R.id.description_tv);
        origin = findViewById(R.id.origin_tv);
        ingredient = findViewById(R.id.ingredients_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        if(!sandwich.getImage().isEmpty())
        {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
        }
        else {
            Picasso.with(this).load(sandwich.getImage()).placeholder(R.mipmap.ic_launcher).into(ingredientsIv);
        }

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if(!sandwich.getAlsoKnownAs().isEmpty() && sandwich.getAlsoKnownAs().size()>0) {
            StringBuilder stringBuilder = new StringBuilder();
           stringBuilder.append(sandwich.getAlsoKnownAs().get(0));
            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(",");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));

            }
            aName.setText(stringBuilder.toString());
        }
        else
        {
            aName.setText("Data is not available");
        }
        if(desc.getText()!=null) {
            desc.setText(sandwich.getDescription());
        }
        else
        {
            desc.setText("Data is not available");
        }
        if(origin.getText()!=null) {
            origin.setText(sandwich.getPlaceOfOrigin());
        }
        else
        {

            origin.setText("Data is not available");

        }

        if(!sandwich.getIngredients().isEmpty() && sandwich.getIngredients().size()>0)
        {
            StringBuilder builder = new StringBuilder();
            builder.append(sandwich.getIngredients().get(0));
            for (int i =1;i<sandwich.getIngredients().size();i++) {
                builder.append(",");
                builder.append(sandwich.getIngredients().get(i));
            }
            ingredient.setText(builder.toString());
        }
        else
        {
            ingredient.setText("Data is not available");
        }



    }
}
