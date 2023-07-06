package com.diagno.vision.webapps.diagnovision.dto;

import com.diagno.vision.webapps.diagnovision.config.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @NonNull
    private String firstName;

    private String middleName;

    private String lastName;
    @NonNull
    private String mobileNumber;
    @NonNull
    private String countryDialCode;
    @NonNull
    private String userEmail;
    @NonNull
    private String password;
    @NonNull
    private LocalDateTime lastLogin;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    @Enumerated
    private Role role;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ImageData> images;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userEmail;
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
    public boolean isEnabled() {
        return true;
    }
}
