package net.atesu.atesvcdataapi.model.DO;

public class DemoTest {
    private Integer name;

    private String description;

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}