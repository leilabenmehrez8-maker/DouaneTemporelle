
package com.example.douanetemporelle;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText editNom;
    private EditText editNaissance;
    private EditText editDestination;
    private RadioGroup radioMotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ١ - ربط عناصر الواجهة بملف جافا
        editNom = findViewById(R.id.edit_nom_la);

        editNaissance =
                findViewById(R.id.edit_naissance_la);

        editDestination =
                findViewById(R.id.edit_destination_la);

        radioMotif =
                findViewById(R.id.radio_motif_la);

        Button btnInspecter =
                findViewById(R.id.btn_inspecter_la);

        // ٢ - تنفيذ عملية فحص الملف عند الضغط
        btnInspecter.setOnClickListener(v -> {
            inspecterProfil();
        });
    }

    // ٣ - قراءة بيانات المسافر والتحقق منها
    private void inspecterProfil() {

        String nom = editNom.getText()
                .toString().trim();

        String naissanceTexte =
                editNaissance.getText()
                        .toString().trim();

        String destinationTexte =
                editDestination.getText()
                        .toString().trim();

        // ٤ - التحقق من اسم المسافر
        if (nom.isEmpty()) {
            editNom.setError(
                    "Entrez le nom du voyageur"
            );
            editNom.requestFocus();
            return;
        }

        // ٥ - التحقق من سنة الميلاد
        if (naissanceTexte.isEmpty()) {
            editNaissance.setError(
                    "Entrez l'année de naissance"
            );
            editNaissance.requestFocus();
            return;
        }

        // ٦ - التحقق من سنة الوجهة
        if (destinationTexte.isEmpty()) {
            editDestination.setError(
                    "Entrez l'année de destination"
            );
            editDestination.requestFocus();
            return;
        }

        int naissance;
        int destination;

        // ٧ - تحويل السنوات إلى أعداد صحيحة
        try {

            naissance =
                    Integer.parseInt(naissanceTexte);

            destination =
                    Integer.parseInt(destinationTexte);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Année invalide",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // ٨ - التحقق من اختيار سبب السفر
        int idSelection =
                radioMotif.getCheckedRadioButtonId();

        if (idSelection == -1) {

            Toast.makeText(
                    this,
                    "Choisissez un motif",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // ٩ - تحديد السبب المختار
        String motif;

        if (idSelection ==
                R.id.radio_tourisme_la) {

            motif = "Tourisme";

        } else if (idSelection ==
                R.id.radio_modification_la) {

            motif = "Modification du passé";

        } else {

            motif = "Fuite";
        }

        // ١٠ - إنشاء انتقال صريح إلى النشاط الثاني
        Intent intent = new Intent(
                MainActivity.this,
                VerificationActivity.class
        );

        String matricule =
                IntentKeyHelper.MATRICULE;

        // ١١ - إرسال اسم المسافر
        intent.putExtra(
                IntentKeyHelper.construireCle(
                        matricule,
                        "NOM_VOYAGEUR"
                ),
                nom
        );

        // ١٢ - إرسال سنة الميلاد
        intent.putExtra(
                IntentKeyHelper.construireCle(
                        matricule,
                        "ANNEE_NAISSANCE"
                ),
                naissance
        );

        // ١٣ - إرسال سنة الوجهة
        intent.putExtra(
                IntentKeyHelper.construireCle(
                        matricule,
                        "ANNEE_DESTINATION"
                ),
                destination
        );

        // ١٤ - إرسال سبب السفر
        intent.putExtra(
                IntentKeyHelper.construireCle(
                        matricule,
                        "MOTIF"
                ),
                motif
        );

        // ١٥ - فتح شاشة التحقق
        startActivity(intent);
    }

    // ١٦ - إفراغ الاستمارة لمسافر جديد
    private void viderFormulaire() {

        editNom.setText("");
        editNaissance.setText("");
        editDestination.setText("");
        radioMotif.clearCheck();
    }

    // ١٧ - استقبال العودة من شاشة النتيجة
    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);

        viderFormulaire();
    }
}
