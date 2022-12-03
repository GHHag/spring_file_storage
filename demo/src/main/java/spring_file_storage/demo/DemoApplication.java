package spring_file_storage.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * För att lösa filsparningen i applikationen har jag använt mig av klassen
 * MultipartFile från springframework.web-biblioteket. Klassen innehåller flera
 * hjälpsamma metoder för att sköta hantera filer vid filuppladdningen och med
 * dem så kunde den funktionaliteten som applikationen behöver implementeras.
 * För att ladda upp en fil så väljer användaren en fil från sin hårddisk och
 * den skickas sedan in i en body till endpointen på /upload. Information om
 * filen lagras i en databastabell där namn, filtyp och filens data finns som
 * kolumner.
 * Valet att använda detta sätt att ladda upp filer på gjordes för att
 * biblioteket för filhantering kunde möta mina behov och var enkelt att
 * använda, både för att skriva koden och som användare av applikationen.
 * Från informationen jag har kommit över så har jag inte hittat något
 * alternativ som verkar bättre för att spara filer utan en array av typen Byte
 * som är sättet MultipartFile klassen hanterar filer i är bra.
 * I övrigt kändes lösningarna tydliga att implementera utifrån uppgiftens
 * beskrivning, vilka entiteter som behövdes för databasen och de enpoints som
 * krävdes för hantera kommunikationen mellan användare och databasen. Det
 * ramverk vi har fått för att kunna skapa ett säkert REST API har varit bra att
 * använda.
 */

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
