package townsq.challenge.ninja.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class ChallengeNinjaApiApplication {

	@GetMapping(path = "/")
	@ResponseBody
	public String isStarted() {
		return "Challenge Ninja";
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeNinjaApiApplication.class, args);
	}

}
