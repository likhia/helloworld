package org.jboss.as.quickstarts.helloworld;

public class CountObject implements java.io.Serializable {

    int count = 1;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void increment() {
        count++;
    }
}