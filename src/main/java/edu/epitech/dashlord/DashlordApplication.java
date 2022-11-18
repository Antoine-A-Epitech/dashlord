package edu.epitech.dashlord;

import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@CssImport("./style.css")
@SpringBootApplication
public class DashlordApplication {
	public static void main(String[] args) {
		SpringApplication.run(DashlordApplication.class, args);
	}

}
