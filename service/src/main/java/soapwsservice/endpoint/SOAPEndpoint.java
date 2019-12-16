package soapwsservice.endpoint;


import https.www_yuksel_com.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soapwsservice.service.PersonService;


@Endpoint
public class SOAPEndpoint {
    private static final String NAMESPACE_URI = "https://www.yuksel.com/services";
    private final PersonService service;

    @Autowired
    public SOAPEndpoint(PersonService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonnelRequest")
    @ResponsePayload
    public GetPersonnelResponse get(@RequestPayload GetPersonnelRequest request) {
        return service.get(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPersonnelRequest")
    @ResponsePayload
    public PersonnelResponse create(@RequestPayload CreatePersonnelRequest request) {
        return service.create(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePersonnelRequest")
    @ResponsePayload
    public PersonnelResponse update(@RequestPayload UpdatePersonnelRequest request) {
        return service.update(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonnelRequest")
    @ResponsePayload
    public PersonnelResponse delete(@RequestPayload DeletePersonnelRequest request) {
        return service.delete(request);
    }
}