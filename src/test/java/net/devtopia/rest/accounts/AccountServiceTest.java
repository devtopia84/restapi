package net.devtopia.rest.accounts;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void findByUsername() {
        // Given
        String password = "seungho";
        String username = "seungho@doamin.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Stream.of(AccountRole.ADMIN, AccountRole.USER)
                        .collect(Collectors.toCollection(HashSet::new)))
                .build();

        this.accountService.saveAccount(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Than
        assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_returnNotFind() {
        String username = "asdf@asdfasdf.com";

        accountService.loadUserByUsername(username);
    }

    @Test
    public void findByUsername_returnNotFind_tryCatch() {
        String username = "asdf@asdfasdf.com";

        try {
            accountService.loadUserByUsername(username);
            fail("Supporsed to be failed");
        } catch (Exception e) {
            assertThat(e instanceof UsernameNotFoundException).isTrue();
            assertThat(e.getMessage()).containsSequence(username);
        }
    }

    @Test
    public void findByUsername_returnNotFind_Expected() {
        String username = "asdf@asdfasdf.com";

        // Expected
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        // When
        accountService.loadUserByUsername(username);
    }
}