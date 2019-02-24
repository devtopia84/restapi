package net.devtopia.rest.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAdpter extends User {
    private Account account;

    public AccountAdpter(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));

        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(accountRole -> new SimpleGrantedAuthority("ROLE"+ accountRole.name()))
                .collect(Collectors.toSet());
    }

    public Account getAccount() {
        return account;
    }
}
