package com.example.DouaneTemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edtNom;
    private EditText edtNaissance;
    private EditText edtDestination;
    private RadioGroup rgMotif;
    private Button btnInspecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        edtNom = findViewById(R.id.edt_nom_yg);
        edtNaissance = findViewById(R.id.edt_naissance_yg);
        edtDestination = findViewById(R.id.edt_destination_yg);
        rgMotif = findViewById(R.id.rg_motif_yg);
        btnInspecter = findViewById(R.id.btn_inspecter_yg);

        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    private void inspecterProfil() {
        String nom = edtNom.getText().toString().trim();
        String texteNaissance = edtNaissance.getText().toString().trim();
        String texteDestination = edtDestination.getText().toString().trim();

        boolean formulaireValide = true;
        if (nom.isEmpty()) {
            edtNom.setError(getString(R.string.erreur_nom_vide));
            formulaireValide = false;
        }
        if (texteNaissance.isEmpty()) {
            edtNaissance.setError(getString(R.string.erreur_naissance_vide));
            formulaireValide = false;
        }
        if (texteDestination.isEmpty()) {
            edtDestination.setError(getString(R.string.erreur_destination_vide));
            formulaireValide = false;
        }

        if (!formulaireValide) {
            return;
        }

        int anneeNaissance = Integer.parseInt(texteNaissance);
        int anneeDestination = Integer.parseInt(texteDestination);

        RadioButton rbChoisi = findViewById(rgMotif.getCheckedRadioButtonId());
        String motif = rbChoisi.getText().toString();

        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

        intent.putExtra(ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.NOM_VOYAGEUR), nom);
        intent.putExtra(ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.ANNEE_NAISSANCE), anneeNaissance);
        intent.putExtra(ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.ANNEE_DESTINATION), anneeDestination);
        intent.putExtra(ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.MOTIF_VOYAGE), motif);

        startActivity(intent);
    }
}
