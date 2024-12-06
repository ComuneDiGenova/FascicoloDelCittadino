package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class DichiarazioniPermessiPersonalizzati implements Serializable {

  private static final long serialVersionUID = -1615706336403043384L;

  LocalDate marcaBolloData;
  String marcaBolloNumero;

  List<DichiarazionePermessiPersonalizzati> listaDichiarazioni;

  public List<DichiarazionePermessiPersonalizzati> getListaDichiarazioni() {
    return listaDichiarazioni;
  }

  public void setListaDichiarazioni(List<DichiarazionePermessiPersonalizzati> listaDichiarazioni) {
    this.listaDichiarazioni = listaDichiarazioni;
  }

  public LocalDate getMarcaBolloData() {
    return marcaBolloData;
  }

  public void setMarcaBolloData(LocalDate marcaBolloData) {
    this.marcaBolloData = marcaBolloData;
  }

  public String getMarcaBolloNumero() {
    return marcaBolloNumero;
  }

  public void setMarcaBolloNumero(String marcaBolloNumero) {
    this.marcaBolloNumero = marcaBolloNumero;
  }
}
