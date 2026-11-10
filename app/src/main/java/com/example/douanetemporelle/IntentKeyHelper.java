package com.example.douanetemporelle;

public class IntentKeyHelper {
    // ١ - آخر أربعة أرقام من بطاقة الطالب
    public static final String MATRICULE = "1234";

    // ٢ - منع إنشاء كائن من هذه الفئة
    private IntentKeyHelper() {
    }

    // ٣ - إنشاء المفتاح باستعمال رقم الطالب
    public static String construireCle(
            String matricule,
            String information) {

        if (matricule == null ||
                !matricule.matches(".*[0-9]{4}")) {
            throw new IllegalArgumentException(
                    "Matricule invalide"
            );
        }

        if (information == null ||
                information.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Information invalide"
            );
        }

        // ٤ - استخراج آخر أربعة أرقام
        String prefixe = matricule.substring(
                matricule.length() - 4
        );

        // ٥ - إرجاع المفتاح النهائي
        return prefixe + "_" + information;
    }
}
