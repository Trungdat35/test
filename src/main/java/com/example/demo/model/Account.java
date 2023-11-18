package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountID;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private int status;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @Column(name = "reset_password_token_expiry")
    private LocalDateTime resetPasswordTokenExprixy;
    @Column(name = "create_at")
    private LocalDate createAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "decentralization_id",referencedColumnName = "decentralization_id",foreignKey = @ForeignKey(name = "fk_account_decentralization"))
    private  Decentralization decentralization;
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Users> usersSet;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // phân quyền
            return List.of(new SimpleGrantedAuthority(decentralization.getAuthorityName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { // giúp tài khoản k bị hết hạn
        return true;
    }
}
