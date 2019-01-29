package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import no.neic.cloudwatch.MainLayout;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Product;

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
    private ProductGrid grid;
//    private ProductForm form;
    private TextField filter;

    private VMCrudLogic viewLogic = new VMCrudLogic(this);
//    private Button newProduct;

    private ProductDataProvider dataProvider = new ProductDataProvider();

    public VMCrudView() {
        setSizeFull();
        HorizontalLayout topLayout = createTopBar();

        grid = new ProductGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

//        form = new ProductForm(viewLogic);
//        form.setCategories(DataService.get().getAllCategories());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
//        add(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter name, availability or category");
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

//        newProduct = new Button("New product");
//        newProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        newProduct.setIcon(VaadinIcon.PLUS_CIRCLE.create());
//        newProduct.addClickListener(click -> viewLogic.newProduct());

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
//        topLayout.add(newProduct);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

//    public void setNewProductEnabled(boolean enabled) {
//        newProduct.setEnabled(enabled);
//    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(Product row) {
        grid.getSelectionModel().select(row);
    }

    public Product getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateProduct(Product product) {
        dataProvider.save(product);
    }

    public void removeProduct(Product product) {
        dataProvider.delete(product);
    }

//    public void editProduct(Product product) {
//        showForm(product != null);
//        form.editProduct(product);
//    }

    public void showForm(boolean show) {
//        form.setVisible(show);

        /* FIXME The following line should be uncommented when the CheckboxGroup
         * issue is resolved. The category CheckboxGroup throws an
         * IllegalArgumentException when the form is disabled.
         */
        //form.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }

}
