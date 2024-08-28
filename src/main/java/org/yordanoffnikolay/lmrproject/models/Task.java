package org.yordanoffnikolay.lmrproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private int timeSpent = 1;

    @Column(name = "is_completed", columnDefinition = "boolean default false")
    private boolean isCompleted;

    @Column(name = "is_locked", columnDefinition = "boolean default false")
    private boolean isLocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workday_id")
    @JsonBackReference
    private Workday workday;

    public Task() {
    }

    public long getTaskId() {
        return taskId;
    }
    public Workday getWorkday() {
        return workday;
    }
    public int getTimeSpent() {
        return timeSpent;
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public boolean isLocked() {
        return isLocked;
    }

    public void setCompleted(boolean completed) {
        isCompleted = false;
    }
    public void setLocked(boolean locked) {
        isLocked = false;
    }
    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }
    public void setWorkday(Workday workday) {
        this.workday = workday;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return timeSpent == task.timeSpent
                && isCompleted == task.isCompleted
                && isLocked == task.isLocked
                && Objects.equals(workday, task.workday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSpent, isCompleted, isLocked, workday);
    }
}
