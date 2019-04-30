package com.dripop.core.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pageable implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Integer NO_PAGE = 0;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 1000;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String orderProperty;
    private Direction orderDirection;
    private String countKeyField;
    private List<Order> orders = new ArrayList();

    public Pageable() {
    }

    public Pageable(Integer pageNumber) {
        if(pageNumber != null && pageNumber.intValue() >= 1) {
            this.pageNumber = pageNumber.intValue();
        }
    }

    public Pageable(Integer pageNumber, Integer pageSize) {
        if(pageNumber != null && pageNumber.intValue() >= 1) {
            this.pageNumber = pageNumber.intValue();
        }

        if(pageSize != null && pageSize.intValue() >= 1 && pageSize.intValue() <= 1000) {
            this.pageSize = pageSize.intValue();
        }

    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getStartRow() {
        return (this.getPageNumber() - 1) * this.getPageSize();
    }

    public void setPageNumber(int pageNumber) {
        if(pageNumber < 1) {
            pageNumber = 1;
        }

        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize < NO_PAGE || pageSize > 1000) {
            pageSize = 10;
        }

        this.pageSize = pageSize;
    }

    public String getOrderProperty() {
        return this.orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public Direction getOrderDirection() {
        return this.orderDirection;
    }

    public void setOrderDirection(Direction orderDirection) {
        this.orderDirection = orderDirection;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getCountKeyField() {
        return this.countKeyField;
    }

    public void setCountKeyField(String countKeyField) {
        this.countKeyField = countKeyField;
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else if(this == obj) {
            return true;
        } else {
            Pageable other = (Pageable)obj;
            return (new EqualsBuilder()).append(this.getPageNumber(), other.getPageNumber()).append(this.getPageSize(), other.getPageSize()).append(this.getOrderProperty(), other.getOrderProperty()).append(this.getOrderDirection(), other.getOrderDirection()).append(this.getOrders(), other.getOrders()).isEquals();
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.getPageNumber()).append(this.getPageSize()).append(this.getOrderProperty()).append(this.getOrderDirection()).append(this.getOrders()).toHashCode();
    }
}
