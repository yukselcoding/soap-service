package soapwsservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Person {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long identifier;

    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;

    public Person(Long identifier, String firstName, String lastName) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
