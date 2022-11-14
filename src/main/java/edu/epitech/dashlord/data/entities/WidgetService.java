package edu.epitech.dashlord.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "widgetservices")
public class WidgetService {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String logoUrl;

    public WidgetService() {}

    public WidgetService(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
