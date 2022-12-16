package org.example.entities.car;

public enum QualityClass {
  BASIC,
  BUSINESS,
  PREMIUM;

  public static QualityClass getQualityClass(String value){
    if(value == null){
      return null;
    }
    switch (value){
      case "BASIC": return BASIC;
      case "BUSINESS": return BUSINESS;
      case "PREMIUM": return PREMIUM;
      default: return null;
    }
  }

}
