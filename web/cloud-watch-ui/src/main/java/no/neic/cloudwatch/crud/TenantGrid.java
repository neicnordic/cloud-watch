package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

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

        final String vmsRunningTemplate = "<div style='text-align: right'>[[item.vms]]</div>";
        addColumn(TemplateRenderer.<Tenant>of(vmsRunningTemplate)
                .withProperty("vms", Tenant::getVmsRunning))
                .setHeader("VMs running")
                .setComparator(Comparator.comparing(Tenant::getVmsRunning))
                .setFlexGrow(3);

        addColumn(this::formatRegions)
                .setHeader("Region")
                .setComparator(Comparator.comparing(tenant -> tenant.getRegion().getName()))
                .setFlexGrow(12);
    }

    private String formatRegions(Tenant tenant) {
        if (tenant.getRegion() == null) {
            return "";
        }
        return tenant.getRegion().getName();
    }

}
