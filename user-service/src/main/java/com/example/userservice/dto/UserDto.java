package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String userId;
    private LocalDate createAt;
    private String encryptedPwd;
    private List<ResponseOrder> orders;

}
