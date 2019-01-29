package no.neic.cloudwatch.crud;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Status;
import no.neic.cloudwatch.backend.data.Tenant;

/**
 * A form for editing a single product.
 */
public class TenantForm extends Div {

    private VerticalLayout content;

    private TextField name;
//    private ComboBox<Status> status;
    private ComboBox<Region> region;
//    private Button save;
//    private Button discard;
    private Button cancel;
//    private Button delete;

    private TenantCrudLogic viewLogic;
    private Binder<Tenant> binder;
//    private Tenant currentTenant;

//    private static class RegionConverter extends StringToBigDecimalConverter {
//
//        public RegionConverter() {
//            super(BigDecimal.ZERO, "Cannot convert value to a number.");
//        }
//
//        @Override
//        protected NumberFormat getFormat(Locale locale) {
//            // Always display currency with two decimals
//            NumberFormat format = super.getFormat(locale);
//            if (format instanceof DecimalFormat) {
//                format.setMaximumFractionDigits(2);
//                format.setMinimumFractionDigits(2);
//            }
//            return format;
//        }
//    }


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

//        price = new TextField("Price");
//        price.setSuffixComponent(new Span("€"));
//        price.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
//        price.setValueChangeMode(ValueChangeMode.EAGER);
//
//        stockCount = new TextField("In stock");
//        stockCount.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
//        stockCount.setValueChangeMode(ValueChangeMode.EAGER);

//        HorizontalLayout horizontalLayout = new HorizontalLayout(price,
//                stockCount);
//        horizontalLayout.setWidth("100%");
//        horizontalLayout.setFlexGrow(1, price, stockCount);
//        content.add(horizontalLayout);

//        status = new ComboBox<>("Status");
//        status.setWidth("100%");
//        status.setRequired(true);
//        status.setItems(Status.values());
//        status.setAllowCustomValue(false);
//        content.add(status);

        region = new ComboBox<>("Status");
        region.setWidth("100%");
        region.setRequired(true);
//        region.setItems(Status.values());
        region.setAllowCustomValue(false);
        content.add(region);

        binder = new BeanValidationBinder<>(Tenant.class);
//        binder.forField(price).withConverter(new PriceConverter())
//                .bind("price");
//        binder.forField(stockCount).withConverter(new StockCountConverter())
//                .bind("stockCount");
        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
//            save.setEnabled(hasChanges && isValid);
//            discard.setEnabled(hasChanges);
        });

//        save = new Button("Save");
//        save.setWidth("100%");
//        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        save.addClickListener(event -> {
//            if (currentTenant != null
//                    && binder.writeBeanIfValid(currentTenant)) {
//                viewLogic.saveProduct(currentTenant);
//            }
//        });

//        discard = new Button("Discard changes");
//        discard.setWidth("100%");
//        discard.addClickListener(
//                event -> viewLogic.editTenant(currentTenant));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelProduct());
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelProduct())
                .setFilter("event.key == 'Escape'");

//        delete = new Button("Delete");
//        delete.setWidth("100%");
//        delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
//        delete.addClickListener(event -> {
//            if (currentTenant != null) {
//                viewLogic.deleteProduct(currentTenant);
//            }
//        });

        content.add(cancel);
    }

    public void setRegions(Collection<Region> categories) {
        region.setItems(categories);
    }

    public void editProduct(Tenant tenant) {
        if (tenant == null) {
            tenant = new Tenant();
        }
//        delete.setVisible(!tenant.isNewProduct());
//        currentTenant = tenant;
        binder.readBean(tenant);
    }

}
