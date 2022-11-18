package edu.epitech.dashlord.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Timer;

@Entity(name = "userswidgets")
public class UserWidget {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Widget widget;

    private String userKey;

    @Transient
    private Timer timer;

    public UserWidget(){}

    public UserWidget(User user, Widget widget) {
        this.user = user;
        this.widget = widget;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Integer getId() {
        return id;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
