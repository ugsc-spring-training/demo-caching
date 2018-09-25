package com.example.democaching;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan
@EnableCaching
public class DemoCachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCachingApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("calc");
	}
}

@Component
class MyRunner implements CommandLineRunner {

	private final Calculation calculation;

	MyRunner(Calculation calculation) {
		this.calculation = calculation;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("result:" + calculation.difficultCalculation(1));
		System.out.println("result:" + calculation.difficultCalculation(1));

		calculation.clearCache();
		System.out.println("result:" + calculation.difficultCalculation(1));
	}
}

@Component
class Calculation {

	@Cacheable("calc")
	public int difficultCalculation(Integer a) {
		System.out.println("doing very expensive calculation");
		return a + 1;
	}

	@CacheEvict(value="calc", allEntries = true)
	public void clearCache() {}
}
