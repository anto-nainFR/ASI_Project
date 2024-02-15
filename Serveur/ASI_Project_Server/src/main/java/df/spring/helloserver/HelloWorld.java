package df.spring.helloserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootApplication
public class HelloWorld {

    public static void main(String [] args){
        SpringApplication.run(HelloWorld.class,args);
    }

    ArrayList<Utilisateur> listUsers = new ArrayList<Utilisateur>();
    ArrayList<Cours> listCours = new ArrayList<Cours>();

    @Bean
    public CommandLineRunner demo(UtilisateurRepository repoUsers, CoursRepository repoCours) {

        listUsers.add(new Utilisateur(1,"Car....","Antonin",new GregorianCalendar(2001, Calendar.MAY,22), "1 rue du petit caillou Amiens", "Antonin@gmail.com", "password",Boolean.FALSE));
        listUsers.add(new Utilisateur(2,"Jeu....","Malo",new GregorianCalendar(2000, Calendar.JANUARY,1), "2 rue du petit caillou Amiens", "Malo@gmail.com", "password",Boolean.TRUE));
        listUsers.add(new Utilisateur(3,"Vig....","Tanguy",new GregorianCalendar(2000, Calendar.JANUARY,1), "3 rue du petit caillou Amiens", "Tanguy@gmail.com", "password",Boolean.FALSE));

        listCours.add(new Cours(1,new GregorianCalendar(2024, Calendar.FEBRUARY,15),"Cours de plongée","Natation","Piscine municipale",30,listUsers.get(1)));
        listCours.add(new Cours(2,new GregorianCalendar(2024, Calendar.MARCH,1),"Cours d'apnée","Natation","Piscine municipale",5,listUsers.get(1)));

        return (args) -> {
            // rajouter quelques utilisateurs de la repository
            repoUsers.save(listUsers.get(0));
            repoUsers.save(listUsers.get(1));
            repoUsers.save(listUsers.get(2));

            // rajouter quelques cours
            repoCours.save(listCours.get(0));
            repoCours.save(listCours.get(1));
        };
    }
}

/*
    pour pouvoir voir les users un par un





 */