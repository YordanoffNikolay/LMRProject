package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workdays")
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workday_id")
    private long workdayId;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workday", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Workday() {
    }

    public Workday(Date date, User user) {
        this.date = date;
        this.user = user;
    }

    public long getWorkdayId() {
        return workdayId;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setWorkdayId(long workdayId) {
        this.workdayId = workdayId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workday workday = (Workday) o;
        return workdayId == workday.workdayId &&
                Objects.equals(date, workday.date) &&
                Objects.equals(user, workday.user) &&
                Objects.equals(tasks, workday.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workdayId, date, user, tasks);
    }
}
