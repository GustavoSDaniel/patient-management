package com.gustavosdaniel.stack.authservice.confg;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // ativa o sistema de auditoria automática para s entidades.
public class JpaConfiguration {
}
