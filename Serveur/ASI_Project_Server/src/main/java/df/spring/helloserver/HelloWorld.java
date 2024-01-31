package df.spring.helloserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloWorld {

    public static void main(String [] args){
        SpringApplication.run(HelloWorld.class,args);
        Utilisateur personne1 = new Utilisateur("Malo",25,1);
    }

    @Bean
    public CommandLineRunner demo(UtilisateurRepository repository) {
        return (args) -> {
            // rajouter quelques utilisateurs de la repository
            repository.save(new Utilisateur("Malo", 25, 1));
            repository.save(new Utilisateur("Antonin", 22, 2));
            repository.save(new Utilisateur("Tanguy", 22, 3));
        };
    }
}

/*
    pour pouvoir voir les users un par un





 */