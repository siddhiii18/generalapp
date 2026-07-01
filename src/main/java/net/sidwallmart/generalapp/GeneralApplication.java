package net.sidwallmart.generalapp;

import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication // equivalent to 3 annotation refer notes
@EnableTransactionManagement // ye annotation sirf main application me hi lagti hai
// spring se kehta hai wo methods dhundo jaha pe transactional likha hoo
// so after finding springboot har method ke corresponding ek transactional context bana dega
// means ek container bana dega jisme uss method se related db ke sare operation rahenge so unn sabko as a 1 single operation treat kiya jayega
// so we r following automacity and achieving isolation also.
@EnableScheduling

public class GeneralApplication {

	public static void main(String[] args) {

		System.out.println("RUNNING PROJECT CHECK");
		SpringApplication.run(GeneralApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){ // MDF we can make connection with DB
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}


//PlatformTransactionManager(PTM)
//MongoTransactionManager (MTM) actual me ye dono help karte hai to commit and rollback
//so hume bean banani padegi to tell that pTM jo ki interface hai uska implementation hai MTM WO spring ko batana padega.

// now we want PTM wo bean degi lekin humko uska instance chhaiye so return new MongoTransactionManager(dbFactory);
// so basically bean banaya PTM interface return karta hai lekin jo actual implementation hai wo MTM karta hai which is using MongoDatabaseFactory
// mongodatabasefactory ko implement karta hai sara -- springmongoclientdatabasefactory.class

