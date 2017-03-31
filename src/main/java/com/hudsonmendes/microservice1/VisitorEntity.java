package com.hudsonmendes.microservice1;

import org.springframework.data.annotation.Id;

public class VisitorEntity {

    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
