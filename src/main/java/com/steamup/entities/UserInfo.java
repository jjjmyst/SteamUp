package com.steamup.entities;

import com.steamup.models.BasicUserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;

    public UserInfo(BasicUserInfo userInfo) {
        this.name = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.roles = "ROLE_USER";
    }
}
