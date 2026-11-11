package com.example.DouaneTemporelle;

public final class ClesIntent {

    public static final String MATRICULE = "2201";

    public static final String NOM_VOYAGEUR = "NOM_VOYAGEUR";
    public static final String ANNEE_NAISSANCE = "ANNEE_NAISSANCE";
    public static final String ANNEE_DESTINATION = "ANNEE_DESTINATION";
    public static final String MOTIF_VOYAGE = "MOTIF_VOYAGE";
    public static final String VERDICT = "VERDICT";

    private ClesIntent() {
    }

    public static String construireCle(String matricule, String nomCle) {
        return matricule + "_" + nomCle;
    }
}
