package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Tenant;

import java.util.Collection;

/**
 * A form for editing a single product.
 */
public class TenantForm extends Div {

    private VerticalLayout content;

    private TextField name;
    private ComboBox<Region> region;
    private Button cancel;

    private TenantCrudLogic viewLogic;
    private Binder<Tenant> binder;

    public TenantForm(TenantCrudLogic tenantCrudLogic) {
        setClassName("product-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        add(content);

        viewLogic = tenantCrudLogic;

        name = new TextField("Tenant name");
        name.setWidth("100%");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(name);

        region = new ComboBox<>("Status");
        region.setWidth("100%");
        region.setRequired(true);
        region.setAllowCustomValue(false);
        content.add(region);

        binder = new BeanValidationBinder<>(Tenant.class);
        binder.bindInstanceFields(this);

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelProduct());
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelProduct())
                .setFilter("event.key == 'Escape'");

        content.add(cancel);
    }

    public void setRegions(Collection<Region> categories) {
        region.setItems(categories);
    }

    public void editProduct(Tenant tenant) {
        if (tenant == null) {
            tenant = new Tenant();
        }
        binder.readBean(tenant);
    }

}
