package no.neic.cloudwatch.backend.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Region implements Serializable {

    private String name;

    public Region() {
    }

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
