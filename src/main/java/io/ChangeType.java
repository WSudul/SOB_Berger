package io;

import java.util.HashMap;
import java.util.Map;

public enum ChangeType {

    NONE(0),
    SINGLE_ZERO(1),
    SINGLE_ONE(2),
    MULTIPLE_ZEROS(3),
    MULTIPLE_ONES(4),
    MULTIPLE_MIXED(5,false);

    ChangeType(int id){
        this.id=id;
        this.detectable=true;
    }

    ChangeType(int id,boolean detectable){
        this.id=id;
        this.detectable=detectable;
    }

    private static final Map<Integer,ChangeType> map;
    static {
        map = new HashMap<>();
        for (ChangeType type : ChangeType.values()) {
            map.put(type.id, type);
        }
    }
    public static ChangeType findByKey(int i) {
        return map.get(i);
    }


    private final int id;
    private final boolean detectable;

    public int getId() {
        return id;
    }

    public boolean isDetectable() {
        return detectable;
    }

}


