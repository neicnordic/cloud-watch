package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import no.neic.cloudwatch.MainLayout;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link TenantCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "vms", layout = MainLayout.class)
@RouteAlias(value = "vms", layout = MainLayout.class)
public class VMCrudView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "VMs";
    private VMGrid grid;
    private VMForm form;
    private ComboBox<Tenant> tenantFilter;
    private ComboBox<Region> regionFilter;
    private TextField filter;

    private VMCrudLogic viewLogic = new VMCrudLogic(this);

    private VMDataProvider dataProvider = new VMDataProvider();

    public VMCrudView() {
        setSizeFull();
        HorizontalLayout topLayout = createTopBar();

        grid = new VMGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

        form = new VMForm(viewLogic);
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
        DataService dataService = DataService.get();

        tenantFilter = new ComboBox<>("Tenant", dataService.getAllTenants());
        tenantFilter.setItemLabelGenerator(Tenant::getName);
        tenantFilter.addValueChangeListener(event -> dataProvider.setTenantFilter(tenantFilter.getValue() == null ? "" : tenantFilter.getValue().getName()));

        regionFilter = new ComboBox<>("Region", dataService.getAllRegions());
        regionFilter.setItemLabelGenerator(Region::getName);
        regionFilter.addValueChangeListener(event -> dataProvider.setRegionFilter(regionFilter.getValue() == null ? "" : regionFilter.getValue().getName()));

        filter = new TextField();
        filter.setPlaceholder("Filter entries by typing here...");
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(tenantFilter, regionFilter, filter);
        topLayout.setVerticalComponentAlignment(Alignment.END, tenantFilter, regionFilter, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(VM row) {
        grid.getSelectionModel().select(row);
    }

    public void editVM(VM vm) {
        showForm(vm != null);
        form.editVM(vm);
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
