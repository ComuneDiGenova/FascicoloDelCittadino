package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.pojo;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class CzrmMailCommenti implements Serializable {

  private static final long serialVersionUID = 6691870322095447259L;

  private EnumCzrmMailCommenti tipologia;

  private OffsetDateTime data;

  private String oggetto;

  private String body;

  private String emailMessage;

  public EnumCzrmMailCommenti getTipologia() {
    return tipologia;
  }

  public void setTipologia(EnumCzrmMailCommenti tipologia) {
    this.tipologia = tipologia;
  }

  public OffsetDateTime getData() {
    return data;
  }

  public void setData(OffsetDateTime data) {
    this.data = data;
  }

  public String getOggetto() {
    return oggetto;
  }

  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getEmailMessage() {
    return emailMessage;
  }

  public void setEmailMessage(String emailMessage) {
    this.emailMessage = emailMessage;
  }

  @Override
  public String toString() {
    return "CzrmMailCommenti [tipologia="
        + tipologia
        + ", data="
        + data
        + ", oggetto="
        + oggetto
        + ", body="
        + body
        + ", emailMessage="
        + emailMessage
        + "]";
  }
}
