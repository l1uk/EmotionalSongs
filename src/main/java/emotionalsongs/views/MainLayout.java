package emotionalsongs.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import emotionalsongs.components.appnav.AppNav;
import emotionalsongs.components.appnav.AppNavItem;
//import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private H2 viewTitle;
    HorizontalLayout top;
    HorizontalLayout middle;
    HorizontalLayout bottom;
    LoginForm loginForm;
    Button login;
    Button registerButton;
    Button exitButton;
    Dialog dialog;


    public MainLayout() {
        top = new HorizontalLayout();
        top.setWidthFull();
        top.setMargin(true);
        top.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        loginForm = new LoginForm();
        loginForm.setI18n(createLoginI18n());
        //loginForm.setForgotPasswordButtonVisible(false);
        registerButton = new Button("Non hai ancora un account? Registrati", VaadinIcon.USERS.create());
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        middle = new HorizontalLayout(registerButton);
        middle.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        middle.setAlignSelf(FlexComponent.Alignment.CENTER, registerButton);

        dialog = new Dialog(loginForm, middle);
        registerButton.addClickListener(click -> {
            register();
        });
        exitButton = new Button(VaadinIcon.CLOSE.create(), click -> {
            dialog.close();
        });
        exitButton.getStyle().set("color", "red");

        bottom = new HorizontalLayout();
        bottom.add(exitButton);
        bottom.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        bottom.setAlignSelf(FlexComponent.Alignment.END, exitButton);

        dialog.addComponentAsFirst(bottom);
        dialog.setCloseOnEsc(true);
        login = new Button("Login", VaadinIcon.USER.create(), click -> {
            login();
        });
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        top.setAlignItems(FlexComponent.Alignment.CENTER);
        top.setAlignSelf(FlexComponent.Alignment.END, login);
        top.add(login);

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        addToNavbar(top);
    }

    private void login() {
        dialog.setOpened(true);
    }

    private void register() {
        UI.getCurrent().navigate(RegistrazioneView.class);
        dialog.close();
    }

    private LoginI18n createLoginI18n() {
        final LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getForm().setTitle("Login");
        i18n.getForm().setUsername("Username");
        i18n.getForm().setSubmit("Accedi");
        i18n.getForm().setPassword("Password");
        i18n.getForm().setForgotPassword("Password dimenticata?");
        return i18n;
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        //viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("EmotionalSongs \uD83C\uDFBC");
        //appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, new Footer());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Ricerca Titolo", RicercaTitoloView.class));
        nav.addItem(new AppNavItem("Ricerca Autore e Anno", RicercaAutoreAnnoView.class));
        nav.addItem(new AppNavItem("My Playlist", MyPlaylistView.class));

        return nav;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
