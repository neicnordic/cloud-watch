package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import no.neic.cloudwatch.backend.data.Tenant;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class TenantGrid extends Grid<Tenant> {

    public TenantGrid() {
        setSizeFull();

        addColumn(Tenant::getName)
                .setHeader("Tenant name")
                .setFlexGrow(20)
                .setSortable(true);

        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        // To change the text alignment of the column, a template is used.
        final String vmsRunningTemplate = "<div style='text-align: right'>[[item.vms]]</div>";
        addColumn(TemplateRenderer.<Tenant>of(vmsRunningTemplate)
                .withProperty("vms", tenant -> decimalFormat.format(tenant.getVmsRunning())))
                .setHeader("VMs running")
                .setComparator(Comparator.comparing(Tenant::getVmsRunning))
                .setFlexGrow(3);

        // Show all categories the product is in, separated by commas
        addColumn(this::formatRegions)
                .setHeader("Region")
                .setFlexGrow(12);
    }

    public Tenant getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Tenant tenant) {
        getDataCommunicator().refresh(tenant);
    }

    private String formatRegions(Tenant tenant) {
        if (tenant.getRegion() == null) {
            return "";
        }
        return tenant.getRegion().getName();
    }

}
