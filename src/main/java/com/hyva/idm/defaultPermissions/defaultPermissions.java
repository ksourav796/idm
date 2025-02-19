package com.hyva.idm.defaultPermissions;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class defaultPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    public Long id;
    public String keyName;
    public String x_posVal;
    public String y_posVal;
    public String columnOne;
    public String columTwo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getX_posVal() {
        return x_posVal;
    }

    public void setX_posVal(String x_posVal) {
        this.x_posVal = x_posVal;
    }

    public String getY_posVal() {
        return y_posVal;
    }

    public void setY_posVal(String y_posVal) {
        this.y_posVal = y_posVal;
    }

    public String getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public String getColumTwo() {
        return columTwo;
    }

    public void setColumTwo(String columTwo) {
        this.columTwo = columTwo;
    }
}
