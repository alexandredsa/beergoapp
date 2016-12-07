package br.com.beergo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.beergo.R;
import br.com.beergo.domain.dto.UserCredentials;
import br.com.beergo.domain.dto.UserDetail;
import br.com.beergo.rest.AuthRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
    }

    public void send(View v) {
        new AuthRestService().login(new UserCredentials(user.getText().toString(), password.getText().toString()), new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                callMapsActivity();
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Usuário ou senha inválido.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMapsActivity() {
        Intent i = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(i);
        finish();
    }
}
