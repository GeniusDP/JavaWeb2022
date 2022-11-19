package org.example.entities;

import static org.assertj.core.api.Assertions.*;

import org.example.entities.car.QualityClass;
import org.junit.jupiter.api.Test;

class QualityClassTest {

  @Test
  void getQualityClass() {
    QualityClass basic = QualityClass.getQualityClass("BASIC");
    assertThat(basic).isEqualTo(QualityClass.BASIC);

    QualityClass business = QualityClass.getQualityClass("BUSINESS");
    assertThat(business).isEqualTo(QualityClass.BUSINESS);

    QualityClass premium = QualityClass.getQualityClass("PREMIUM");
    assertThat(premium).isEqualTo(QualityClass.PREMIUM);

    QualityClass nullable = QualityClass.getQualityClass("SOME another text");
    assertThat(nullable).isEqualTo(null);
  }
}