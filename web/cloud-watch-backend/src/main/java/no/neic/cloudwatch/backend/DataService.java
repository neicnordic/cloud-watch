package no.neic.cloudwatch.backend;

import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

import java.io.Serializable;
import java.util.List;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    public abstract List<Tenant> getAllTenants();

    public abstract List<Region> getAllRegions();

    public abstract List<VM> getAllVMs();

    public abstract Tenant getTenantById(int tenantId);

    public abstract VM getVMById(int vmId);

    public static DataService get() {
        return RestDataService.getInstance();
    }

}
