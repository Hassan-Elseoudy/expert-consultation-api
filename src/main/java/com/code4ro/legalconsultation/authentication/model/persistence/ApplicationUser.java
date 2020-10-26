package com.code4ro.legalconsultation.authentication.model.persistence;

import com.code4ro.legalconsultation.core.model.persistence.BaseEntity;
import com.code4ro.legalconsultation.user.model.persistence.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "applicationusers")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationUser extends BaseEntity {

    @NotBlank
    @Size(max = 40)
    @Column(unique = true)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @MapsId
    private User user;

    public ApplicationUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
