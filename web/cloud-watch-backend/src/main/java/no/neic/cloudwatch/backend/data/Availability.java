package no.neic.cloudwatch.backend.data;

public enum Availability {

    COMING("Coming"), AVAILABLE("Available"), DISCONTINUED("Discontinued");

    private final String name;

    Availability(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
