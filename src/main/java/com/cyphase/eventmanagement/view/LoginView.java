package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.utils.AuthenticationProviderImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.awt.*;

@Route(value = "login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final LoginForm loginForm;
    private final AuthenticationProviderImpl authenticationProvider;


    public LoginView(AuthenticationProviderImpl authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm = new LoginForm();
        loginForm.setAction("login");
        loginForm.getElement().getThemeList().add("dark");
        loginForm.addLoginListener(loginEvent -> authenticate(loginEvent.getUsername(), loginEvent.getPassword()));
        loginForm.addForgotPasswordListener(event -> getUI().ifPresent(ui -> ui.navigate("forgot-password")));
        add(loginForm);
        Anchor loginWithGoogle = new Anchor("/oauth2/authorization/google", "Sign In With Google");
        loginWithGoogle.getElement().setAttribute("router-ignore", true);
        Anchor signup = new Anchor("/add-user", "Sign Up");
        Image googleLogo = new Image("../../../images/google-logo.png", "No Image");
        googleLogo.addClassName("google-logo");
        signup.addClassName("login-base");
        Div verticalDiv = new Div();
        verticalDiv.addClassName("vertical");
        loginWithGoogle.addClassName("login-base");
        add(new HorizontalLayout(googleLogo, loginWithGoogle, verticalDiv, signup));

    }

    private void authenticate(String userName, String password) {

        try {
            SecurityContextHolder.getContext().setAuthentication(
                    authenticationProvider.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    userName,
                                    password
                            )
                    )
            );
            getUI().ifPresent(ui -> ui.navigate(""));
        } catch (Exception e) {
            Notification.show("Invalid username or password");
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
