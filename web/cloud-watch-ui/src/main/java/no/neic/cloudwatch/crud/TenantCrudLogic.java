package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.UI;
import no.neic.cloudwatch.authentication.AccessControl;
import no.neic.cloudwatch.authentication.AccessControlFactory;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Tenant;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class TenantCrudLogic implements Serializable {

    private TenantCrudView view;

    public TenantCrudLogic(TenantCrudView simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editTenant(null);
        // Hide and disable if not admin
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
//            view.setNewProductEnabled(false);
        }
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        UI.getCurrent().navigate(TenantCrudView.class, fragmentParameter);
    }

    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
//                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(productId);
                    Tenant tenant = findTenant(pid);
                    view.selectRow(tenant);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private Tenant findTenant(int tenantId) {
        return DataService.get().getTenantById(tenantId);
    }

//    public void saveProduct(Tenant tenant) {
//        boolean newProduct = tenant.isNewProduct();
//        view.clearSelection();
//        view.updateProduct(tenant);
//        setFragmentParameter("");
//        view.showSaveNotification(tenant.getName()
//                + (newProduct ? " created" : " updated"));
//    }

//    public void deleteProduct(Tenant tenant) {
//        view.clearSelection();
//        view.removeProduct(tenant);
//        setFragmentParameter("");
//        view.showSaveNotification(tenant.getName() + " removed");
//    }

    public void editTenant(Tenant tenant) {
        if (tenant == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(tenant.getId() + "");
        }
        view.editTenant(tenant);
    }

//    public void newProduct() {
//        view.clearSelection();
//        setFragmentParameter("new");
//        view.editTenant(new Tenant());
//    }

    public void rowSelected(Tenant tenant) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editTenant(tenant);
        }
    }

}
