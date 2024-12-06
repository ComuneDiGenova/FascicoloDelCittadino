package it.liguriadigitale.ponmetro.portale.business.rest.impl.borsedistudio;

import it.liguriadigitale.ponmetro.borsestudio.apiclient.BorsedistudioApi;
import it.liguriadigitale.ponmetro.borsestudio.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.borsestudio.model.Annualita;
import it.liguriadigitale.ponmetro.borsestudio.model.Bambino;
import it.liguriadigitale.ponmetro.borsestudio.model.BorsaStudioIBAN;
import it.liguriadigitale.ponmetro.borsestudio.model.Categoria;
import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import it.liguriadigitale.ponmetro.borsestudio.model.FileAllegato;
import it.liguriadigitale.ponmetro.borsestudio.model.Parentela;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import it.liguriadigitale.ponmetro.borsestudio.model.Scuola;
import java.math.BigDecimal;
import java.util.List;

public class ApiBorseDiStudioImpl implements BorsedistudioApi {

  private BorsedistudioApi instance;

  public ApiBorseDiStudioImpl(BorsedistudioApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<AnnoScolastico> getAnniScolastici() {
    return instance.getAnniScolastici();
  }

  @Override
  public Annualita getAnnualita() {
    return instance.getAnnualita();
  }

  @Override
  public Bambino getBambino(BigDecimal arg0, String arg1) {
    return instance.getBambino(arg0, arg1);
  }

  @Override
  public List<Categoria> getCategorie() {
    return instance.getCategorie();
  }

  @Override
  public List<Comune> getComuni(String arg0) {
    return instance.getComuni(arg0);
  }

  @Override
  public List<Parentela> getParentele() {
    return instance.getParentele();
  }

  @Override
  public List<Pratica> getPratiche(String arg0) {
    return instance.getPratiche(arg0);
  }

  @Override
  public List<Provincia> getProvince() {
    return instance.getProvince();
  }

  @Override
  public List<Scuola> getScuole(String arg0) {
    return instance.getScuole(arg0);
  }

  @Override
  public void setPraticaBorseDiStudio(String arg0, Pratica arg1) {
    instance.setPraticaBorseDiStudio(arg0, arg1);
  }

  @Override
  public List<BorsaStudioIBAN> getBorseStudioIban(String arg0) {
    return instance.getBorseStudioIban(arg0);
  }

  @Override
  public void setBorseStudioIban(String arg0, BorsaStudioIBAN arg1) {
    instance.setBorseStudioIban(arg0, arg1);
  }

  @Override
  public FileAllegato getDocumentoBorseDiStudio(String arg0, BigDecimal arg1) {
    return instance.getDocumentoBorseDiStudio(arg0, arg1);
  }
}
