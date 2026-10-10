package ru.netology.delivery.data;

import lombok.Value;

@Value
public class UserDto {
    String login;
    String password;
    String status;
}
