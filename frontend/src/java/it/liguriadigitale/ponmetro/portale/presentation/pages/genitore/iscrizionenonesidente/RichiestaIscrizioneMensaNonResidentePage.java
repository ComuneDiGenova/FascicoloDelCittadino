package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiFiglioExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiGenitoreExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiIscrizioneMensaNonResidente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.panel.RichiestaIscrizioneMensaNonResidentePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafeGenitore;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDipartimentaliBambino;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import org.apache.wicket.model.CompoundPropertyModel;

public class RichiestaIscrizioneMensaNonResidentePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3259955626818843997L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaIscrizioneMensaNonResidentePage(UtenteServiziRistorazione iscritto) {
    super();

    if (iscritto == null) {
      if (getSession().getAttribute("iscrizioneRistorazioneNonResidente") != null) {
        iscritto =
            (UtenteServiziRistorazione)
                getSession().getAttribute("iscrizioneRistorazioneNonResidente");
      }
    } else {
      getSession().setAttribute("iscrizioneRistorazioneNonResidente", iscritto);
    }

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiIscrizioneMensaNonResidente datiIscrizione = new DatiIscrizioneMensaNonResidente();

    DatiGenitoreExtend datiGenitoreExtend = getDatiGenitoreExtend();
    datiIscrizione.setDatiGenitore(datiGenitoreExtend);

    DatiFiglioExtend datiFiglioExtend = getDatiFiglioExted(iscritto);
    datiIscrizione.setDatiBambino(datiFiglioExtend);

    CompoundPropertyModel<DatiIscrizioneMensaNonResidente> datiCompleti =
        new CompoundPropertyModel<>(datiIscrizione);

    RichiestaIscrizioneMensaNonResidentePanel iscrizioneNonResidentePanel =
        new RichiestaIscrizioneMensaNonResidentePanel("iscrizioneNonResidentePanel", datiCompleti);
    addOrReplace(iscrizioneNonResidentePanel);

    setOutputMarkupId(true);
  }

  private DatiAnagrafeGenitore getDatiGenitore() {
    DatiAnagrafeGenitore datiGenitore = null;
    try {
      datiGenitore =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDatiAnagraficiGenitore(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore dati genitore: " + e.getMessage(), e);
    }
    return datiGenitore;
  }

  private DatiGenitoreExtend getDatiGenitoreExtend() {
    return ServiceLocator.getInstance()
        .getServiziRistorazione()
        .getDatiGenitoreExted(getUtente(), getDatiGenitore());
  }

  private DatiDipartimentaliBambino getDatiFiglio(String codiceFiscaleIscritto) {
    DatiDipartimentaliBambino datiFiglio = null;
    try {
      datiFiglio =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDatiAnagraficiFiglio(codiceFiscaleIscritto);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore dati figlio: " + e.getMessage(), e);
    }
    return datiFiglio;
  }

  private DatiFiglioExtend getDatiFiglioExted(UtenteServiziRistorazione iscritto) {
    DatiDipartimentaliBambino datiFiglio = getDatiFiglio(iscritto.getCodiceFiscale());
    return ServiceLocator.getInstance()
        .getServiziRistorazione()
        .getDatiFiglioExted(
            iscritto.getCodiceFiscale(),
            iscritto.getNome(),
            iscritto.getCognome(),
            iscritto.getDataNascita(),
            datiFiglio);
  }
}
