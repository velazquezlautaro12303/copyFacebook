package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        // Obtengo los datos que se pasan de la actividad main
        Bundle datos = getIntent().getExtras();
        int idTweet = datos.getInt("IDTweet");

        // Establece el color de la barra de estado
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.background, getTheme()));
        LoadComment(idTweet);
    }

    public enum TypeComment{
        MYCOMMENT, OTHERS
    }

    private void createComment(String text, TypeComment typeComment){
        // LinearLayoutPrincipal
        LinearLayout linearLayoutMain = findViewById(R.id.LayoutComments);

        // Creó LinearLayoutCommentPhoto
        LinearLayout linearLayoutCommentPhoto = new LinearLayout(this);
        linearLayoutCommentPhoto.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutCommentPhoto.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) );
        linearLayoutCommentPhoto.setPaddingRelative(20,20,20,20);

        // Creo la Imagen de Perfil del tweet
        CircleImageView photoPerfil = new CircleImageView(this);
        photoPerfil.setLayoutParams( new LinearLayout.LayoutParams(110, 110) );
        photoPerfil.setImageResource(R.drawable.photo);

        // Creo el texto commentario
        TextView textViewComment = new TextView(this);
        textViewComment.setPadding(10,10,10,10);
        textViewComment.setBackgroundColor(getColor(R.color.purple_700));
        textViewComment.setText(text);
        LinearLayout.LayoutParams paramsTweet = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTweet.setMarginStart(20);
        textViewComment.setLayoutParams(paramsTweet);
        textViewComment.setTextColor(getColor(R.color.white));

        if(typeComment == TypeComment.MYCOMMENT){
            // Si es mi comentario ubico los componentes del otro lado
            linearLayoutCommentPhoto.setRotationY(180);
            photoPerfil.setRotationY(180);
            textViewComment.setRotationY(180);
        }

        // Add photo y comentario
        linearLayoutCommentPhoto.addView(photoPerfil);
        linearLayoutCommentPhoto.addView(textViewComment);

        // Add linearComment a LinearMain
        linearLayoutMain.addView(linearLayoutCommentPhoto);
    }

    public void LoadComment(int id)
    {
        String url = "https://instagram20220219095914.azurewebsites.net/api/UsersAPI/GetComments/" + id;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray dataAPI = new JSONArray(response);
                for (int i = 0; i < dataAPI.length(); i++) {
                    createComment(dataAPI.getString(i), TypeComment.OTHERS);
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
