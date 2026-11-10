package com.example.douanetemporelle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VerificationActivity extends Activity {

    private TextView txtNom;
    private TextView txtNaissance;
    private TextView txtDestination;
    private TextView txtAge;
    private TextView txtMotif;
    private TextView txtRisque;

    private Button btnApprouver;
    private Button btnRefuser;

    private String nomVoyageur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ١ - عرض واجهة جواز السفر الزمني
        setContentView(R.layout.activity_verification);

        // ٢ - ربط العناصر الموجودة في ملف XML
        txtNom = findViewById(R.id.txt_nom_yo);

        txtNaissance =
                findViewById(R.id.txt_naissance_yo);

        txtDestination =
                findViewById(R.id.txt_destination_yo);

        txtAge = findViewById(R.id.txt_age_yo);

        txtMotif = findViewById(R.id.txt_motif_yo);

        txtRisque = findViewById(R.id.txt_risque_yo);

        btnApprouver =
                findViewById(R.id.btn_approuver_yo);

        btnRefuser =
                findViewById(R.id.btn_refuser_yo);

        // ٣ - استقبال المعلومات من النشاط الأول
        Intent intent = getIntent();

        String matricule = IntentKeyHelper.MATRICULE;

        // ٤ - إنشاء مفاتيح البيانات باستعمال رقم الطالب
        String cleNom =
                IntentKeyHelper.construireCle(
                        matricule, "NOM_VOYAGEUR");

        String cleNaissance =
                IntentKeyHelper.construireCle(
                        matricule, "ANNEE_NAISSANCE");

        String cleDestination =
                IntentKeyHelper.construireCle(
                        matricule, "ANNEE_DESTINATION");

        String cleMotif =
                IntentKeyHelper.construireCle(
                        matricule, "MOTIF");

        // ٥ - التأكد من وجود جميع البيانات المطلوبة
        if (!intent.hasExtra(cleNom)
                || !intent.hasExtra(cleNaissance)
                || !intent.hasExtra(cleDestination)
                || !intent.hasExtra(cleMotif)) {

            Toast.makeText(
                    this,
                    "Informations du voyageur manquantes",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        // ٦ - استخراج بيانات المسافر
        nomVoyageur = intent.getStringExtra(cleNom);

        int naissance =
                intent.getIntExtra(cleNaissance, 0);

        int destination =
                intent.getIntExtra(cleDestination, 0);

        String motif = intent.getStringExtra(cleMotif);

        // ٧ - التحقق من صحة البيانات
        if (nomVoyageur == null
                || nomVoyageur.trim().isEmpty()
                || motif == null
                || naissance <= 0
                || destination <= 0) {

            Toast.makeText(
                    this,
                    "Profil du voyageur invalide",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        // ٨ - حساب العمر الزمني
        int ageTemporel = destination - naissance;

        // ٩ - عرض المعلومات على جواز السفر
        txtNom.setText("Nom : " + nomVoyageur);

        txtNaissance.setText(
                "Naissance : " + naissance
        );

        txtDestination.setText(
                "Destination : " + destination
        );

        txtAge.setText(
                "Age temporel : " + ageTemporel + " ans"
        );

        txtMotif.setText("Motif : " + motif);

        // ١٠ - التحقق من خطر المفارقة الزمنية
        boolean risqueParadoxe =
                "Modification du passé".equals(motif)
                        && estPremier(ageTemporel);

        if (risqueParadoxe) {

            // ١١ - منع الموافقة عند وجود مفارقة
            btnApprouver.setEnabled(false);

            txtRisque.setText(
                    "Risque de paradoxe détecté !"
            );

            txtRisque.setTextColor(Color.RED);

        } else {

            // ١٢ - السماح بالموافقة في الحالات الأخرى
            btnApprouver.setEnabled(true);

            txtRisque.setText(
                    "Aucun paradoxe détecté"
            );

            txtRisque.setTextColor(
                    Color.rgb(22, 101, 52)
            );
        }

        // ١٣ - إرسال قرار الموافقة
        btnApprouver.setOnClickListener(v ->
                envoyerVerdict(true)
        );

        // ١٤ - إرسال قرار الرفض
        btnRefuser.setOnClickListener(v ->
                envoyerVerdict(false)
        );
    }

    // ١٥ - اختبار ما إذا كان العدد أوليا
    private boolean estPremier(int nombre) {

        if (nombre <= 1) {
            return false;
        }

        // ١٦ - البحث عن قاسم للعدد
        for (int i = 2; i <= nombre / i; i++) {

            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    // ١٧ - فتح النشاط الثالث وإرسال القرار
    private void envoyerVerdict(boolean approuve) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        String matricule = IntentKeyHelper.MATRICULE;

        // ١٨ - إرسال اسم المسافر
        intent.putExtra(
                IntentKeyHelper.construireCle(
                        matricule, "NOM_VOYAGEUR"),
                nomVoyageur
        );

        // ١٩ - إرسال القرار كقيمة منطقية
        intent.putExtra(
                IntentKeyHelper.construireCle(
                        matricule, "VERDICT"),
                approuve
        );

        // ٢٠ - الانتقال إلى شاشة النتيجة
        startActivity(intent);
    }
}
