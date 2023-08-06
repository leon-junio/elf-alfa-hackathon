package com.elf.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.elf.app.providers.WhatsappBuilder;

@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
		//startWhatsappService();
	}

	public static void startWhatsappService() {
		try {
			if (!WhatsappBuilder.isValidated()) {
				WhatsappBuilder.connectNewQr();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
