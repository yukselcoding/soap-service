package soapwsservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import soapwsservice.entity.Person;

import java.util.List;

@Repository
public interface PersonRepository  extends CrudRepository<Person, Long> {
    List<Person> findByLastName(String lastName);
    List<Person> findByFirstName(String firstName);
    List<Person> findAll();
    Person findByIdentifier(Long identifier);
    Person save(Person person);
    void deleteAllByFirstName(String firstName);
    void deleteAllByLastName(String lastName);
    void deleteAllByIdentifier(Long identifier);
}
