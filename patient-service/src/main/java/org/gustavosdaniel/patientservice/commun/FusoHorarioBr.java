package org.gustavosdaniel.patientservice.commun;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FusoHorarioBr {

    // Método que retorna a data e hora atuais de Brasília
    public static ZonedDateTime nowInBrasil() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }
}
