package no.neic.cloudwatch.backend.mock;

import no.neic.cloudwatch.backend.data.Region;
import no.neic.cloudwatch.backend.data.Status;
import no.neic.cloudwatch.backend.data.Tenant;
import no.neic.cloudwatch.backend.data.VM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MockDataGenerator {

    private static int nextProductId = 1;
    private static int nextVMId = 1;
    private static final Random random = new Random(1);
    private static final String[] regionNames = new String[]{"osl", "bgo"};

    private static String[] word1 = new String[]{"The art of", "Mastering",
            "The secrets of", "Avoiding", "For fun and profit: ",
            "How to fail at", "10 important facts about",
            "The ultimate guide to", "Book of", "Surviving", "Encyclopedia of",
            "Very much", "Learning the basics of", "The cheap way to",
            "Being awesome at", "The life changer:", "The Vaadin way:",
            "Becoming one with", "Beginners guide to",
            "The complete visual guide to", "The mother of all references:"};

    private static String[] word2 = new String[]{"gardening",
            "living a healthy life", "designing tree houses", "home security",
            "intergalaxy travel", "meditation", "ice hockey",
            "children's education", "computer programming", "Vaadin TreeTable",
            "winter bathing", "playing the cello", "dummies", "rubber bands",
            "feeling down", "debugging", "running barefoot",
            "speaking to a big audience", "creating software", "giant needles",
            "elephants", "keeping your wife happy"};

    static List<Region> createRegions() {
        List<Region> categories = new ArrayList<Region>();
        for (String name : regionNames) {
            Region c = createRegion(name);
            categories.add(c);
        }
        return categories;

    }

    static List<Tenant> createTenants(List<Region> regions) {
        List<Tenant> tenants = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Tenant p = createTenant(regions);
            tenants.add(p);
        }

        return tenants;
    }

    static List<VM> createVMs(List<Tenant> tenants) {
        List<VM> vms = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            VM vm = createVM(tenants);
            vms.add(vm);
        }

        return vms;
    }

    private static Region createRegion(String name) {
        Region region = new Region();
        region.setName(name);
        return region;
    }

    private static Tenant createTenant(List<Region> regions) {
        Tenant tenant = new Tenant();
        tenant.setId(nextProductId++);
        tenant.setName(generateName());
        tenant.setRegion(getRegion(regions));
        tenant.setSource("OpenStack");
        tenant.setVmsRunning(random.nextInt(10));
        return tenant;
    }

    private static VM createVM(List<Tenant> tenants) {
        VM vm = new VM();
        vm.setId(nextVMId++);
        vm.setName(generateName());
        Tenant tenant = getTenant(tenants);
        vm.setTenant(tenant);
        vm.setRegion(tenant.getRegion());
        vm.setFlavour("x-large");
        vm.setStatus(Status.RUNNING);
        return vm;
    }

    private static Region getRegion(List<Region> categories) {
        int nr = random.nextInt(1) + 1;
        HashSet<Region> productCategories = new HashSet<>();
        for (int i = 0; i < nr; i++) {
            productCategories.add(categories.get(random.nextInt(categories.size())));
        }

        return productCategories.iterator().next();
    }

    private static Tenant getTenant(List<Tenant> tenants) {
        int nr = random.nextInt(1) + 1;
        HashSet<Tenant> productCategories = new HashSet<>();
        for (int i = 0; i < nr; i++) {
            productCategories.add(tenants.get(random.nextInt(tenants.size())));
        }

        return productCategories.iterator().next();
    }

    private static String generateName() {
        return word1[random.nextInt(word1.length)] + " " + word2[random.nextInt(word2.length)];
    }

}
