package edu.epitech.dashlord.data.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "widgets")
public class Widget {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private boolean requiresKey;

    private Double reloadTime;

    @ManyToOne
    private WidgetService service;

    private String endpointUrl;

    public Widget() {}

    public Widget(String name, String description, boolean requiresKey, Double reloadTime, String endpointUrl, WidgetService service) {
        this.name = name;
        this.description = description;
        this.requiresKey = requiresKey;
        this.reloadTime = reloadTime;
        this.endpointUrl = endpointUrl;
        this.service = service;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public WidgetService getService() {
        return service;
    }

    public void setService(WidgetService service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(Double reloadTime) {
        this.reloadTime = reloadTime;
    }
}
