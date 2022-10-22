package org.example.entities;

public enum QualityClass {
  BASIC,
  BUSINESS,
  PREMIUM;

  public static QualityClass getQualityClass(String value){
    return switch (value){
      case "BASIC" -> BASIC;
      case "BUSINESS" -> BUSINESS;
      case "PREMIUM" -> PREMIUM;
      default -> null;
    };
  }

}
