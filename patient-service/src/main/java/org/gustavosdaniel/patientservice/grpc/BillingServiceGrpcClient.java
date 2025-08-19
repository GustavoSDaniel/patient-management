package org.gustavosdaniel.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final ManagedChannel channel;
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // A anotação @Value vai injetar o endereço da propriedade
    public BillingServiceGrpcClient(@Value("${grpc.client.billing-service.address:static://localhost:9092}") String serviceAddress) {
        // Extrai o host e a porta do endereço
        String host = serviceAddress.substring(serviceAddress.indexOf("://") + 3, serviceAddress.lastIndexOf(":"));
        int port = Integer.parseInt(serviceAddress.substring(serviceAddress.lastIndexOf(":") + 1));

        log.info("Connecting gRPC client to billing service at {}:{}", host, port);

        // Constrói o canal gRPC com o host e a porta corretos
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Não use TLS, pois é um ambiente de desenvolvimento
                .build();
        this.blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down gRPC channel for billing service.");
        channel.shutdown();
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        BillingResponse response;

        try {
            response = blockingStub.createBillingAccount(request);
            log.info("Received response from billing service via GRPC: {}", response);
        } catch (StatusRuntimeException ex){
            log.error("GRPC call failed: {}: {}", ex.getStatus().getCode(), ex.getStatus().getDescription());
            throw new ValidationAcountException("Failed to create billing account via GRPC", ex);
        }

        log.info("Received response from billing service via GRPC: {}", response);

        return response;
    }
}
