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
import no.neic.cloudwatch.backend.data.Status;
import no.neic.cloudwatch.backend.data.VM;

import java.util.Collection;

/**
 * A form for editing a single product.
 */
public class VMForm extends Div {

    private VerticalLayout content;

    private TextField name;
    private ComboBox<Status> status;
    private ComboBox<Region> region;
    private Button cancel;

    private VMCrudLogic viewLogic;
    private Binder<VM> binder;

    public VMForm(VMCrudLogic vmCrudLogic) {
        setClassName("product-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        add(content);

        viewLogic = vmCrudLogic;

        name = new TextField("VM name");
        name.setWidth("100%");
        name.setRequired(true);
        name.setEnabled(false);
        content.add(name);

        status = new ComboBox<>("Status");
        status.setWidth("100%");
        status.setRequired(true);
        status.setItems(Status.values());
        status.setEnabled(false);
        content.add(status);

        region = new ComboBox<>("Region");
        region.setWidth("100%");
        region.setRequired(true);
        region.setAllowCustomValue(false);
        region.setEnabled(false);
        content.add(region);

        binder = new BeanValidationBinder<>(VM.class);
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

    public void editVM(VM vm) {
        if (vm == null) {
            vm = new VM();
        }
        binder.readBean(vm);
    }

}
