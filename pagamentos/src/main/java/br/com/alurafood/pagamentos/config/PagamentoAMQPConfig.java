package br.com.alurafood.pagamentos.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoAMQPConfig {

    @Bean
    public RabbitAdmin criaRabbitAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter converteMensagem() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate criaRabbitTemplate(final ConnectionFactory connectionFactory,
                                             final Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // inicializa o rabbit ao iniciar a aplicação
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(final RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}
