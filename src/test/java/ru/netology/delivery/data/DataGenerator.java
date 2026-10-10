package ru.netology.delivery.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {

    public DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("en"));

    public static String generateLogin(Faker faker) {
        return faker.name().username();
    }

    public static String generatePassword(Faker faker) {
        return faker.internet().password();
    }
}
