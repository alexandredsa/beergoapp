package br.com.beergo.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.beergo.R;
import br.com.beergo.content.UserPreferences;
import br.com.beergo.domain.dto.UserDetail;
import br.com.beergo.rest.UserRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodeActivity extends AppCompatActivity {
    private EditText txtCode;
    private ProgressDialog progress;
    private UserRestService userRestService;
    private UserPreferences userPreferences;
    private UserDetail user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        txtCode = (EditText) findViewById(R.id.code);
        userPreferences = new UserPreferences(PreferenceManager.getDefaultSharedPreferences(this), new Gson());
        userRestService = new UserRestService();
        user = userPreferences.getUser();

    }

    public void send(View v) {
        progress = ProgressDialog.show(this, "Aguarde",
                "Validando código", true);
        txtCode.setText(txtCode.getText().toString().toUpperCase());
        userRestService.sendCode(user, txtCode.getText().toString(), new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                UserDetail newUser = response.body();

                if (response.isSuccessful()) {
                    Toast.makeText(CodeActivity.this, user.getSummary(newUser), Toast.LENGTH_LONG).show();
                    userPreferences.saveUser(newUser);
                    txtCode.setText("");
                } else {
                    Toast.makeText(CodeActivity.this,
                            "Falha ao submeter código informado.\nVerifique se o código foi digitado corretamente.",
                            Toast.LENGTH_SHORT).show();
                }


                progress.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(CodeActivity.this,
                        "Falha ao submeter código informado.\nVerifique se o código foi digitado corretamente.",
                        Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
}
