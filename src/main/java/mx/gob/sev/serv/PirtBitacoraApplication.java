package mx.gob.sev.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import mx.gob.sev.serv.config.SecurityConfig;
import mx.gob.sev.serv.config.ModelMapperConfig;

@SpringBootApplication
@Import({SecurityConfig.class, ModelMapperConfig.class}) // Asegura que se carguen las configuraciones
public class PirtBitacoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(PirtBitacoraApplication.class, args);
    }
}