package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

import java.util.ArrayList;

public class Person {
  public String givenName;
  public String familyName;
  public String birthPlace;
  public String citizenship;
  public DateOfBirth dateOfBirth;
  public String taxCode;
  public String sex;
  public String maritalStatus;
  public ResidentIn residentIn;
  public ArrayList<HasIdCard> hasIdCard;
  public ArrayList<HasElectoralCard> hasElectoralCard;
  public HasElectoralSection hasElectoralSection;

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getBirthPlace() {
    return birthPlace;
  }

  public void setBirthPlace(String birthPlace) {
    this.birthPlace = birthPlace;
  }

  public String getCitizenship() {
    return citizenship;
  }

  public void setCitizenship(String citizenship) {
    this.citizenship = citizenship;
  }

  public DateOfBirth getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(DateOfBirth dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public ResidentIn getResidentIn() {
    return residentIn;
  }

  public void setResidentIn(ResidentIn residentIn) {
    this.residentIn = residentIn;
  }

  public ArrayList<HasIdCard> getHasIdCard() {
    return hasIdCard;
  }

  public void setHasIdCard(ArrayList<HasIdCard> hasIdCard) {
    this.hasIdCard = hasIdCard;
  }

  public ArrayList<HasElectoralCard> getHasElectoralCard() {
    return hasElectoralCard;
  }

  public void setHasElectoralCard(ArrayList<HasElectoralCard> hasElectoralCard) {
    this.hasElectoralCard = hasElectoralCard;
  }

  public HasElectoralSection getHasElectoralSection() {
    return hasElectoralSection;
  }

  public void setHasElectoralSection(HasElectoralSection hasElectoralSection) {
    this.hasElectoralSection = hasElectoralSection;
  }
}
