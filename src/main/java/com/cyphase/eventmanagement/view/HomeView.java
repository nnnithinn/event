package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.entity.SystemUser;
import com.cyphase.eventmanagement.repos.SystemUserRepository;
import com.cyphase.eventmanagement.utils.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route("")
@AnonymousAllowed
public class HomeView extends AppLayout {
    private final SecurityService securityService;
    private UserDetails userDetails;
    private Avatar avatar;
    private H1 title;
    private SystemUser systemUser;
    private SystemUserRepository userRepository;
    private ContextMenu contextMenu = new ContextMenu();

    public HomeView(SecurityService securityService, SystemUserRepository systemUserRepository) {
        this.securityService = securityService;
        this.userRepository = systemUserRepository;

        DrawerToggle drawerToggle = new DrawerToggle();
        title = new H1("Hi");
        Div emptyDiv = new Div();
        emptyDiv.getElement().getStyle().set("flex-grow", "1");

        Object user = securityService == null ? null : securityService.getAuthenticatedUser();
        Notification.show(user == null ? "NULL USER" : user.toString());
        //Avatar click functionality
        Button avatarButton = new Button("", avatar = new Avatar());
        contextMenu = new ContextMenu(avatarButton);
        MenuItem loginMenuItem = contextMenu.addItem("Login", event -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));
        MenuItem logoutMenuItem = contextMenu.addItem("Logout", event -> {
            securityService.logout();
            getUI().ifPresent(ui -> ui.navigate("/login"));
        });
        avatarButton.addSingleClickListener(event -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Notification.show("Here " + authentication.getName());
            logoutMenuItem.setVisible(user != null);
            loginMenuItem.setVisible(user == null);
        });
        contextMenu.setOpenOnClick(true);

        drawerToggle.addClassName("title-component");
        title.addClassName("title-component");
        avatarButton.addClassName("title-component");

        avatarButton.addClassName("avatar");
        avatar.setClassName("avatar-image");
        HorizontalLayout horizontalLayout = new HorizontalLayout(drawerToggle, title, emptyDiv, avatarButton);
        horizontalLayout.setSizeFull();

        Tabs tabs = getTabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);

        addToNavbar(horizontalLayout);
        addToDrawer(getTabs());
        loadAndSetData();
    }

    private void loadAndSetData() {
        if (userDetails == null) {
            return;
        }
        String userName = userDetails.getUsername();
        systemUser = userRepository.findByUsername(userName);
        String name = userName;
        if (systemUser != null) {
            name = systemUser.getName();
        }
        avatar.setName(name);
        title.setText("Hi " + name);
    }

    private void logout() {
        securityService.logout();
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.USER, "User(s)", UserGrid.class));
        return tabs;
    }

    private Tab createTab(VaadinIcon vaadinIcon, String tabName, Class<? extends Component> logic) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(tabName));
        link.setRoute(logic);
        link.setTabIndex(-1);
        return new Tab(link);
    }

}
