package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tasks")
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private long taskId;

    @Column(name = "time_spent")
    private int timeSpent;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "is_locked")
    private boolean isLocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workday_id")
    private Workday workday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Workday getWorkday() {
        return workday;
    }

    public void setWorkday(Workday workday) {
        this.workday = workday;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return timeSpent == task.timeSpent
                && isCompleted == task.isCompleted
                && isLocked == task.isLocked
                && Objects.equals(workday, task.workday)
                && Objects.equals(user, task.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSpent, isCompleted, isLocked, workday, user);
    }
}
