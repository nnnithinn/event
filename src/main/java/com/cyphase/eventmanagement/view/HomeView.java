package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.utils.SecurityService;
import com.cyphase.eventmanagement.utils.Utils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class HomeView extends AppLayout {
    private final SecurityService securityService;
    public HomeView(SecurityService securityService) {
        this.securityService = securityService;
        DrawerToggle drawerToggle = new DrawerToggle();
        H1 title = new H1("Label Here");
        Div emptyDiv = new Div();
        emptyDiv.getElement().getStyle().set("flex-grow", "1");
        Button logoutButton = new Button("Logout", new Icon(VaadinIcon.EXIT), event ->  logout());
        logoutButton.getElement().getStyle().set("padding", "30px");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout horizontalLayout = new HorizontalLayout(drawerToggle, title, emptyDiv, logoutButton);
        horizontalLayout.setSizeFull();
        addToNavbar(horizontalLayout);
        addToDrawer(getTabs());
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
