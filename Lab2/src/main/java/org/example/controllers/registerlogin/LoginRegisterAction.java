package org.example.controllers.registerlogin;


public enum LoginRegisterAction {
    LOGIN,
    REGISTER,
    BACK_TO_CHOOSE;

    public static LoginRegisterAction getAction(String value){
        if(value == null){
            return null;
        }
        return switch (value){
            case "LOGIN" -> LOGIN;
            case "REGISTER" -> REGISTER;
            case "BACK_TO_CHOOSE" -> BACK_TO_CHOOSE;
            default -> null;
        };
    }

}
