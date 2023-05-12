package emotionalsongs.progetto;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Ricerca")
@Route(value = "ricerca-titolo", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)

public class RicercaTitoloView extends VerticalLayout {
    HorizontalLayout layoutTitolo;
    HorizontalLayout toolbar;
    Icon iconTitolo;
    H3 titoloPagina;
    TextField titoloDaCercare;
    Button searchButton;
    Grid<Canzone> grid = new Grid<>(Canzone.class);

    private final CanzoneService canzoneService;

    public RicercaTitoloView(@Autowired CanzoneService canzoneService) {
        this.canzoneService = canzoneService;
        setSpacing(true);
        setSizeFull();
        layoutTitolo = new HorizontalLayout();
        layoutTitolo.setAlignItems(Alignment.CENTER);
        iconTitolo = new Icon(VaadinIcon.SEARCH);
        iconTitolo.setColor("#006af5");
        titoloPagina = new H3("Titolo");

        layoutTitolo.add(iconTitolo, titoloPagina);

        titoloDaCercare = new TextField();
        titoloDaCercare.setPlaceholder("Inserisci titolo...");
        searchButton = new Button("Cerca", buttonClickEvent -> {
            search();
        });

        searchButton.setAutofocus(true);
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        toolbar = new HorizontalLayout(titoloDaCercare, searchButton);
        addClassName("list-view");
        configureGrid();
        updateSongList();

        add(layoutTitolo, toolbar, grid);
    }

    private void configureGrid() {
        grid.addClassName("list-canzoni-view");
        grid.setSizeFull();
    }

    private void updateSongList() {
        grid.setItems(canzoneService.findAllCanzoni(null));
    }

    private void search() {
        titoloDaCercare.setValue("");
        titoloDaCercare.setPlaceholder("Inserisci titolo...");
    }
}
