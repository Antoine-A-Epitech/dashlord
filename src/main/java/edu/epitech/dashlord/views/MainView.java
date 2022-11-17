package edu.epitech.dashlord.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.epitech.dashlord.data.entities.User;
import edu.epitech.dashlord.data.services.AuthService;

import java.util.Optional;

public class MainView extends AppLayout {
    private final Tabs menu;
    private H1 viewTitle;
    private AuthService authService;

    public MainView(AuthService authService) {
        this.authService = authService;
        // user the drawer for the section
        setPrimarySection(Section.DRAWER);

        addHeaderContent();

        menu = createMenu();

        // Put the menu in the drawer
        addDrawerContent(menu);
    }

    private void addHeaderContent() {

        HorizontalLayout layout = new HorizontalLayout();

        // Configure the styling
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        viewTitle = new H1();
        layout.add(new DrawerToggle(),viewTitle);

        addToNavbar(true,layout);
    }

    private void addDrawerContent(Tabs menu) {

        VerticalLayout layout = new VerticalLayout();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        layout.setSizeFull();

        H1 appName = new H1("Dashlord");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(menu);

        Div div = new Div();
        div.add(header,scroller);
        layout.add(div, createFooter());
        addToDrawer(layout);
    }

    private Component createFooter() {
        Footer footer = new Footer();
        MenuBar menu = new MenuBar();
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        Avatar avatar = new Avatar(user.getUserName());
        avatar.setImage("");

        MenuItem menuItem = menu.addItem("");

        Div div = new Div();
        div.add(avatar);
        div.add(user.getUserName());
        div.add(new Icon("lumo", "dropdown"));
        div.getElement().getStyle().set("display", "flex");
        div.getElement().getStyle().set("align-items", "center");
        div.getElement().getStyle().set("gap", "var(--lumo-space-s)");

        menuItem.add(div);
        menuItem.getSubMenu().addItem("Sign out", event -> {
            UI.getCurrent().getPage().setLocation("login");
            VaadinSession.getCurrent().getSession().invalidate();
            VaadinSession.getCurrent().close();
        });
        footer.setWidthFull();
        footer.add(menu);
        return footer;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        var user = VaadinSession.getCurrent().getAttribute(User.class);
        return authService.getAuthRoutes(user.getRole()).stream()
                .map(r -> createTab(r.name(), r.view()))
                .toArray(Component[]::new);
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Select the tab corresponding to currently shown view
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Set the view title in the header
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
