package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import no.neic.cloudwatch.MainLayout;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Tenant;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link TenantCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "tenants", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class TenantCrudView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Tenants";
    private TenantGrid grid;
    private TenantForm form;
    private TextField filter;

    private TenantCrudLogic viewLogic = new TenantCrudLogic(this);

    private TenantDataProvider dataProvider = new TenantDataProvider();

    public TenantCrudView() {
        setSizeFull();
        HorizontalLayout topLayout = createTopBar();

        grid = new TenantGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

        form = new TenantForm(viewLogic);
        form.setRegions(DataService.get().getAllRegions());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        add(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter name, availability or category");
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(Tenant row) {
        grid.getSelectionModel().select(row);
    }

    public void editTenant(Tenant tenant) {
        showForm(tenant != null);
        form.editProduct(tenant);
    }

    public void showForm(boolean show) {
        form.setVisible(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }

}
