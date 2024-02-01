package df.spring.helloserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class HelloWorld {

    public static void main(String [] args){
        SpringApplication.run(HelloWorld.class,args);
    }

    @Bean
    public CommandLineRunner demo(UtilisateurRepository repository) {
        return (args) -> {
            // rajouter quelques utilisateurs de la repository
            repository.save(new Utilisateur(1,"Car....","Antonin",new GregorianCalendar(2001, Calendar.MAY,22), "1 rue du petit caillou Amiens", "Antonin@gmail.com", "password",Boolean.FALSE));
            repository.save(new Utilisateur(2,"Jeu....","Malo",new GregorianCalendar(2000, Calendar.JANUARY,1), "2 rue du petit caillou Amiens", "Malo@gmail.com", "password",Boolean.TRUE));
            repository.save(new Utilisateur(3,"Vig....","Tanguy",new GregorianCalendar(2000, Calendar.JANUARY,1), "3 rue du petit caillou Amiens", "Tanguy@gmail.com", "password",Boolean.FALSE));        };
    }
}

/*
    pour pouvoir voir les users un par un





 */