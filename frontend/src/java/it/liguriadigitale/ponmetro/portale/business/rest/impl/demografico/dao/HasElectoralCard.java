package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao;

public class HasElectoralCard {
  public String identifier;
  public String organization;
  public StartTime startTime;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public StartTime getStartTime() {
    return startTime;
  }

  public void setStartTime(StartTime startTime) {
    this.startTime = startTime;
  }
}
