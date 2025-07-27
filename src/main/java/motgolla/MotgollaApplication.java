package motgolla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MotgollaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotgollaApplication.class, args);
    }

}
