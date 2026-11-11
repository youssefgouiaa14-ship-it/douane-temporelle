package com.example.DouaneTemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class VerificationActivity extends AppCompatActivity {
    private static final int LONGUEUR_MRZ = 30;

    private TextView txtNom;
    private TextView txtAge;
    private TextView txtTrajet;
    private TextView txtMotif;
    private TextView txtAvertissement;
    private TextView txtMrz;
    private Button btnApprouver;
    private Button btnRefuser;

    private String nomVoyageur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verification);

        txtNom = findViewById(R.id.txt_nom_passeport_yg);
        txtAge = findViewById(R.id.txt_age_passeport_yg);
        txtTrajet = findViewById(R.id.txt_trajet_passeport_yg);
        txtMotif = findViewById(R.id.txt_motif_passeport_yg);
        txtAvertissement = findViewById(R.id.txt_avertissement_yg);
        txtMrz = findViewById(R.id.txt_mrz_yg);
        btnApprouver = findViewById(R.id.btn_approuver_yg);
        btnRefuser = findViewById(R.id.btn_refuser_yg);

        Intent intent = getIntent();

        nomVoyageur = intent.getStringExtra(
                ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.NOM_VOYAGEUR));
        int anneeNaissance = intent.getIntExtra(
                ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.ANNEE_NAISSANCE), 0);
        int anneeDestination = intent.getIntExtra(
                ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.ANNEE_DESTINATION), 0);
        String motif = intent.getStringExtra(
                ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.MOTIF_VOYAGE));

        if (nomVoyageur == null) {
            nomVoyageur = getString(R.string.voyageur_inconnu);
        }
        if (motif == null) {
            motif = "";
        }

        int ageTemporel = anneeDestination - anneeNaissance;

        txtNom.setText(nomVoyageur);
        txtMotif.setText(motif);
        txtTrajet.setText(getString(R.string.valeur_trajet, anneeNaissance, anneeDestination));
        if (ageTemporel < 0) {
            txtAge.setText(getString(R.string.valeur_age_negatif, ageTemporel));
        } else {
            txtAge.setText(getString(R.string.valeur_age, ageTemporel));
        }
        txtMrz.setText(construireZoneMrz(nomVoyageur, anneeNaissance, anneeDestination, ageTemporel));

        boolean risqueParadoxe = motif.equals(getString(R.string.motif_modification))
                && estPremier(ageTemporel);
        if (risqueParadoxe) {
            btnApprouver.setEnabled(false);
            txtAvertissement.setText(getString(R.string.avertissement_paradoxe, ageTemporel));
            txtAvertissement.setVisibility(View.VISIBLE);
        }

        btnApprouver.setOnClickListener(v -> envoyerVerdict(true));
        btnRefuser.setOnClickListener(v -> envoyerVerdict(false));
    }

    private void envoyerVerdict(boolean approuve) {
        Intent intent = new Intent(VerificationActivity.this, ResultActivity.class);

        intent.putExtra(ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.VERDICT), approuve);
        intent.putExtra(ClesIntent.construireCle(ClesIntent.MATRICULE, ClesIntent.NOM_VOYAGEUR), nomVoyageur);

        startActivity(intent);
    }

    static boolean estPremier(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private String construireZoneMrz(String nom, int naissance, int destination, int age) {
        String nomMrz = nom.toUpperCase(Locale.ROOT).replace(' ', '<');
        String ligne1 = completerAvecChevrons("P<TMP<" + nomMrz);

        String ligne2 = completerAvecChevrons(naissance + "<" + destination + "<" + age);

        return ligne1 + "\n" + ligne2;
    }

    private String completerAvecChevrons(String texte) {
        StringBuilder sb = new StringBuilder(texte);
        while (sb.length() < LONGUEUR_MRZ) {
            sb.append('<');
        }
        return sb.substring(0, LONGUEUR_MRZ);
    }
}
