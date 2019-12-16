package soapwsservice.service;

import https.www_yuksel_com.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soapwsservice.entity.Person;
import soapwsservice.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service for the person endpoint and repository
 * All business logic about person is embedded into this
 */
@Service
public class PersonService {
    private  final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    /**
     * This method deletes relevant records with the filterWith field
     * @param request
     * @return
     */
    public PersonnelResponse delete(DeletePersonnelRequest request) {
        try {
            FilterWith filterWith = request.getDeletePersonnel().getFilterWith();
            String whatToFilterWith = request.getDeletePersonnel().getWhatToFilterWith();
            if(filterWith.equals(FilterWith.FIRST_NAME)) {
               repository.deleteAllByFirstName(whatToFilterWith);
            } else if(filterWith.equals(FilterWith.LAST_NAME)) {
                repository.deleteAllByLastName(whatToFilterWith);
            } else if(filterWith.equals(FilterWith.IDENTIFIER)){
                Long identifier = Long.valueOf(whatToFilterWith);
                repository.deleteAllByIdentifier(identifier);
            } else {
                return respond("Delete request Failed!");
            }
            return respond("All records with this identifier deleted");
        } catch (Exception e) {
            return respond("Delete request Failed!");
        }
    }

    /**
     * This method updates the person record if finds by identifier
     * @param request
     * @return
     */
    public PersonnelResponse update(UpdatePersonnelRequest request) {
        try {
            Personnel personnel = request.getUpdatePersonnel().getPersonnel();
            Person person = repository.findByIdentifier(personnel.getIdentifier());
            if(person == null)
                return respond("Person Not Found!");
            else {
                person.setIdentifier(personnel.getIdentifier());
                person.setFirstName(personnel.getFirstName());
                person.setLastName(personnel.getLastName());
                Person updatedPerson = repository.save(person);
                if(updatedPerson == null)
                    return respond("Update operation fail!");
                else
                    return respond("Update operation successful");
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method creates a new record from SOAP request, and if record saved
     * gives necessary warnings
     * @param request
     * @return
     */
    public PersonnelResponse create(CreatePersonnelRequest request) {
        try {
            Personnel personnel = request.getCreatePersonnel().getPersonnel();
            Person personRecord = repository.findByIdentifier(personnel.getIdentifier());
            if(personRecord != null)
                return respond(String.format("This person with id: %s already in DB",
                        personnel.getIdentifier()));
            else {
                Person personToBeSaved = new Person(personnel.getIdentifier(),
                        personnel.getFirstName(), personnel.getLastName());
                Person savedPerson = repository.save(personToBeSaved);
                if(savedPerson != null)
                    return respond(String.format("Personnel with id:%s is saved successfully!",
                            savedPerson.getIdentifier()));
                else
                    return respond("Personnel creation failed!");
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method gets the relevant records according to filterWith
     * variable, e.g if filterWith is identifier, then filters with respect to that
     * @param request
     * @return
     */
    public GetPersonnelResponse get(GetPersonnelRequest request) {
        try {
            GetDeletePersonnelRequestInfo requestInfo = request.getGetPersonnel();
            FilterWith filterWith = requestInfo.getFilterWith();
            String whatToFilterWith = requestInfo.getWhatToFilterWith();
            List<Person> personList = new ArrayList<>();
            if(filterWith.equals(FilterWith.FIRST_NAME)) {
                personList = repository.findByFirstName(whatToFilterWith);
            } else if(filterWith.equals(FilterWith.LAST_NAME)) {
                personList = repository.findByLastName(whatToFilterWith);
            } else if(filterWith.equals(FilterWith.IDENTIFIER)){
                Long identifier = Long.valueOf(whatToFilterWith);
                personList.add(repository.findByIdentifier(identifier));
            } else {
                personList = repository.findAll();
            }
            return respond(personList);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Prepares response with respect to getPerson procedure call
     * @param personnel
     * @return
     */
    GetPersonnelResponse respond(List<Person> personnel) {
        ObjectFactory factory = new ObjectFactory();
        GetPersonnelResponse response = factory.createGetPersonnelResponse();
        for(Person person : personnel) {
            Personnel newPersonnel = factory.createPersonnel();
            newPersonnel.setIdentifier(person.getIdentifier());
            newPersonnel.setFirstName(person.getFirstName());
            newPersonnel.setLastName(person.getLastName());
            response.getPeople().add(newPersonnel);
        }
        return response;
    }


    /**
     * Prepares response with a informative message
     * @param message
     * @return
     */
    PersonnelResponse respond(String message) {
        ObjectFactory factory = new ObjectFactory();
        PersonnelResponse response = factory.createPersonnelResponse();
        response.setMessage(message);
        return response;
    }
}
