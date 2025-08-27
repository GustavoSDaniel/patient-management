package com.gustavosdaniel.stack.analyticsservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {

       try{

           //  Desserializa os bytes recebidos do patient-service Protocol Buffers
           PatientEvent patientEvent = PatientEvent.parseFrom(event);

           log.info("Received Patient Event: [PatientId={}, PatientName={}, " + "PatientEmail={}]",
                   patientEvent.getPatientId(),
                   patientEvent.getName(),
                   patientEvent.getEmail());

       }catch(Exception e){

           log.error("Error deserializing event {}", e.getMessage());
       }
    }

}
