package com.learning.learningquartzscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LearningQuartzSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningQuartzSchedulerApplication.class, args);
	}

}
