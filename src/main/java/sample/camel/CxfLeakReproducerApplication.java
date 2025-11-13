package sample.camel;

import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sample.camel.service.TempConvertSoap;

@SpringBootApplication
public class CxfLeakReproducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CxfLeakReproducerApplication.class, args);
    }

    @Bean
    public CxfEndpoint tempConvertEndpoint() {
        var endpoint = new CxfEndpoint();
        endpoint.setBeanId("tempConvertEndpoint");
        endpoint.setServiceClass(TempConvertSoap.class);
        endpoint.setAddress("https://www.w3schools.com/xml/tempconvert.asmx");
        endpoint.setDataFormat(DataFormat.POJO);
        return endpoint;
    }
}
