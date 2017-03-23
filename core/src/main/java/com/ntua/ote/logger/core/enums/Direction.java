package com.ntua.ote.logger.core.enums;

public enum Direction {

    INCOMING(0 ,"codelist.incoming"),
    OUTGOING(1 ,"codelist.outgoing");

    public int code;
    
    public String label;

    Direction(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static Direction parseCode(int code){
        switch(code){
            case 0 : return INCOMING;
            case 1 : return OUTGOING;
        }
        return null;
    }
    
    public String getLabel(){
    	return label;
    }
    
    
}
