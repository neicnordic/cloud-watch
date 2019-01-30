package no.neic.cloudwatch.backend;

import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

import java.util.Collection;

public class RestDataSource extends DataService {

    public static final String ENDPOINT = "";

    @Override
    public Collection<Tenant> getAllTenants() {
        return null;
    }

    @Override
    public Collection<Region> getAllRegions() {
        return null;
    }

    @Override
    public Collection<VM> getAllVMs() {
        return null;
    }

    @Override
    public Tenant getTenantById(int tenantId) {
        return null;
    }

    @Override
    public VM getVMById(int vmId) {
        return null;
    }

}
