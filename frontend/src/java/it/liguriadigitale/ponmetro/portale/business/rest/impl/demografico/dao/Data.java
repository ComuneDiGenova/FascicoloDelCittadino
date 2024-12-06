package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

import java.util.ArrayList;

public class Data {
  public ArrayList<Person> persons;
  public RegisteredFamilyFromTaxCode registeredFamilyFromTaxCode;

  public ArrayList<Person> getPersons() {
    return persons;
  }

  public void setPersons(ArrayList<Person> persons) {
    this.persons = persons;
  }

  public RegisteredFamilyFromTaxCode getRegisteredFamilyFromTaxCode() {
    return registeredFamilyFromTaxCode;
  }

  public void setRegisteredFamilyFromTaxCode(
      RegisteredFamilyFromTaxCode registeredFamilyFromTaxCode) {
    this.registeredFamilyFromTaxCode = registeredFamilyFromTaxCode;
  }
}
