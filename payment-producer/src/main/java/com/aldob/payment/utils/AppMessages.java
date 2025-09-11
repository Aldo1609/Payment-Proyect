package com.aldob.payment.utils;

public class AppMessages {

    private AppMessages() {
        throw new IllegalStateException("No existe un constructor para la clase AppMessages");
    }

    //200
    public static final String ACTION_OK = "Se completo correctamente la operacion";
    public static final String OK_STATUS_HEALTH = "Servicio payment-producer se encuentra arriba";
    public static final String OK_STATUS_READINESS = "Conexión exitosa con la base de datos.";

    // 500
    public static final String ERROR_DB_CONNECTION = "Error al conectar a la base de datos.";


}
