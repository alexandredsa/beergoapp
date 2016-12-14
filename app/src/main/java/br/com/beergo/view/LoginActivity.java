package br.com.beergo.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.beergo.R;
import br.com.beergo.content.UserPreferences;
import br.com.beergo.domain.dto.UserCredentials;
import br.com.beergo.domain.dto.UserDetail;
import br.com.beergo.rest.AuthRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText user, password;
    private ProgressDialog progress;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        userPreferences = new UserPreferences(PreferenceManager.getDefaultSharedPreferences(this), new Gson());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signup:
                callSignUpActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callSignUpActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    public void send(View v) {
        progress = ProgressDialog.show(this, "Aguarde",
                "Efetuando login", true);
        new AuthRestService().login(new UserCredentials(user.getText().toString(), password.getText().toString()), new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (response.isSuccessful()) {
                    userPreferences.saveUser(response.body());
                    callMapsActivity();
                } else
                    Toast.makeText(LoginActivity.this, "Usuário e/ou senha inválido(s).", Toast.LENGTH_LONG).show();

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Usuário e/ou senha inválido(s).", Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });
    }

    private void callMapsActivity() {
        Intent i = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(i);
        finish();
    }
}
