package it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils;

public enum TipologiaRichiestaEnum {
  MATRIMONIO("PUBBLICAZIONI DI MATRIMONIO"),
  UNIONECIVILE("VERBALE DI UNIONE CIVILE"),
  SEPARAZIONEDIVORZIO("DIVORZI SEPARAZIONI"),
  TRASCRIZIONEMATRIMONIO("TRASCRIZIONE MATRIMONIO ESTERO"),
  TRASCRIZIONESCIGLIOMENTO("TRASCRIZIONE SCIOGLIMENTO ESTERO"),
  PRESIDENTI("Iscrizione Albo Presidente di seggio"),
  SCRUTATORI("Iscrizione Albo Scrutatore di seggio"),
  RINNOVO_PRESIDENTI("Rinnovo Disponibilita Presidenti"),
  RINNOVO_SCRUTATORI("Rinnovo Disponibilita Scrutatore"),
  CANCELLAZIONE_SCRUTATORI("Cancellazione Albo Scrutatori"),
  SEGNALAZIONE_DANNI_BENI_PRIVATI("SEGNALAZIONE DANNI BENI PRIVATI");

  private final String tipologia;

  TipologiaRichiestaEnum(final String text) {
    this.tipologia = text;
  }

  public static TipologiaRichiestaEnum getById(String id) {
    for (TipologiaRichiestaEnum e : values()) {
      if (e.tipologia.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return tipologia;
  }
}
