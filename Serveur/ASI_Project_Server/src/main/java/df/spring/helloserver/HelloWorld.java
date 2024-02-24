package df.spring.helloserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication
public class HelloWorld {



    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String [] args){
        SpringApplication.run(HelloWorld.class,args);
    }

    ArrayList<Utilisateur> listUsers = new ArrayList<Utilisateur>();
    ArrayList<Cours> listCours = new ArrayList<Cours>();
    ArrayList<Inscription> listInscription = new ArrayList<Inscription>();


    @Bean
    public CommandLineRunner demo(UtilisateurRepository repoUsers, CoursRepository repoCours, InscriptionRepository repoInscription) {

        try {
            String originalString = "password";
            String originalString2 = "mdp";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
            byte[] encodedhash2 = digest.digest(originalString2.getBytes(StandardCharsets.UTF_8));

            listUsers.add(new Utilisateur(1,"Car....","Antonin",new GregorianCalendar(2001, Calendar.MAY,22), "1 rue du petit caillou Amiens", "Antonin@gmail.com", bytesToHex(encodedhash),Boolean.FALSE));
            listUsers.add(new Utilisateur(2,"Jeu....","Malo",new GregorianCalendar(2000, Calendar.JANUARY,1), "2 rue du petit caillou Amiens", "Malo@gmail.com", bytesToHex(encodedhash),Boolean.TRUE));
            listUsers.add(new Utilisateur(3,"Vig....","Tanguy",new GregorianCalendar(2000, Calendar.JANUARY,1), "3 rue du petit caillou Amiens", "Tanguy@gmail.com", bytesToHex(encodedhash),Boolean.FALSE));

            listUsers.add(new Utilisateur("Test1....","Ahah",new GregorianCalendar(2000, Calendar.JANUARY,1), "petite rue", "test1@gmail.com", bytesToHex(encodedhash2),Boolean.FALSE));
            listUsers.add(new Utilisateur("Test2....","Eheh",new GregorianCalendar(2000, Calendar.JANUARY,1), "grosse rue", "test2@gmail.com", bytesToHex(encodedhash2),Boolean.FALSE));


            listCours.add(new Cours(1,new Date(2024-1900,1,15).getTime(),"Cours de plongée","Natation","Piscine municipale",30,listUsers.get(1)));
            listCours.add(new Cours(2,new Date(2024-1900,2,1).getTime(),"Cours d'apnée","Natation","Piscine municipale",5,listUsers.get(1)));
            listCours.add(new Cours(3,new Date(2024-1900,2,14).getTime(),"Cours","Tennis","terre battue",4,listUsers.get(1)));

            listInscription.add(new Inscription(1, 1, 1));
            listInscription.add(new Inscription(2, 1, 2));
            listInscription.add(new Inscription(3, 3, 2));
        }catch (Exception e){
        }


        return (args) -> {
            // rajouter quelques utilisateurs de la repository
            repoUsers.save(listUsers.get(0));
            repoUsers.save(listUsers.get(1));
            repoUsers.save(listUsers.get(2));

            repoUsers.save(listUsers.get(3));
            repoUsers.save(listUsers.get(4));


            // rajouter quelques cours
            repoCours.save(listCours.get(0));
            repoCours.save(listCours.get(1));
            repoCours.save(listCours.get(2));

            repoInscription.save(listInscription.get(0));
            repoInscription.save(listInscription.get(1));
            repoInscription.save(listInscription.get(2));
        };
    }
}