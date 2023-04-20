/*
 * Denne ble generert automatisk. Vi har ikke gjort endringer her.
 * 
 * Det er denne som kjøres når vi gjør Run As | Java Application.
 * 
 * Den starter opp en web-tjener på port 8080 der mappingene vi
 * har laget i controllerne er tilgjengelige og virker.
 */
package prosjektGruppe5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

