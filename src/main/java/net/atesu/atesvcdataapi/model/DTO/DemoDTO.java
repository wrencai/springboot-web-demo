package net.atesu.atesvcdataapi.model.DTO;

import net.atesu.atesvcdataapi.model.DO.DemoTest;

public class DemoDTO {
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
        this.description = description;
    }

    public DemoDTO build(DemoTest demoTest) {
        this.name = demoTest.getName();
        this.description = demoTest.getDescription();
        return this;
    }
}
