package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

public class Member {
  public Person person;
  public String memberTypeCode;

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getMemberTypeCode() {
    return memberTypeCode;
  }

  public void setMemberTypeCode(String memberTypeCode) {
    this.memberTypeCode = memberTypeCode;
  }
}
