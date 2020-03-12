package br.com.forum.alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport //Para habilitar paginação
@EnableCaching //Usa cache na memoria
public class AluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(AluraApplication.class, args);
    }

}
