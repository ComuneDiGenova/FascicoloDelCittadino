package it.liguriadigitale.ponmetro.test;

import java.time.LocalDate;

public class VacMain {

  public static void main(String[] args) {
    // TODO Auto-generated method stub

    LocalDate giornoVaccino = LocalDate.of(2021, 6, 7);
    LocalDate terzaDose = giornoVaccino.plusDays(150);
    System.out.println("terzaDose:" + terzaDose);
  }
}
