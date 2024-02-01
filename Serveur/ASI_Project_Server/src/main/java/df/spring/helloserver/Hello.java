package df.spring.helloserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@RequestMapping("/home")
@RestController
public class Hello {



    @GetMapping("")
    public String getHelloPage() throws IOException {
        // Read the HTML file from the classpath
        ClassPathResource resource = new ClassPathResource("/Home.html");
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());

        // Copy the content of the HTML file to a string

        // Return the HTML content
        return FileCopyUtils.copyToString(reader);
    }


    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public Hello(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

/*
    @GetMapping("/{userId}")
    public String getUserById(@PathVariable int userId) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);

        return utilisateurOptional.map(utilisateur -> "User found: " + utilisateur.toString())
                .orElse("User not found");
    }

    @GetMapping("/allUsers")
    public String getAllUsers(){
        List<Utilisateur> utilisateurOptional = utilisateurRepository.findAll();

        String text = "<!DOCTYPE html><html lang=\"en\"<head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>User List</title></head><body><h1>User List</h1><table border=\"1\"><thead><tr><th>ID</th><th>Name</th><th>Prenom</th><th>Date de naissance</th><th>Adresse</th><th>Mail</th><th>Password</th><th>isProfesseur</th></tr></thead><tbody>";
        for (Utilisateur user: utilisateurOptional) {
            text += "<tr>" +
                        "<td>" + user.getId()+"</td>" +
                        "<td>" + user.getNom()+"</td>" +
                        "<td>" + user.getPrenom() +"</td>" +
                        "<td>" + user.getBirthday().getTime() + "</td>" +
                        "<td>" + user.getAdresse() + "</td>" +
                        "<td>" + user.getMail() + "</td>" +
                        "<td>" + user.getPassword() + "</td>" +
                        "<td>" + user.getProfessor() + "</td>" +
                    "</tr>";
        }
        text += "</table></body></html>";
        return text;
    }
    */
}





