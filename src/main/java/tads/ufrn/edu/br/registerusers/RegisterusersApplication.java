package tads.ufrn.edu.br.registerusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "tads.ufrn.edu.br.registerusers")
@SpringBootApplication
public class RegisterusersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterusersApplication.class, args);
	}

}
