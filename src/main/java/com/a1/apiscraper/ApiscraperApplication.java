package com.a1.apiscraper;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Role;
import com.a1.apiscraper.domain.User;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import com.a1.apiscraper.repository.RoleRepository;
import com.a1.apiscraper.repository.UserRepository;
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
			List<Endpoint> endpointList = new ArrayList<>();
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
			Endpoint endpoint1 = new Endpoint();
			endpoint1.setName("/currentprice.json");
			endpointList.add(endpoint1);
			API api = new API();
			api.setName("Coindesk API");
			api.setBaseUrl("https://api.coindesk.com/v1/bpi");
			api.setEndpoints(endpointList);
			apiRepository.save(api);
			endpointRepository.save(endpoint1);
		};
	}
}
