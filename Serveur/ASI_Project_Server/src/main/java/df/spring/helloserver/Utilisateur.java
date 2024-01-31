package df.spring.helloserver;

import jakarta.persistence.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Entity
public class Utilisateur implements UtilisateurRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private int age;

    public Utilisateur(String nom, int age, int id){
        this.id = id;
        this.age = age;
        this.nom = nom;
    }

    public Utilisateur() {}


    public String toString(){
       return "Utilisateur : "+id+" "+nom+" "+age+" ans";
    }
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public List<Utilisateur> findByNom(String nom) {
        return null;
    }

    @Override
    public Optional<Utilisateur> findById(int id) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Utilisateur> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Utilisateur> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Utilisateur> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Utilisateur getOne(Integer integer) {
        return null;
    }

    @Override
    public Utilisateur getById(Integer integer) {
        return null;
    }

    @Override
    public Utilisateur getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Utilisateur> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Utilisateur> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Utilisateur> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Utilisateur> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Utilisateur> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Utilisateur> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Utilisateur, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Utilisateur> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Utilisateur> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Utilisateur> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Utilisateur> findAll() {
        return null;
    }

    @Override
    public List<Utilisateur> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Utilisateur entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Utilisateur> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Utilisateur> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Utilisateur> findAll(Pageable pageable) {
        return null;
    }
}
