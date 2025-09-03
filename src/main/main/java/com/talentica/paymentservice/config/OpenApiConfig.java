package com.talentica.paymentservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Payment Transaction Service API",
                version = "1.0",
                description = "API documentation for Payment Transaction Service",
                contact = @Contact(name = "Talentica Payments", email = "support@talentica.com"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class OpenApiConfig {
    // Configuration handled by annotation
}
