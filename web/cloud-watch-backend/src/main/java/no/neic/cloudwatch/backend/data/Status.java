package no.neic.cloudwatch.backend.data;

public enum Status {

    RUNNING("Running"), NOT_RUNNING("Not running");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
