package it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.utils;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class DatiTrasportoBambiniDisabili implements Serializable {

  private static final long serialVersionUID = 484228343728009956L;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getOwnerId() {
    return OwnerId;
  }

  public void setOwnerId(String ownerId) {
    OwnerId = ownerId;
  }

  public boolean isIsDeleted() {
    return IsDeleted;
  }

  public void setIsDeleted(boolean isDeleted) {
    IsDeleted = isDeleted;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public OffsetDateTime getCreatedDate() {
    return CreatedDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    CreatedDate = createdDate;
  }

  public String getCreatedbyId() {
    return CreatedbyId;
  }

  public void setCreatedbyId(String createdbyId) {
    CreatedbyId = createdbyId;
  }

  public OffsetDateTime getLastModifiedDate() {
    return LastModifiedDate;
  }

  public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
    LastModifiedDate = lastModifiedDate;
  }

  public String getLastModifiedById() {
    return LastModifiedById;
  }

  public void setLastModifiedById(String lastModifiedById) {
    LastModifiedById = lastModifiedById;
  }

  public OffsetDateTime getSystemModeStamp() {
    return SystemModeStamp;
  }

  public void setSystemModeStamp(OffsetDateTime systemModeStamp) {
    SystemModeStamp = systemModeStamp;
  }

  public OffsetDateTime getLastViewedDate() {
    return LastViewedDate;
  }

  public void setLastViewedDate(OffsetDateTime lastViewedDate) {
    LastViewedDate = lastViewedDate;
  }

  public OffsetDateTime getLastReferencedDate() {
    return LastReferencedDate;
  }

  public void setLastReferencedDate(OffsetDateTime lastReferencedDate) {
    LastReferencedDate = lastReferencedDate;
  }

  public String getNote__c() {
    return Note__c;
  }

  public void setNote__c(String note__c) {
    Note__c = note__c;
  }

  public String getURL_Pubblico_Pdf__c() {
    return URL_Pubblico_Pdf__c;
  }

  public void setURL_Pubblico_Pdf__c(String uRL_Pubblico_Pdf__c) {
    URL_Pubblico_Pdf__c = uRL_Pubblico_Pdf__c;
  }

  private String Id;

  private String OwnerId;

  private boolean IsDeleted;

  private String Name;

  private OffsetDateTime CreatedDate;

  private String CreatedbyId;

  private OffsetDateTime LastModifiedDate;

  private String LastModifiedById;

  private OffsetDateTime SystemModeStamp;

  private OffsetDateTime LastViewedDate;

  private OffsetDateTime LastReferencedDate;

  private String Note__c;

  private String URL_Pubblico_Pdf__c;
}
