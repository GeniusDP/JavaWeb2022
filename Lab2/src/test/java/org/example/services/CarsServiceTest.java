package org.example.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.example.entities.car.Car;
import org.example.entities.car.Mark;
import org.example.entities.car.QualityClass;
import org.example.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarsServiceTest {

  private CarsService carsService;

  @BeforeEach
  public void init() {

    Mark lexus = new Mark(1L, "Lexus");
    Mark bmw = new Mark(2L, "BMW");
    Mark ford = new Mark(3L, "Ford");

    Car lexus1 = Car.builder()
      .id(1L)
      .name("Lexus 1")
      .basePrice(10_000)
      .mark(lexus)
      .qualityClass(QualityClass.BASIC)
      .build();
    Car lexus2 = Car.builder()
      .id(2L)
      .name("Lexus 2")
      .basePrice(20_000)
      .mark(lexus)
      .qualityClass(QualityClass.BUSINESS)
      .build();
    Car lexus3 = Car.builder()
      .id(3L)
      .name("Lexus 3")
      .basePrice(30_000)
      .mark(lexus)
      .qualityClass(QualityClass.PREMIUM)
      .build();
    Car bmw1 = Car.builder()
      .id(4L)
      .name("BMW 1")
      .basePrice(40_000)
      .mark(bmw)
      .qualityClass(QualityClass.BASIC)
      .build();

    Car ford1 = Car.builder()
      .id(5L)
      .name("Ford 1")
      .basePrice(50_000)
      .mark(ford)
      .qualityClass(QualityClass.BASIC)
      .build();

    List<Car> cars = List.of(lexus1, lexus2, lexus3, bmw1, ford1);

    CarRepository carRepository = Mockito.mock(CarRepository.class);

    Mockito.doReturn(cars).when(carRepository).findAll();

    Mockito.doAnswer(invocation -> {
      Long id = invocation.getArgument(0, Long.class);
      Optional<Car> first = cars.stream().filter(c -> c.getId().equals(id)).findFirst();
      return first.orElse(null);
    }).when(carRepository).findById(any(long.class));

    Mockito.doAnswer(invocation -> {
      String markName = invocation.getArgument(0, String.class);
      return cars.stream().filter(c -> c.getMark().getName().equals(markName)).collect(Collectors.toList());
    }).when(carRepository).findAllByMarkName(any(String.class));

    Mockito.doAnswer(invocation -> {
      QualityClass qualityClass = invocation.getArgument(0, QualityClass.class);
      return cars.stream().filter(c -> c.getQualityClass().equals(qualityClass)).collect(Collectors.toList());
    }).when(carRepository).findAllByQualityClass(any(QualityClass.class));

    Mockito.doAnswer(invocation -> {
      String name = invocation.getArgument(0, String.class);
      return cars.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }).when(carRepository).findByName(any(String.class));

    Mockito.doReturn(cars.stream().sorted(Comparator.comparing(Car::getName)).collect(Collectors.toList()))
      .when(carRepository).findAllByNameOrdered();

    Mockito.doReturn(cars.stream().sorted(Comparator.comparing(Car::getBasePrice)).collect(Collectors.toList()))
      .when(carRepository).findAllByPriceOrdered();

    carsService = new CarsService(carRepository);
  }

  @Test
  void getCarsByMark() {
    List<Car> byMarkLexus = carsService.getCarsByMark("Lexus");
    Assertions.assertThat(byMarkLexus.size()).isEqualTo(3);
  }

  @Test
  void getCarsByQualityClass() {
    List<Car> business = carsService.getCarsByQualityClass("BUSINESS");
    Assertions.assertThat(business.size()).isEqualTo(1);
  }

  @Test
  void getCarsSortedByPrice() {
    List<Car> sortedByPrice = carsService.getCarsSortedByPrice();
    List<Car> expected = sortedByPrice.stream().sorted(Comparator.comparing(Car::getBasePrice)).collect(Collectors.toList());
    Assertions.assertThat(sortedByPrice).isEqualTo(expected);
  }

  @Test
  void getCarsSortedByName() {
    List<Car> sortedByName = carsService.getCarsSortedByName();
    List<Car> expected = sortedByName.stream().sorted(Comparator.comparing(Car::getName)).collect(Collectors.toList());
    Assertions.assertThat(sortedByName).isEqualTo(expected);
  }

  @Test
  void existsById() {
    Assertions.assertThat(carsService.existsById(1L)).isTrue();
    Assertions.assertThat(carsService.existsById(2L)).isTrue();
    Assertions.assertThat(carsService.existsById(100L)).isFalse();
  }

  @Test
  void getCarById() {
    Car carById = carsService.getCarById(1L);
    Assertions.assertThat(carById.getName()).isEqualTo("Lexus 1");
    Car carByIdNotFound = carsService.getCarById(100L);
    Assertions.assertThat(carByIdNotFound).isNull();
  }

  @Test
  void existsByName() {
    Assertions.assertThat(carsService.existsByName("Ford 1")).isTrue();
    Assertions.assertThat(carsService.existsByName("Lexus 2")).isTrue();
    Assertions.assertThat(carsService.existsByName("BMW 1")).isTrue();
    Assertions.assertThat(carsService.existsByName("Ferrari 5")).isFalse();
  }

}