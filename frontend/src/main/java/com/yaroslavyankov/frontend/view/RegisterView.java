package com.yaroslavyankov.frontend.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.yaroslavyankov.frontend.dto.user.RegisteredUser;
import com.yaroslavyankov.frontend.dto.user.UserDto;
import com.yaroslavyankov.frontend.props.AuthLinkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Route("register")
@PageTitle("Register | InvestFlow")
public class RegisterView extends VerticalLayout {

    private final RestTemplate restTemplate;

    private final AuthLinkProperties authLinkProperties;

    public RegisterView(RestTemplate restTemplate, AuthLinkProperties authLinkProperties) {
        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        this.restTemplate = restTemplate;
        this.authLinkProperties = authLinkProperties;

        H1 appName = new H1("InvestFlow");
        H2 registerHeader = new H2("Регистрация");
        TextField nameField = new TextField("Имя");
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Пароль");
        PasswordField passwordConfirmation = new PasswordField("Подтвердите пароль");
        Button registerButton = new Button("Зарегистрироваться", event -> {
            String name = nameField.getValue().trim();
            String username = usernameField.getValue().trim();
            String password = passwordField.getValue().trim();
            String confirmPassword = passwordConfirmation.getValue().trim();
            if (!password.equals(confirmPassword)) {
                Notification.show("Пароли не совпадают", 3000, Notification.Position.MIDDLE);
                return;
            }
            UserDto userDto = getUserDto(name, username, password);
            try {
                RegisteredUser response
                        = restTemplate.postForObject(authLinkProperties.getRegisterLink(), userDto, RegisteredUser.class);
                if (response != null) {
                    UI.getCurrent().navigate("/login");
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.CONFLICT) {
                    String detailMessage = e.getMessage();
                    String errorMessage
                            = detailMessage.substring(detailMessage.indexOf("message") + 10, detailMessage.indexOf("}") - 1);
                    Notification.show(errorMessage, 3000, Notification.Position.MIDDLE);
                }
            }
        });

        add(appName,
                registerHeader,
                nameField,
                usernameField,
                passwordField,
                passwordConfirmation,
                registerButton);

    }

    private UserDto getUserDto(String name, String username, String password) {
        UserDto userDto = new UserDto();

        userDto.setName(name);
        userDto.setUsername(username);
        userDto.setPassword(password);

        return userDto;
    }
}
