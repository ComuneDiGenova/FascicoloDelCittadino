package it.liguriadigitale.ponmetro.portale.business.borsedistudio.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
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
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ServiziBorseDiStudioService {

  List<BreadcrumbFdC> getListaBreadcrumbBorseDiStudio();

  List<BreadcrumbFdC> getListaBreadcrumbBorseDiStudioDomanda();

  List<MessaggiInformativi> popolaListaMessaggiBorseDiStudio()
      throws BusinessException, ApiException, IOException;

  List<MessaggiInformativi> popolaListaMessaggiDichiarazioniBorseDiStudio();

  List<MessaggiInformativi> popolaListaMessaggiDichiarazioniBorseDiStudioConLink();

  List<Legenda> getListaLegenda();

  List<Pratica> listaPraticheBorseDiStudio(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  Annualita validitaDomandaBorseDiStudio() throws BusinessException, ApiException, IOException;

  List<StepFdC> getListaStep();

  List<AnnoScolastico> getAnniScolastici() throws BusinessException, ApiException, IOException;

  List<Parentela> getParentele() throws BusinessException, ApiException, IOException;

  List<Categoria> getCategorie() throws BusinessException, ApiException, IOException;

  Categoria getCategoria(String codice) throws BusinessException, ApiException, IOException;

  List<Scuola> getScuole(String codiceCategoria)
      throws BusinessException, ApiException, IOException;

  Scuola getScuola(String codiceScuola, String codiceCategoria)
      throws BusinessException, ApiException, IOException;

  Bambino getBambino(BigDecimal codiceAnnoScolastico, String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  void richiediBorsaStudio(PraticaBorseStudioExtend praticaEstesa)
      throws BusinessException, ApiException, IOException;

  List<Provincia> getPronvice() throws BusinessException, ApiException, IOException;

  List<Provincia> getProvinciaSelect2(String input)
      throws BusinessException, ApiException, IOException;

  List<Comune> getComuni(String codiceProvincia)
      throws BusinessException, ApiException, IOException;

  List<Comune> getComuniSelect2(String codiceProvincia, String input)
      throws BusinessException, ApiException, IOException;

  String getDescrizioneProvinciaDaCodice(String codiceProvincia)
      throws BusinessException, ApiException, IOException;

  String getDescrizioneComuneDaCodice(String codiceProvincia, String codiceComune)
      throws BusinessException, ApiException, IOException;

  FileAllegato getDocumentoBorse(String tipoFile, BigDecimal identificativoDomanda)
      throws BusinessException, ApiException, IOException;

  List<BorsaStudioIBAN> getListaBorseStudioIban(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  void putModificaIbanBorsaStudio(String codiceFiscale, BorsaStudioIBAN datiBorsaStudio)
      throws BusinessException, ApiException, IOException;

  void setDatiIseePerIntestatarioBorsa(
      Residente intestatarioBorsa, PraticaBorseStudioExtend datiDomanda)
      throws BusinessException, ApiException, IOException;
}
