package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

public interface CostantiBaseDatiScadenzePersonalizzate {

  // TODO trasformare in ENUM

  public static final String BATCH_SCADENZE = "BATCH_SCADENZE";
  public static final long STATO_PUBBLICATA = 2L;
  public static final long STATO_TOLTA_DALLA_PUBBLICAZIONE = 3L;

  public static final long SCADENZA_CIE = 1L;
  public static final long SCADENZA_BOLLO = 2L;
  public static final long SCADENZA_ASSICURAZIONE = 3L;
  public static final long SCADENZA_REVISIONE = 4L;
  public static final long SCADENZA_BIBLIOTECA = 5L;
  public static final long SCADENZA_ABBONAMENTI_AMT = 6L;
  public static final long SCADENZA_PERMESSI_GENOVA_PARCHEGGI = 7L;

  public static final String TESTO_SCADENZA_ASS_AUTO = "SCADENZA_ASS_AUTO";
  public static final String TESTO_SCADENZA_REV_AUTO = "SCADENZA_REV_AUTO";
  public static final String TESTO_SCADENZA_CIE = "SCADENZA_CIE";
  public static final String TESTO_SCADENZA_BOLLO = "SCADENZA_BOLLO_AUTO";
  public static final String TESTO_SCADENZE_BIBLIOTECA = "SCADENZA_BIBLIOTECA";
  public static final String TESTO_SCADENZA_ABBONAMENTI_AMT = "SCADENZA_ABBONAMENTI_AMT";
  public static final String TESTO_SCADENZA_PERMESSI_GENOVA_PARCHEGGI =
      "SCADENZA_PERMESSI_GENOVA_PARCHEGGI";
}
