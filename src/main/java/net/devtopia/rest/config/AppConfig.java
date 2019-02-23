package net.devtopia.rest.config;

import net.devtopia.rest.accounts.Account;
import net.devtopia.rest.accounts.AccountRole;
import net.devtopia.rest.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            private AccountService accountService;

            @Autowired
            private AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(Stream.of(AccountRole.ADMIN, AccountRole.USER)
                                .collect(Collectors.toSet()))
                        .build();

                accountService.saveAccount(admin);

                Account user = Account.builder()
                        .email(appProperties.getUsername())
                        .password(appProperties.getPassword())
                        .roles(Stream.of(AccountRole.USER)
                                .collect(Collectors.toSet()))
                        .build();

                accountService.saveAccount(user);
            }
        };
    }
}
