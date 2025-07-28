package com.mall.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private List<String> roles;
    private List<String> permissions;
}
