package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "office_work")
public class OfficeWork extends Task {

    public OfficeWork() {
    }

    @Override
    public void setTimeSpent(int timeSpent) {
        super.setTimeSpent(timeSpent);
    }
}
