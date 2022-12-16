package org.example.controllers.registerlogin;


public enum LoginRegisterAction {
    LOGIN,
    REGISTER,
    BACK_TO_CHOOSE;

    public static LoginRegisterAction getAction(String value){
        if(value == null){
            return null;
        }
        switch (value){
            case "LOGIN": return LOGIN;
            case "REGISTER": return REGISTER;
            case "BACK_TO_CHOOSE": return BACK_TO_CHOOSE;
            default: return null;
        }
    }

}
