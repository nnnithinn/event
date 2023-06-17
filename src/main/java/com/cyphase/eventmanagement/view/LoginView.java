package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.utils.AuthenticationProviderImpl;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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
        loginForm.addLoginListener(loginEvent -> authenticate(loginEvent.getUsername(), loginEvent.getPassword()));
        add(loginForm);
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
