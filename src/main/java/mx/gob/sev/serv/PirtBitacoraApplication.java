package mx.gob.sev.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import mx.gob.sev.serv.config.SecurityConfig;
import mx.gob.sev.serv.config.ModelMapperConfig;

@SpringBootApplication
@Import({SecurityConfig.class, ModelMapperConfig.class}) // Configuraciones adicionales
public class PirtBitacoraApplication extends SpringBootServletInitializer { // Extiende SpringBootServletInitializer

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PirtBitacoraApplication.class); // Configuración para WAR
    }

    public static void main(String[] args) {
        SpringApplication.run(PirtBitacoraApplication.class, args); // Punto de entrada para JAR
    }
}