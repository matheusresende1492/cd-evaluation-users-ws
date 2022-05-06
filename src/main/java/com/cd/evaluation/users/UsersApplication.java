package com.cd.evaluation.users;

import com.cd.evaluation.users.model.enums.roles.RoleEnum;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.model.user.roles.UserRoleModel;
import com.cd.evaluation.users.repository.user.UserMongoRepository;
import org.modelmapper.ModelMapper;
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
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner runner(UserMongoRepository userMongoRepository){
		return args -> {
			if (userMongoRepository.findByEmail("admin").isEmpty()){
				UserModel userModel = new UserModel(
						null,
						"Dev",
						"admin",
						passwordEncoder().encode("admin"),
						null,
						null,
						List.of(new UserRoleModel(RoleEnum.ROLE_ADMIN.toString())));
				userMongoRepository.save(userModel);
			}
		};
	}
}
