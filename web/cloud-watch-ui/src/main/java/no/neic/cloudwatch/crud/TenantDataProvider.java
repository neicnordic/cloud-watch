package no.neic.cloudwatch.crud;

import com.vaadin.flow.data.provider.ListDataProvider;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Tenant;

import java.util.Locale;
import java.util.Objects;

public class TenantDataProvider extends ListDataProvider<Tenant> {

    /**
     * Text filter that can be changed separately.
     */
    private String filterText = "";

    public TenantDataProvider() {
        super(DataService.get().getAllTenants());
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for product name, availability and category.
     *
     * @param filterText the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim();

        setFilter(tenant -> passesFilter(tenant.getName(), filterText)
                || passesFilter(tenant.getSource(), filterText)
                || passesFilter(tenant.getRegion(), filterText));
    }

    @Override
    public Integer getId(Tenant tenant) {
        Objects.requireNonNull(tenant,
                "Cannot provide an id for a null tenant.");

        return tenant.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }

}
