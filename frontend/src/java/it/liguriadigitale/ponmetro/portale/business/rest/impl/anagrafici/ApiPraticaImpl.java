package it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.PraticaApi;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDichiarazionePrecompilataResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDocumentiPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoIndividuiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoParenteleResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoPraticheResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoStatiPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoStatoCivileResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoTitolaritaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetPareriPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProfessioniResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProvenienzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoIscrizioneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTitoliStudioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoAllegatiRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoAllegatiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostInserimentoPraticaSospesaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PutCambioStatoPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.StringGenericResponse;

public class ApiPraticaImpl implements PraticaApi {

  private PraticaApi instance;

  public ApiPraticaImpl(PraticaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public StringGenericResponse delPratica(Integer arg0) {
    return instance.delPratica(arg0);
  }

  @Override
  public GetDatiGeneraliPraticaResponseGenericResponse getDatiGeneraliPratica(Integer arg0) {
    return instance.getDatiGeneraliPratica(arg0);
  }

  @Override
  public GetDocumentiPraticaResponseGenericResponse getDocumentiPratica(
      Integer arg0, Boolean arg1) {
    return instance.getDocumentiPratica(arg0, arg1);
  }

  @Override
  public GetElencoIndividuiResponseGenericResponse getElencoIndividui(Integer arg0) {
    return instance.getElencoIndividui(arg0);
  }

  @Override
  public GetElencoParenteleResponseGenericResponse getElencoParentele() {
    return instance.getElencoParentele();
  }

  @Override
  public GetElencoPraticheResponseGenericResponse getElencoPratiche(String arg0) {
    return instance.getElencoPratiche(arg0);
  }

  @Override
  public GetElencoStatoCivileResponseGenericResponse getElencoStatoCivile() {
    return instance.getElencoStatoCivile();
  }

  @Override
  public GetElencoTitolaritaResponseGenericResponse getElencoTitolarita() {
    return instance.getElencoTitolarita();
  }

  @Override
  public GetPareriPraticaResponseGenericResponse getPareriPratica(Integer arg0) {
    return instance.getPareriPratica(arg0);
  }

  @Override
  public GetProfessioniResponseGenericResponse getProfessioni() {
    return instance.getProfessioni();
  }

  @Override
  public GetProvenienzaResponseGenericResponse getProvenienza() {
    return instance.getProvenienza();
  }

  @Override
  public GetTipoIscrizioneResponseGenericResponse getTipoIscrizione(Integer arg0) {
    return instance.getTipoIscrizione(arg0);
  }

  @Override
  public GetTitoliStudioResponseGenericResponse getTitoliStudio() {
    return instance.getTitoliStudio();
  }

  @Override
  public PostInserimentoAllegatiResponseGenericResponse postInserimentoAllegati(
      PostInserimentoAllegatiRequest arg0) {
    return instance.postInserimentoAllegati(arg0);
  }

  @Override
  public PostInserimentoPraticaSospesaResponseGenericResponse postInserimentoPraticaSospesa(
      PostInserimentoPraticaSospesaRequest arg0) {
    return instance.postInserimentoPraticaSospesa(arg0);
  }

  @Override
  public StringGenericResponse delAllegato(Integer arg0) {
    return instance.delAllegato(arg0);
  }

  @Override
  public GetElencoStatiPraticaResponseGenericResponse getElencoStatiPratica(Integer arg0) {
    return instance.getElencoStatiPratica(arg0);
  }

  @Override
  public PutCambioStatoPraticaResponseGenericResponse putCambioStatoPratica(Integer arg0) {
    return instance.putCambioStatoPratica(arg0);
  }

  @Override
  public GetDichiarazionePrecompilataResponseGenericResponse getDichiarazionePrecompilata(
      Integer arg0, String arg1) {
    return instance.getDichiarazionePrecompilata(arg0, arg1);
  }
}
