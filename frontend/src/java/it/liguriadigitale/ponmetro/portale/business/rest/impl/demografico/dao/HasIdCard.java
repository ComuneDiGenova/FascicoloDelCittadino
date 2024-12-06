package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

public class HasIdCard {
  public String identifier;
  public boolean electronicID;
  public StartTime startTime;
  public String organization;
  public EndTime endTime;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public boolean isElectronicID() {
    return electronicID;
  }

  public void setElectronicID(boolean electronicID) {
    this.electronicID = electronicID;
  }

  public StartTime getStartTime() {
    return startTime;
  }

  public void setStartTime(StartTime startTime) {
    this.startTime = startTime;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public EndTime getEndTime() {
    return endTime;
  }

  public void setEndTime(EndTime endTime) {
    this.endTime = endTime;
  }
}
