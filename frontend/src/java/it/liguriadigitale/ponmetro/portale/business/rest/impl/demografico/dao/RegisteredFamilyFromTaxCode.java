package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

import java.util.ArrayList;

public class RegisteredFamilyFromTaxCode {
  public int registeredFamilyId;
  public IsFamilySheetHolderOf isFamilySheetHolderOf;
  public ArrayList<Member> members;

  public int getRegisteredFamilyId() {
    return registeredFamilyId;
  }

  public void setRegisteredFamilyId(int registeredFamilyId) {
    this.registeredFamilyId = registeredFamilyId;
  }

  public IsFamilySheetHolderOf getIsFamilySheetHolderOf() {
    return isFamilySheetHolderOf;
  }

  public void setIsFamilySheetHolderOf(IsFamilySheetHolderOf isFamilySheetHolderOf) {
    this.isFamilySheetHolderOf = isFamilySheetHolderOf;
  }

  public ArrayList<Member> getMembers() {
    return members;
  }

  public void setMembers(ArrayList<Member> members) {
    this.members = members;
  }
}
