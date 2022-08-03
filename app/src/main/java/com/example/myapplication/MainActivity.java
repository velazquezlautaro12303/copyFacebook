package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establece el color de la barra de estado
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.background, getTheme()));

        // Establezco a la actividad el layout
        setContentView(R.layout.main);

        // setSupportActionBar(findViewById(R.id.toolbar));

        getTweets();
    }

    public void watchMessageTweet(View view){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("IDTweet", 2);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.LogOut) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTweets(){
        String url = "https://instagram20220219095914.azurewebsites.net/api/UsersAPI/GetUser";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray dataAPI = new JSONArray(response);

                 for (int i = 0; i < dataAPI.length(); i++) {
                    addTweet(dataAPI.getJSONObject(i).getString("NameUser"),
                            dataAPI.getJSONObject(i).getString("Date"),
                            dataAPI.getJSONObject(i).getString("Description"),
                            dataAPI.getJSONObject(i).getInt("CountCommnet"),
                            dataAPI.getJSONObject(i).getInt("CountShare"),
                            dataAPI.getJSONObject(i).getInt("CountLike"),
                            dataAPI.getJSONObject(i).getInt("ID"));
                }
            } catch (JSONException e) {
                 // Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                 finish();
            }
        }, error -> {
             // Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
             finish();
        });
        // Envia la peticion
        Volley.newRequestQueue(this).add(getRequest);
    }

    private void addTweet(String NameUser, String Date, String Description, int CountComment, int CountShare, int CountLike, int id)
    {
        // LinearLayoutPrincipal
        LinearLayout linearLayoutMain = findViewById(R.id.LayoutMain);

        // Creó LinearLayoutTweet
        LinearLayout linearLayoutTweet = new LinearLayout(this);
        LinearLayout.LayoutParams paramsTweet = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTweet.setMargins(0, 20, 0, 20);
        linearLayoutTweet.setLayoutParams( paramsTweet );
        linearLayoutTweet.setOrientation(LinearLayout.VERTICAL);
        linearLayoutTweet.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundTweet));
        linearLayoutTweet.setPadding(0, 10, 0, 0);

        // Creó LinearLayoutHeader
        LinearLayout linearLayoutHeader = new LinearLayout(this);
        linearLayoutHeader.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) );
        linearLayoutHeader.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutHeader.setPaddingRelative(20, 0, 0, 0);

        // Creo LinearLayoutNameUser
        LinearLayout linearLayoutNameUser = new LinearLayout(this);
        linearLayoutNameUser.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) );
        linearLayoutNameUser.setOrientation(LinearLayout.VERTICAL);
        linearLayoutNameUser.setPadding(20, 20, 20, 20);
        linearLayoutNameUser.setGravity(Gravity.CENTER | Gravity.START);

        // LinearLayoutButtons
        LinearLayout linearLayoutButtons = new LinearLayout(this);
        linearLayoutButtons.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) );
        linearLayoutButtons.setOrientation(LinearLayout.HORIZONTAL);

        // Creo LinearLayoutCountLike
        LinearLayout linearLayoutCountLike = new LinearLayout(this);
        linearLayoutCountLike.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) );
        linearLayoutCountLike.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutCountLike.setGravity(Gravity.START);
        linearLayoutCountLike.setPadding(10, 0, 10, 0);

        // Creo LinearLayoutCountLikeShare
        LinearLayout linearLayoutCountLikeShare = new LinearLayout(this);
        linearLayoutCountLikeShare.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) );
        linearLayoutCountLikeShare.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutCountLikeShare.setGravity(Gravity.END);
        linearLayoutCountLikeShare.setPadding(10, 0, 10, 0);

        // Creo el texto Descripcion
        TextView textDescription = new TextView(this);
        textDescription.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textDescription.setId(R.id.idDescription);
        textDescription.setText(Description);
        textDescription.setTextColor(Color.WHITE);
        textDescription.setTextSize(15);
        textDescription.setPadding(20,20,20,20);

        // Creo el texto NameUser
        TextView textNameUser = new TextView(this);
        textNameUser.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textNameUser.setText(NameUser);
        textNameUser.setTextColor(Color.WHITE);

        // Creo el texto DateTime
        TextView textDateTime = new TextView(this);
        textDateTime.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textDateTime.setText(Date);
        textDateTime.setTextColor(Color.WHITE);

        // Creo el texto countLike
        TextView textCountLike = new TextView(this);
        textCountLike.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        String countLike = CountLike + " Me Gusta";
        textCountLike.setText(countLike);
        textCountLike.setTextColor(Color.WHITE);
        textCountLike.setPadding(10, 0, 10, 0);

        // Creo el texto countComment
        TextView textCountComment = new TextView(this);
        textCountComment.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        String countCommentString = CountComment + " comentarios";
        textCountComment.setText(countCommentString);
        textCountComment.setTextColor(Color.WHITE);
        textCountComment.setPadding(10, 0, 10, 0);

        // Creo el texto countShare
        TextView textCountShare = new TextView(this);
        textCountShare.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        String countShareString = CountShare + " veces compartido";
        textCountShare.setText(countShareString);
        textCountShare.setTextColor(Color.WHITE);
        textCountShare.setPadding(10, 0, 10, 0);

        // Creo la galeria de fotos del tweet
        ImageView photoView = new ImageView(this);
        photoView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700) );
        photoView.setContentDescription(getText(R.string.Description));
        LoadUrlPhotos(photoView, id);

        // Creo la Imagen de Perfil del tweet
        CircleImageView photoPerfil = new CircleImageView(this);
        photoPerfil.setLayoutParams( new LinearLayout.LayoutParams(100, 100) );
        photoPerfil.setImageResource(R.drawable.photo);

        // ButtonLike
        Button buttonLike = new Button(this, null, android.R.attr.buttonBarButtonStyle);
        buttonLike.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        buttonLike.setText(R.string.Like);
        buttonLike.setTextColor(Color.WHITE);
        buttonLike.setFocusedByDefault(true);

        // ButtonComment
        Button buttonComment = new Button(this, null, android.R.attr.buttonBarButtonStyle);
        buttonComment.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        buttonComment.setText(R.string.Comment);
        buttonComment.setTextColor(Color.WHITE);
        buttonComment.setFocusedByDefault(true);
        // Code here executes on main thread after user presses button
        buttonComment.setOnClickListener(this::watchMessageTweet);

        // ButtonShare
        Button buttonShare = new Button(this, null, android.R.attr.buttonBarButtonStyle);
        buttonShare.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        buttonShare.setText(R.string.Share);
        buttonShare.setTextColor(Color.WHITE);
        buttonShare.setFocusedByDefault(true);

        // Creo lineDivider
        View lineDivider = new View(this, null, android.R.attr.listDivider);
        LinearLayout.LayoutParams paramsLineDivider = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        paramsLineDivider.setMargins(20, 10,20, 0);
        lineDivider.setLayoutParams( paramsLineDivider );
        lineDivider.setBackgroundColor(getColor(R.color.whiteLineDivider));

        // Añado los botones al layout
        linearLayoutButtons.addView(buttonLike);
        linearLayoutButtons.addView(buttonComment);
        linearLayoutButtons.addView(buttonShare);

        // Añado los textos al linearLayoutNameUser
        linearLayoutNameUser.addView(textNameUser);
        linearLayoutNameUser.addView(textDateTime);

        // Añado imagen de perfil a linearLayoutHeader
        linearLayoutHeader.addView(photoPerfil);
        linearLayoutHeader.addView(linearLayoutNameUser);

        // Añado description de la imagen
        linearLayoutCountLike.addView(textCountLike);
        linearLayoutCountLike.addView(linearLayoutCountLikeShare);

        // Añado texts Count Like, Share a linearLayoutCountLikeShare
        linearLayoutCountLikeShare.addView(textCountComment);
        linearLayoutCountLikeShare.addView(textCountShare);

        // añadó las vistas en linearLayoutTweet
        linearLayoutTweet.addView(linearLayoutHeader);
        linearLayoutTweet.addView(textDescription);
        linearLayoutTweet.addView(photoView);
        linearLayoutTweet.addView(linearLayoutCountLike);
        linearLayoutTweet.addView(lineDivider);
        linearLayoutTweet.addView(linearLayoutButtons);

        // añadó los tweets al LinearLayoutPrincipal
        linearLayoutMain.addView(linearLayoutTweet);
    }

    public void LoadUrlPhotos(ImageView photoView, int id)
    {
        String url = "https://instagram20220219095914.azurewebsites.net/api/UsersAPI/GetPhotos/" + id;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray dataAPI = new JSONArray(response);
                if(dataAPI.length() > 0){
                    Picasso.get().load("https://instagram20220219095914.azurewebsites.net/" + dataAPI.getString(0)).error(R.drawable.ic_launcher_foreground).into(photoView);
                }
            } catch (JSONException e) {
                finish();
            }
        }, error -> {
            finish();
        });
        // Envia la peticion
        Volley.newRequestQueue(this).add(getRequest);
    }

}
