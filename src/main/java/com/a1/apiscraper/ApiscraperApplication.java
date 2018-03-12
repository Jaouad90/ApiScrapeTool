package com.a1.apiscraper;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.APIScraper;
import com.a1.apiscraper.logic.SimpleAPIscraper;
import com.a1.apiscraper.logic.TweetDecorator;
import com.a1.apiscraper.repository.*;
import com.a1.apiscraper.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiscraperApplication extends SpringBootServletInitializer{

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private EndpointRepository endpointRepository;
	@Autowired
	private APIRepository apiRepository;
	@Autowired
	private APIConfigRepository apiConfigRepository;
    @Autowired
    private DecoratorRepository decoratorRepository;
    @Autowired
	private CareTakerRepository careTakerRepository;



	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiscraperApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(ApiscraperApplication.class, args);
	}

	@Bean
	@Transactional
	InitializingBean sendDatabase() {
		return () -> {
			List<User> userList = new ArrayList<>();
			List<Role> roleList = new ArrayList<>();
			Role role = new Role();
			role.setName("Admin");
			User user = new User();
			user.setUsername("Admin");
			user.setPassword("Root");
			roleRepository.save(role);
			roleList.add(role);
			userList.add(user);
			role.setUsers(userList);
			user.setRoles(roleList);
			userService.save(user);
//			Map<Long, Endpoint> endpoints = new HashMap<>();
//			Endpoint endpoint = new Endpoint();
//			endpoint.setName("/currentprice.json");
//			Endpoint endpoint1 = new Endpoint();
//			endpoint1.setName("/oldprice.json");
//			Endpoint endpoint2 = new Endpoint();
//			endpoint2.setName("/highprice.json");
//			endpointRepository.save(endpoint);
//			endpointRepository.save(endpoint1);
//			endpointRepository.save(endpoint2);
//			endpoints.put(endpoint.getId(), endpoint);
//			endpoints.put(endpoint1.getId(), endpoint1);
//			endpoints.put(endpoint2.getId(), endpoint2);
			API api = new API();
			api.setName("Coindesk API");
			api.setBaseUrl("https://api.coindesk.com/v1/bpi");
//			api.setEndpoints(endpoints);
			apiRepository.save(api);
			Decorator tweetDecorator = new Decorator();
            tweetDecorator.setName("TweetDecorator");
            decoratorRepository.save(tweetDecorator);

            Decorator mailDecorator = new Decorator();
			mailDecorator.setName("MailDecorator");
            decoratorRepository.save(mailDecorator);

			APIConfig apiConfig = new APIConfig();
			apiConfig.setApi(api);
            apiConfigRepository.save(apiConfig);

            api.setConfig(apiConfig);
            apiRepository.save(api);

//            apiConfig.addDecorator(tweetDecorator);
//            apiConfig.addDecorator(mailDecorator);
			apiConfigRepository.save(apiConfig);

		};
	}
}
