package soapwsclient.service;

import com.yuksel.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.SourceExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

@Service
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private final WebServiceTemplate webServiceTemplate;
    private final String url = "http://localhost:8080/person";
    private ObjectFactory factory;

    @Autowired
    public Client(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
        factory = new ObjectFactory();
    }


    private SoapActionCallback soapActionCallbackMapper(String action) {
        return new SoapActionCallback(String.format("http://localhost:8080/person/%sPersonnel", action));
    }

    private void seePeople(List<Personnel> people) {
        for(Personnel personnel : people) {
            System.out.println(String.format("%s - %s - %s", personnel.getIdentifier(), personnel.getFirstName(), personnel.getLastName()));
        }
    }



    public void create(Long identifier, String firstName, String lastName) {
        CreatePersonnelRequest request = this.factory.createCreatePersonnelRequest();
        request.setCreatePersonnel(createUpdatePersonnelRequestInfo(identifier, firstName, lastName));
        PersonnelResponse response = (PersonnelResponse) webServiceTemplate.marshalSendAndReceive(this.url, request, this.soapActionCallbackMapper("create"));
        LOGGER.info("Client received ='{}'", response.getMessage());
    }

    public void get(FilterWith filterWith, String whatToFilterWith) {
        GetPersonnelRequest getPersonnelRequest = this.factory.createGetPersonnelRequest();
        getPersonnelRequest.setGetPersonnel(createGetDeletePersonnelRequestInfo(filterWith, whatToFilterWith));
        GetPersonnelResponse response = (GetPersonnelResponse) webServiceTemplate.marshalSendAndReceive(this.url, getPersonnelRequest, this.soapActionCallbackMapper("get"));
        LOGGER.info("######### Client Received ############\n");
        seePeople(response.getPeople());
    }


    public void delete(FilterWith filterWith, String whatToFilterWith) {
        DeletePersonnelRequest deletePersonnelRequest = this.factory.createDeletePersonnelRequest();
        deletePersonnelRequest.setDeletePersonnel(createGetDeletePersonnelRequestInfo(filterWith, whatToFilterWith));
        PersonnelResponse response = (PersonnelResponse) webServiceTemplate.marshalSendAndReceive(this.url, deletePersonnelRequest, this.soapActionCallbackMapper("create"));
        LOGGER.info("Client received ='{}'", response.getMessage());
    }


    public void update(Long identifier, String firstName, String lastName) {
        UpdatePersonnelRequest request = this.factory.createUpdatePersonnelRequest();
        request.setUpdatePersonnel(createUpdatePersonnelRequestInfo(identifier, firstName, lastName));
        PersonnelResponse response = (PersonnelResponse) webServiceTemplate.marshalSendAndReceive(this.url, request, this.soapActionCallbackMapper("update"));
        LOGGER.info("Client received ='{}'", response.getMessage());
    }



    private PersonnelRequestInfo createUpdatePersonnelRequestInfo(Long identifier, String firstName, String lastName) {
        Personnel personnel = this.factory.createPersonnel();
        personnel.setFirstName(firstName);
        personnel.setLastName(lastName);
        personnel.setIdentifier(identifier);
        PersonnelRequestInfo requestInfo = this.factory.createPersonnelRequestInfo();
        requestInfo.setPersonnel(personnel);
        return requestInfo;
    }

    private GetDeletePersonnelRequestInfo createGetDeletePersonnelRequestInfo(FilterWith filterWith, String whatToFilterWith) {
        GetDeletePersonnelRequestInfo getDeletePersonnelRequestInfo = this.factory.createGetDeletePersonnelRequestInfo();
        getDeletePersonnelRequestInfo.setFilterWith(filterWith);
        getDeletePersonnelRequestInfo.setWhatToFilterWith(whatToFilterWith);
        return getDeletePersonnelRequestInfo;
    }


}
