package com.himalayas.authserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(
        scanBasePackages = {
                "com.himalayas.authserver",
                "com.himalayas.securitycommons"
        })
@MapperScan("com.himalayas.authserver.mapper")
@EntityScan(basePackages = "com.himalayas.shareddomain.entities")
@ComponentScan(basePackages = {"com.himalayas.shareddomain", "com.himalayas.authserver", "com.himalayas.securitycommons"})
@EnableJpaRepositories(basePackages = {"com.himalayas.shareddomain.repository", "com.himalayas.securitycommons.tenant"})
public class AuthServerApplication {

/*  private final PasswordEncoder passwordEncoder;

  public AuthServerApplication(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }*/

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }

/*    @Bean
	public CommandLineRunner initData(){
      return args -> {
        System.out.println("------------------password encoded------------------");
        System.out.println(passwordEncoder.encode("password"));
      };
	}*/


	/*@Bean
	public CommandLineRunner initData(TenantRepository tenantRepository,
									  AppUserRepository appUserRepository,
									  PasswordEncoder passwordEncoder){
		return args -> {
			Tenant tenant1 = new Tenant("tenant1", "Tenant One Inc.", "tenant1.auth.example.com");
			tenantRepository.save(tenant1);

			AppUser user1 = new AppUser();
			user1.setId(UUID.randomUUID().toString());
			user1.setTenantId("tenant1");
			user1.setUsername("user1@tenant1.com");
			user1.setPassword(passwordEncoder.encode("password")); // Encode password
			user1.setEnabled(true);
			user1.setRoles(Set.of("ROLE_USER"));
			appUserRepository.save(user1);

			// --- Create Tenant 2 ---
			Tenant tenant2 = new Tenant("tenant2", "Tenant Two Corp.", "tenant2.auth.example.com");
			tenantRepository.save(tenant2);

			// --- Create User for Tenant 2 ---
			AppUser user2 = new AppUser();
			user2.setId(UUID.randomUUID().toString());
			user2.setTenantId("tenant2");
			user2.setUsername("user2@tenant2.com");
			user2.setPassword(passwordEncoder.encode("password")); // Encode password
			user2.setEnabled(true);
			user2.setRoles(Set.of("ROLE_USER", "ROLE_ADMIN"));
			appUserRepository.save(user2);

			System.out.println("Initial tenant and user data populated successfully!");
		};
	}*/

  /*@Bean
  public CommandLineRunner initPKCEClient(TenantAwareRegisteredClientMapper mapper) {
    return args -> {
      TenantContext.setCurrentTenant("tenant1");

      RegisteredClientDto dto = RegisteredClientConverter.from(
              RegisteredClient.withId(UUID.randomUUID().toString())
                      .clientId("spa-client")
                      .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                      .redirectUris((uri) -> uri.add("http://localhost:3000/callback"))
                      .scope(OidcScopes.OPENID)
                      .scope("profile")
                      .clientSettings(ClientSettings.builder()
                              .requireProofKey(true)
                              .requireAuthorizationConsent(true)
                              .build())
                      .tokenSettings(TokenSettings.builder()
                              .accessTokenTimeToLive(Duration.ofMinutes(30))
                              .build())
                      .build(),
              "tenant2"

      );
      mapper.save(dto);
      TenantContext.clear();
    };
  }*/
}
