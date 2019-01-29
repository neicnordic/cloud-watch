package no.neic.cloudwatch.crud;

import com.vaadin.flow.component.UI;
import no.neic.cloudwatch.authentication.AccessControl;
import no.neic.cloudwatch.authentication.AccessControlFactory;
import no.neic.cloudwatch.backend.DataService;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 * <p>
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class VMCrudLogic implements Serializable {

    private VMCrudView view;

    public VMCrudLogic(VMCrudView simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editVM(null);
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

        UI.getCurrent().navigate(VMCrudView.class, fragmentParameter);
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
                    VM vm = findVM(pid);
                    view.selectRow(vm);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private VM findVM(int vmId) {
        return DataService.get().getVMById(vmId);
    }

    public void editVM(VM vm) {
        if (vm == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(vm.getId() + "");
        }
        view.editVM(vm);
    }

    public void rowSelected(VM vm) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editVM(vm);
        }
    }

}
