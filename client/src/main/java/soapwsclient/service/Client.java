package soapwsclient.service;

import com.yuksel.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    @Autowired
    private WebServiceTemplate webServiceTemplate;


    public void create() {
        final SoapActionCallback soapActionCallback = new SoapActionCallback("http://localhost:8080/person/createPersonnel");
        final String url = "http://localhost:8080/person";
        ObjectFactory factory = new ObjectFactory();
        Personnel personnel = factory.createPersonnel();
        personnel.setFirstName("Yuksel");
        personnel.setLastName("Ozdemir");
        personnel.setIdentifier((long) 2264638);
        PersonnelRequestInfo requestInfo = factory.createPersonnelRequestInfo();
        requestInfo.setPersonnel(personnel);
        CreatePersonnelRequest request = factory.createCreatePersonnelRequest();
        request.setCreatePersonnel(requestInfo);
        PersonnelResponse response = (PersonnelResponse) webServiceTemplate.marshalSendAndReceive(url, request, soapActionCallback);
        LOGGER.info("Client received ='{}'", response.getMessage());
    }
}
