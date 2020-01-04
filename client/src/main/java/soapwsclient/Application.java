package soapwsclient;

import com.yuksel.services.FilterWith;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import soapwsclient.service.Client;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner lookup(Client client) {
        return args -> {
            client.create((long) 2264646, "Yusuf", "Saglam");
            client.get(FilterWith.FIRST_NAME, "Yuksel");
            client.delete(FilterWith.IDENTIFIER, "2264646");
            client.update((long) 2264646, "Ali", "Veli");
        };
    }
}
