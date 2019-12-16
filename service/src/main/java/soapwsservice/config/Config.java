package soapwsservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class Config extends WsConfigurerAdapter {
    /**
     * This wakes up a java servlet and registers application context to this servlet
     * @param applicationContext
     * @return
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/*");
    }

    /**
     * This creates the wsdl from the xsd file and publishes this to specified URL
     * @param schema
     * @return
     */
    @Bean(name = "person")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("personServicePort");
        wsdl11Definition.setLocationUri("/person");
        wsdl11Definition.setTargetNamespace("https://www.yuksel.com/services");
        wsdl11Definition.setSchema(schema);
        return wsdl11Definition;
    }

    /**
     * This retrieves the schema created by the developer, enables using xsd in the spring application context
     * @return
     */
    @Bean
    public XsdSchema schema() {
        return new SimpleXsdSchema(new ClassPathResource("schema.xsd"));
    }
}