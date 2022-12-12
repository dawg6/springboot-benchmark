package com.dawg6.benchmark.data;

import java.util.Random;
import java.util.UUID;

public class BenchmarkBean {
    private String aString;
    private int aNumber;
    private String[] aList;

    public BenchmarkBean() { }

    public String getaString() {
        return aString;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public int getaNumber() {
        return aNumber;
    }

    public void setaNumber(int aNumber) {
        this.aNumber = aNumber;
    }

    public String[] getaList() {
        return aList;
    }

    public void setaList(String[] aList) {
        this.aList = aList;
    }

    public void initialize() {
        var rand = new Random(420);
        setaNumber(rand.nextInt(Integer.MAX_VALUE));
        setaString(UUID.randomUUID().toString());
        var list = new String[100];
        for (var n = 0; n < list.length; n++) {
            list[n] = UUID.randomUUID().toString();
        }
        setaList(list);
    }
}
