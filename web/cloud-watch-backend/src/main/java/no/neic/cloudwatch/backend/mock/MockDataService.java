package no.neic.cloudwatch.backend.mock;

import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

import java.util.Collection;
import java.util.List;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

    private static MockDataService INSTANCE;

    private List<Tenant> tenants;
    private List<Region> regions;
    private List<VM> vms;

    private MockDataService() {
        regions = MockDataGenerator.createRegions();
        tenants = MockDataGenerator.createTenants(regions);
        vms = MockDataGenerator.createVMs(regions);
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
        return INSTANCE;
    }

    @Override
    public synchronized List<Tenant> getAllTenants() {
        return tenants;
    }

    @Override
    public synchronized List<Region> getAllRegions() {
        return regions;
    }

    @Override
    public Collection<VM> getAllVMs() {
        return vms;
    }

    @Override
    public synchronized Tenant getTenantById(int tenantId) {
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId() == tenantId) {
                return tenants.get(i);
            }
        }
        return null;
    }

    @Override
    public VM getVMById(int vmId) {
        for (int i = 0; i < vms.size(); i++) {
            if (vms.get(i).getId() == vmId) {
                return vms.get(i);
            }
        }
        return null;
    }

}
