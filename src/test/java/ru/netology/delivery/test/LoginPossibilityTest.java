package ru.netology.delivery.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.Registration.generateRegisteredUser;
import static ru.netology.delivery.data.Registration.generateUser;

public class LoginPossibilityTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Должен войти в ЛК зарегистрированный пользователь")
    public void shouldSuccessfullyLoginWithActiveRegisteredUser() {
        var registeredUser = generateRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    @DisplayName("Не должен войти в ЛК незарегистрированный пользователь")
    public void shouldUnsuccessfullyLoginUnregisteredUser() {
        var unregisteredUser = generateUser("active");
        $("[data-test-id='login'] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(unregisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe((visible), Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Не должен войти в ЛК заблокированный зарегистрированный пользователь")
    public void shouldUnsuccessfullyLoginWithBlockedRegisteredUser() {
        var blockedUser = generateRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe((visible), Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Не должен войти в ЛК зарегистрированный пользователь при вводе неверного логина")
    public void shouldUnsuccessfullyLoginWithIncorrectedLoginActiveUser() {
        var registeredUser = generateRegisteredUser("active");
        var faker = new Faker();
        $("[data-test-id='login'] input").setValue(DataGenerator.generateLogin(faker));
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe((visible), Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Не должен войти в ЛК зарегистрированный пользователь при вводе неверного пароля")
    public void shouldUnsuccessfullyLoginWithIncorrectedPasswordActiveUser() {
        var registeredUser = generateRegisteredUser("active");
        var faker = new Faker();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.generatePassword(faker));
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe((visible), Duration.ofSeconds(10));
    }
}
