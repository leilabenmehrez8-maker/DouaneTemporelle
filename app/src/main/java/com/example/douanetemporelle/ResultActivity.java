
package com.example.douanetemporelle;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 1. Récupération des composants XML
        LinearLayout layoutResultat =
                findViewById(R.id.layout_resultat_gh);

        TextView txtVerdict =
                findViewById(R.id.txt_verdict_gh);

        TextView txtMessage =
                findViewById(R.id.txt_message_gh);

        Button btnNouveau =
                findViewById(R.id.btn_nouveau_gh);

        // 2. Réception des données
        Intent intent = getIntent();

        String matricule = IntentKeyHelper.MATRICULE;

        String cleNom = IntentKeyHelper.construireCle(
                matricule, "NOM_VOYAGEUR"
        );

        String cleVerdict = IntentKeyHelper.construireCle(
                matricule, "VERDICT"
        );

        // 3. Vérification des données reçues
        if (!intent.hasExtra(cleNom)
                || !intent.hasExtra(cleVerdict)) {

            Toast.makeText(
                    this,
                    R.string.message_missing,
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        // 4. Extraction des informations
        String nom = intent.getStringExtra(cleNom);

        boolean approuve =
                intent.getBooleanExtra(cleVerdict, false);

        // 5. Vérification du nom
        if (nom == null || nom.trim().isEmpty()) {

            Toast.makeText(
                    this,
                    R.string.message_invalid_name,
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        // 6. Affichage du verdict
        if (approuve) {

            // Voyage approuvé : vert
            layoutResultat.setBackgroundColor(
                    Color.rgb(27, 125, 67)
            );

            txtVerdict.setText(
                    R.string.verdict_approved
            );

            txtMessage.setText(
                    getString(R.string.message_approved, nom)
            );

        } else {

            // Voyage refusé : rouge
            layoutResultat.setBackgroundColor(
                    Color.rgb(185, 28, 28)
            );

            txtVerdict.setText(
                    R.string.verdict_refused
            );

            txtMessage.setText(
                    getString(R.string.message_refused, nom)
            );
        }

        // 7. Bouton nouveau voyageur
        btnNouveau.setOnClickListener(v -> {

            Intent retour = new Intent(
                    ResultActivity.this,
                    MainActivity.class
            );

            retour.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(retour);
            finish();
        });
    }
}