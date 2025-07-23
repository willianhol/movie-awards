package com.awards.api.openapi;


import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "History of Winners - The Awards",
                version = "0.0.1",
                description = "API for accessing Movies and Awards historical data.",
                contact = @Contact(
                        name = "Willian",
                        email = "willianhol@gmail.com"
                )
        )
)public class Documentation extends Application {

}

