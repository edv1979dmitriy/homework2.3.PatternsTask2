package ru.netology.delivery.data;

import com.github.javafaker.Faker;

public class Registration {

    public Registration() {
    }

    public static UserDto generateUser(String status) {
        Faker faker = new Faker();
        return new UserDto(DataGenerator.generateLogin(faker), DataGenerator.generatePassword(faker), status);
    }

    public static UserDto generateRegisteredUser(String status) {
        var registeredUser = generateUser(status);
        ApiHelper.sendRequest(registeredUser);
        return registeredUser;
    }
}
