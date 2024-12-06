package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.Minore;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.builder.CedoleMinoreBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzionePrivacyCedoleLibrarie;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.DatiBambinoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.DomandaCedolePanel;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola.StatoDomandaEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;

public class CedoleLibrarieConPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;

  public CedoleLibrarieConPrivacyPage() {
    this(null);
  }

  public CedoleLibrarieConPrivacyPage(UtenteServiziRistorazione iscritto) {
    super();
    log.debug("DomandaCedolePage INIZIO");

    if (iscritto == null) {
      if (getSession().getAttribute("iscrittoCedoleLibrarie") != null) {
        iscritto = (UtenteServiziRistorazione) getSession().getAttribute("iscrittoCedoleLibrarie");
      }
    } else {
      getSession().setAttribute("iscrittoCedoleLibrarie", iscritto);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    CedoleMinore cedolaMinore = getCedolaDaMinore(iscritto);
    DatiBambinoPanel bambinoPanel = new DatiBambinoPanel("bambino", cedolaMinore);
    DomandaCedolePanel panel = new DomandaCedolePanel("domanda", cedolaMinore);
    add(bambinoPanel);
    add(panel);

    LinkDinamicoLaddaFunzionePrivacyCedoleLibrarie<UtenteServiziRistorazione> privacyCedole =
        new LinkDinamicoLaddaFunzionePrivacyCedoleLibrarie<UtenteServiziRistorazione>(
            "privacyCedole",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("CedoleLibrariePage.privacyCedole")
                .setWicketClassName("CedoleLibrariePrivacyPage")
                .setLinkTitleAdditionalText(iscritto.getNome())
                .build(),
            iscritto,
            CedoleLibrarieConPrivacyPage.this,
            "color-orange col-auto icon-salva",
            getUtente().isServiziCedolePrivacyNonAccettata());
    addOrReplace(privacyCedole);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  protected CedoleMinore getCedolaDaMinore(UtenteServiziRistorazione minore) {

    Integer anno = ricavaAnnoScolasticoCorretto();
    CedoleMinore cedolaMinore =
        new CedoleMinoreBuilder().getInstance().addMinore(new Minore(minore)).build();

    try {
      Cedola cedola =
          ServiceLocator.getInstance().getCedoleLibrarie().situazioneCorrenteCedola(minore, anno);
      cedolaMinore.setCedola(cedola);
      if (StatoDomandaEnum.NON_PRESENTATA.toString().equalsIgnoreCase(cedola.getStatoDomanda())) {
        cedolaMinore.setDatiIscrizioneAnnoCorrente(false);
      } else {
        if ((getMeseOggi() > 4 || getMeseOggi() < 10)) {
          correggiIscrizione(cedolaMinore);
        }
      }
    } catch (BusinessException e) {
      cedolaMinore.setErrore(true);
      log.debug("Errore: ", e);
    }
    return cedolaMinore;
  }

  private void correggiIscrizione(CedoleMinore cedolaMinore) {
    // TODO da spostare in un Builder
    Cedola cedola = cedolaMinore.getCedola();
    cedolaMinore
        .getMinore()
        .setCategoriaStrutturaScolastica(cedola.getCategoriaStrutturaScolastica());
    cedolaMinore.getMinore().setStrutturaScolastica(cedola.getScuola());
    cedolaMinore.getMinore().setCodiceScuola("");
    cedolaMinore.getMinore().setClasse(cedola.getClasse());
    cedolaMinore.getMinore().setSezione(cedola.getSezione());
  }

  public static Integer ricavaAnnoScolasticoCorretto() {

    LocalDate oggi = LocalDate.now();
    Integer anno = 0;
    int mese = getMeseOggi();
    if (mese > 4) {
      anno = oggi.getYear();
    } else {
      anno = oggi.getYear() - 1;
    }
    return anno;
  }

  public static int getMeseOggi() {
    LocalDate oggi = LocalDate.now();
    int mese = oggi.getMonthValue();
    return mese;
  }
}
