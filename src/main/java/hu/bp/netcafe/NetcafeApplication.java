package hu.bp.netcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NetcafeApplication {
	public static void main(String[] args) {
		SpringApplication.run(NetcafeApplication.class, args);
	}
}

