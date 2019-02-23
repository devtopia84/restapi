package net.devtopia.rest.config;

import net.devtopia.rest.accounts.Account;
import net.devtopia.rest.accounts.AccountRole;
import net.devtopia.rest.accounts.AccountService;
import net.devtopia.rest.common.BaseControllerTest;
import net.devtopia.rest.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AppProperties appProperties;

    @Test
    @TestDescription("인증 토큰을 발급 받는 테스")
    public void getAuthToken() throws Exception {
        mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getCliendSecret()))
                .param("username", appProperties.getUsername())
                .param("password", appProperties.getPassword())
                .param("grant_type", "password"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("access_token").exists())
        ;

    }
}