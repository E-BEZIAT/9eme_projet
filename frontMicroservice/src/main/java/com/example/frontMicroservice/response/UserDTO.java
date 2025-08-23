package com.example.frontMicroservice.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private String email;

    public UserDTO() {}
}
