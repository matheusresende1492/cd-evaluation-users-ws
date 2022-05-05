package com.cd.evaluation.users;

import com.cd.evaluation.users.model.enums.roles.RoleEnum;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.model.user.roles.UserRole;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner runner(UserMongoRepository userMongoRepository){
		return args -> {
			if (userMongoRepository.findByEmail("admin").isEmpty()){
				UserModel userModel = new UserModel("Dev",
						"admin",
						passwordEncoder().encode("admin"),
						null,
						null,
						List.of(new UserRole(RoleEnum.ROLE_ADMIN.toString())));
				userMongoRepository.insert(userModel);
			}
		};
	}
}
