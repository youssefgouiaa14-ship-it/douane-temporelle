package com.example.DouaneTemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ResultActivity extends AppCompatActivity {
    private LinearLayout layoutRacine;
    private TextView txtSymbole;
    private TextView txtMessage;
    private TextView txtDetail;
    private Button btnNouveau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        layoutRacine = findViewById(R.id.layout_racine_resultat_yg);
        txtSymbole = findViewById(R.id.txt_symbole_yg);
        txtMessage = findViewById(R.id.txt_message_yg);
        txtDetail = findViewById(R.id.txt_detail_yg);
        btnNouveau = findViewById(R.id.btn_nouveau_yg);

        Intent intent = getIntent();
        boolean approuve = intent.getBooleanExtra(
                ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.VERDICT), false);
        String nom = intent.getStringExtra(
                ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.NOM_VOYAGEUR));

        if (nom == null) {
            nom = getString(R.string.voyageur_inconnu);
        }

        if (approuve) {
            layoutRacine.setBackgroundColor(ContextCompat.getColor(this, R.color.vert_approuve));
            txtSymbole.setText(R.string.symbole_approuve);
            txtMessage.setText(getString(R.string.message_approuve, nom));
            txtDetail.setText(R.string.detail_approuve);
        } else {
            layoutRacine.setBackgroundColor(ContextCompat.getColor(this, R.color.rouge_refuse));
            txtSymbole.setText(R.string.symbole_refuse);
            txtMessage.setText(getString(R.string.message_refuse, nom));
            txtDetail.setText(R.string.detail_refuse);
        }

        btnNouveau.setOnClickListener(v -> nouveauVoyageur());
    }

    private void nouveauVoyageur() {
        Intent retour = new Intent(ResultActivity.this, MainActivity.class);

        retour.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(retour);
        finish();
    }
}
