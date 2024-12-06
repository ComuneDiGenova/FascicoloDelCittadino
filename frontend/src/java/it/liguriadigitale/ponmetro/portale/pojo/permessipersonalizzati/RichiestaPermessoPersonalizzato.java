package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Protocollazione;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.io.Serializable;
import java.util.List;

public class RichiestaPermessoPersonalizzato implements Serializable {

  private static final long serialVersionUID = -1615706336403043384L;

  SintesiPermessiPersonalizzati sintesiPermessiPersonalizzati;
  // STEP1
  TipologiaProcedimento tipoDomanda;
  // STEP2
  SoggettiCoinvolti soggettiCoinvolti;
  // STEP3
  FeaturesGeoserver geoserverUbicazioneParcheggio;

  DichiarazioniPermessiPersonalizzati dichiarazioni;

  DocumentiAllegati documentiAllegati;

  List<AllegatoPermessiPersonalizzati> listaAllegati;

  Protocollazione protocollazione;

  public RichiestaPermessoPersonalizzato() {

    soggettiCoinvolti = new SoggettiCoinvolti();
    dichiarazioni = new DichiarazioniPermessiPersonalizzati();
  }

  public FeaturesGeoserver getGeoserverUbicazioneParcheggio() {
    return geoserverUbicazioneParcheggio;
  }

  public void setGeoserverUbicazioneParcheggio(FeaturesGeoserver geoserverUbicazioneParcheggio) {
    this.geoserverUbicazioneParcheggio = geoserverUbicazioneParcheggio;
  }

  public TipologiaProcedimento getTipoDomanda() {
    return tipoDomanda;
  }

  public void setTipoDomanda(TipologiaProcedimento tipoDomanda) {
    this.tipoDomanda = tipoDomanda;
  }

  public SoggettiCoinvolti getSoggettiCoinvolti() {
    return soggettiCoinvolti;
  }

  public void setSoggettiCoinvolti(SoggettiCoinvolti soggettiCoinvolti) {
    this.soggettiCoinvolti = soggettiCoinvolti;
  }

  public DocumentiAllegati getDocumentiAllegati() {
    return documentiAllegati;
  }

  public void setDocumentiAllegati(DocumentiAllegati documentiAllegati) {
    this.documentiAllegati = documentiAllegati;
  }

  public DichiarazioniPermessiPersonalizzati getDichiarazioni() {
    return dichiarazioni;
  }

  public void setDichiarazioni(DichiarazioniPermessiPersonalizzati dichiarazioni) {
    this.dichiarazioni = dichiarazioni;
  }

  public List<AllegatoPermessiPersonalizzati> getListaAllegati() {
    return listaAllegati;
  }

  public void setListaAllegati(List<AllegatoPermessiPersonalizzati> listaAllegati) {
    this.listaAllegati = listaAllegati;
  }

  public Protocollazione getProtocollazione() {
    return protocollazione;
  }

  public void setProtocollazione(Protocollazione protocollazione) {
    this.protocollazione = protocollazione;
  }

  public SintesiPermessiPersonalizzati getSintesiPermessiPersonalizzati() {
    return sintesiPermessiPersonalizzati;
  }

  public void setSintesiPermessiPersonalizzati(
      SintesiPermessiPersonalizzati sintesiPermessiPersonalizzati) {
    this.sintesiPermessiPersonalizzati = sintesiPermessiPersonalizzati;
  }
}
