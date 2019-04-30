//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dripop.core.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Direction DEFAULT_DIRECTION;
    private String property;
    private Direction direction;

    public Order() {
        this.direction = DEFAULT_DIRECTION;
    }

    public Order(String property, Direction direction) {
        this.direction = DEFAULT_DIRECTION;
        this.property = property;
        this.direction = direction;
    }

    public static Order asc(String property) {
        return new Order(property, Direction.asc);
    }

    public static Order desc(String property) {
        return new Order(property, Direction.desc);
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else if(this == obj) {
            return true;
        } else {
            Order other = (Order)obj;
            return (new EqualsBuilder()).append(this.getProperty(), other.getProperty()).append(this.getDirection(), other.getDirection()).isEquals();
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.getProperty()).append(this.getDirection()).toHashCode();
    }

    static {
        DEFAULT_DIRECTION = Direction.desc;
    }
}
