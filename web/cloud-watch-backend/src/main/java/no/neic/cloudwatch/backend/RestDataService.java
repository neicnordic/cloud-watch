package no.neic.cloudwatch.backend;

import com.google.gson.Gson;
import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Status;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class RestDataService extends DataService {

    private static DataService INSTANCE;

    public static final String ENDPOINT = "http://engine:8080/v1";

    private Gson gson = new Gson();

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestDataService();
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tenant> getAllTenants() {
        try {
            ArrayList<Tenant> result = new ArrayList<>();
            String tenantsString = IOUtils.toString(new URL(ENDPOINT + "/tenants"), Charset.defaultCharset());
            Collection tenants = gson.fromJson(tenantsString, Collection.class);
            for (Object tenant : tenants) {
                Map<String, String> tenantMap = (Map<String, String>) tenant;
                String tenantName = tenantMap.get("name");
                String vmsString = IOUtils.toString(new URL(ENDPOINT + "/instances?name=" + tenantName), Charset.defaultCharset());
                Collection vms = gson.fromJson(vmsString, Collection.class);
                Tenant tenantObject = new Tenant();
                tenantObject.setId(result.size());
                tenantObject.setName(tenantMap.get("tenant"));
                tenantObject.setRegion(new Region(tenantMap.get("region")));
                tenantObject.setSource(tenantMap.get("provider"));
                tenantObject.setVmsRunning(vms.size());
                result.add(tenantObject);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Region> getAllRegions() {
        try {
            ArrayList<Region> result = new ArrayList<>();
            String tenantsString = IOUtils.toString(new URL(ENDPOINT + "/tenants"), Charset.defaultCharset());
            Collection tenants = gson.fromJson(tenantsString, Collection.class);
            for (Object tenant : tenants) {
                Map<String, String> tenantMap = (Map<String, String>) tenant;
                result.add(new Region(tenantMap.get("region")));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VM> getAllVMs() {
        try {
            ArrayList<VM> result = new ArrayList<>();
            String vmsString = IOUtils.toString(new URL(ENDPOINT + "/instances"), Charset.defaultCharset());
            Collection vms = gson.fromJson(vmsString, Collection.class);
            for (Object vm : vms) {
                Map<String, String> vmMap = (Map<String, String>) vm;
                VM vmObject = new VM();
                vmObject.setId(result.size());
                vmObject.setName(vmMap.get("name"));
                vmObject.setRegion(new Region(vmMap.get("region")));
                List<Tenant> allTenants = getAllTenants();
                Tenant tenant = allTenants.stream().filter(t -> t.getName().equals(vmMap.get("tenant"))).findAny().get();
                vmObject.setTenant(tenant);
                String state = vmMap.get("state");
                vmObject.setStatus(state.equals("running") ? Status.RUNNING : Status.NOT_RUNNING);
                vmObject.setImage(vmMap.get("image"));
                vmObject.setStringId(vmMap.get("id"));
                vmObject.setIp(vmMap.get("public_ips"));
                vmObject.setDate(vmMap.get("created_at"));
                result.add(vmObject);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Tenant getTenantById(int tenantId) {
        return getAllTenants().get(tenantId);
    }

    @Override
    public VM getVMById(int vmId) {
        return getAllVMs().get(vmId);
    }

}
