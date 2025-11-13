package sample.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.component.cxf.common.message.CxfConstants.OPERATION_NAME;

/**
 * <p>
 * A simple route builder that sets up a REST consumer that in turn calls a SOAP
 * endpoint using camel-cxf-soap.<br/>
 * A memory leak is observed in the class <code>org.apache.cxf.endpoint.ClientImpl</code>, field <code>responseContext</code>.
 * The Spring Boot task execution thread pool grows and shrinks dynamically, so new threads appear after
 * some idle time. The responseContext map not cleanup up, so there will be an entry for each thread
 * that existed. Each entry references the response context, which may contain large amounts of data.
 * </p>
 * <p>
 * The leak can be circumvented by setting property:
 * </p>
 * <p>
 * <code>
 * spring.task.execution.pool.allow-core-thread-timeout=false
 * </code>
 * </p>
 * <p>
 * This forces the thread pool to not remove idle threads, thereby always re-using the threads.
 * </p>
 */
@Component
public class CxfLeakReproducerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        restConfiguration().component("platform-http");

        rest()
            .post("/leak")
            .to("direct:leak");

        from("direct:leak")
            .setBody(constant("1337"))
            .setHeader(OPERATION_NAME, constant("CelsiusToFahrenheit"))
            .to("cxf:bean:tempConvertEndpoint")
            .log("In Fahrenheit: ${body}");
    }
}
