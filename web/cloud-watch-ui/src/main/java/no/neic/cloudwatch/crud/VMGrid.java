package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import no.neic.cloudwatch.backend.data.VM;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class VMGrid extends Grid<VM> {

    public VMGrid() {
        setSizeFull();

        addColumn(VM::getName)
                .setHeader("VM name")
                .setFlexGrow(20)
                .setSortable(true);

        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        // To change the text alignment of the column, a template is used.
        final String priceTemplate = "<div style='text-align: right'>[[item.flavour]]</div>";
        addColumn(TemplateRenderer.<VM>of(priceTemplate)
                .withProperty("flavour", VM::getFlavour))
                .setHeader("Flavour")
                .setComparator(Comparator.comparing(VM::getFlavour))
                .setFlexGrow(3);

        // Add an traffic light icon in front of availability
        // Three css classes with the same names of three availability values,
        // Available, Coming and Discontinued, are defined in shared-styles.css and are
        // used here in availabilityTemplate.
        final String availabilityTemplate = "<iron-icon icon=\"vaadin:circle\" class-name=\"[[item.availability]]\"></iron-icon> [[item.availability]]";
        addColumn(TemplateRenderer.<VM>of(availabilityTemplate)
                .withProperty("availability", vm -> vm.getStatus().toString()))
                .setHeader("Status")
                .setComparator(Comparator.comparing(VM::getStatus))
                .setFlexGrow(5);


        // Show all categories the product is in, separated by commas
        addColumn(this::formatRegions)
                .setHeader("Region")
                .setFlexGrow(12);
    }

    public VM getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(VM vm) {
        getDataCommunicator().refresh(vm);
    }

    private String formatRegions(VM vm) {
        if (vm.getRegion() == null) {
            return "";
        }
        return vm.getRegion().getName();
    }

}
