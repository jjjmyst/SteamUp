package com.steamup.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserInfo {

    protected String username;
    protected String email;
    protected String password;
}
