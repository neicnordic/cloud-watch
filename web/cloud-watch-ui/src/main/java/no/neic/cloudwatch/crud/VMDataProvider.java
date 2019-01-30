package no.neic.cloudwatch.crud;

import com.vaadin.flow.data.provider.ListDataProvider;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.VM;

import java.util.Locale;
import java.util.Objects;

public class VMDataProvider extends ListDataProvider<VM> {

    private String tenantFilterText = "";
    private String regionFilterText = "";
    private String filterText = "";

    public VMDataProvider() {
        super(DataService.get().getAllVMs());
    }

    public void setTenantFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.tenantFilterText, filterText.trim())) {
            return;
        }
        this.tenantFilterText = filterText.trim();


        updateFilter();
    }

    public void setRegionFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.regionFilterText, filterText.trim())) {
            return;
        }
        this.regionFilterText = filterText.trim();

        updateFilter();
    }

    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim();

        updateFilter();
    }

    private void updateFilter() {
        setFilter(vm -> (passesFilter(vm.getName(), filterText)
                || passesFilter(vm.getImage(), filterText)
                || passesFilter(vm.getStatus(), filterText))
        && passesFilter(vm.getTenant(), tenantFilterText) && passesFilter(vm.getRegion(), regionFilterText));
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }

}
