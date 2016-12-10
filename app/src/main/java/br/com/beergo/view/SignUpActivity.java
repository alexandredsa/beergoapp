package br.com.beergo.view;

import android.app.ProgressDialog;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText user, password, confirmPassword;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
    }

    public void send(View v) {
        String pass = password.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (!pass.equals(confirmPass)) {
            Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_LONG).show();
            return;
        }

        UserCredentials credentials = new UserCredentials(user.getText().toString(), pass);

        progress = ProgressDialog.show(this, "Aguarde",
                "Cadastrando usuário", true);
        new AuthRestService().signUp(credentials, new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                Toast.makeText(SignUpActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
                finish();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                progress.dismiss();
                finish();
            }
        });
    }
}
