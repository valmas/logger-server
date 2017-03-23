package com.ntua.ote.logger.core.enums;

public enum LogType {

    CALL(0, "codelist.call"),
    SMS(1, "codelist.sms");

    public int code;
    
    public String label;

    LogType (int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static LogType parseCode(int code){
        switch(code){
            case 0 : return CALL;
            case 1 : return SMS;
        }
        return null;
    }
    
    public String getLabel(){
    	return label;
    }
}
