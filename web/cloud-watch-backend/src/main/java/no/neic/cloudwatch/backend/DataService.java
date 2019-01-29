package no.neic.cloudwatch.backend;

import java.io.Serializable;
import java.util.Collection;

import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;
import no.neic.cloudwatch.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    public abstract Collection<Tenant> getAllTenants();

    public abstract Collection<Region> getAllRegions();

    public abstract Collection<VM> getAllVMs();

    public abstract Tenant getTenantById(int tenantId);

    public abstract VM getVMById(int vmId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

}
