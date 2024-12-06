package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.io.Serializable;

public class BonificoBancario implements Serializable {

  private static final long serialVersionUID = 165465456487852L;
  private String iban;
  private String nominativoIntestatario;
  private String codiceFiscaleIntestatario;
  private String istituto;
  private String swift;

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public String getNominativoIntestatario() {
    return nominativoIntestatario;
  }

  public void setNominativoIntestatario(String intestatario) {
    this.nominativoIntestatario = intestatario;
  }

  public String getCodiceFiscaleIntestatario() {
    return codiceFiscaleIntestatario;
  }

  public void setCodiceFiscaleIntestatario(String codiceFiscaleIntestatario) {
    this.codiceFiscaleIntestatario = codiceFiscaleIntestatario;
  }

  public String getIstituto() {
    return istituto;
  }

  public void setIstituto(String istituto) {
    this.istituto = istituto;
  }

  public String getSwift() {
    return swift;
  }

  public void setSwift(String codiceSwift) {
    this.swift = codiceSwift;
  }
}
