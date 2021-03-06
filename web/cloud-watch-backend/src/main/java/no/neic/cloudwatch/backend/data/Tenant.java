package no.neic.cloudwatch.backend.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tenant implements Serializable {

    private int id = -1;
    private String name = "";
    private String source = "";
    private Region region;
    private String vmsRunning;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getVmsRunning() {
        return vmsRunning;
    }

    public void setVmsRunning(String vmsRunning) {
        this.vmsRunning = vmsRunning;
    }

}
