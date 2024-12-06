package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.UtenteServiziRistorazioneChoiceRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.panel.CalendarioMensaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.panel.ConsigliCenaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.panel.MenuMensaPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPresenzaServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentUpdatingBehavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class PresenzeInMensaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1607952561006213777L;
  private LocalDate day;
  private MenuMensaPanel panelMenu;

  public PresenzeInMensaPage() {
    this(null, LocalDate.now());
  }

  public PresenzeInMensaPage(UtenteServiziRistorazione iscritto) {
    this(iscritto, LocalDate.now());
  }

  public PresenzeInMensaPage(UtenteServiziRistorazione iscritto, LocalDate day) {
    super();
    init(iscritto, day);
  }

  // init method with initial checks and initializations
  @SuppressWarnings({"unchecked", "rawtypes"})
  private void init(UtenteServiziRistorazione iscritto, LocalDate day) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    this.day = day;
    if (getUtente() == null) {
      throw new RestartResponseAtInterceptPageException(UtenteNonAutorizzatoPage.class);
    }

    if (iscritto == null && getUtente().hasFigliIscrittiAMensa()) {
      // se non specificato l'iscritto prendi il primo (se ho figli
      // iscritti a mensa)
      iscritto = getUtente().getPrimoIscrittoConLinkVisibile();
    } else if (iscritto == null) {
      // se qui non ho iscritto allora > errore
      throw new RestartResponseAtInterceptPageException(UtenteNonAutorizzatoPage.class);
    }

    List<DatiPresenzaServiziRistorazione> listaPresenze = getDatiPresenza(iscritto);
    ConsigliCenaPanel panelConsigli = new ConsigliCenaPanel(iscritto);
    CalendarioMensaPanel panelCalendario = new CalendarioMensaPanel(iscritto, day, listaPresenze);

    panelMenu = new MenuMensaPanel(iscritto, day);
    panelConsigli.setVisible(false);
    creaComboFigli(iscritto);

    add(panelConsigli);
    add(panelCalendario);
    add(panelMenu);
    add(new Label("mese", calcolaMeseCorrente()));
    add(new Label("totale", contaGiorniPresenza(listaPresenze)));
    addElencoIscrizioniFigli();
  }

  private void addElencoIscrizioniFigli() {
    List<UtenteServiziRistorazione> listaFigli = getUtente().getListaFigli();
    List<UtenteServiziRistorazione> listaFigliMensa = getListaFigliMensa(listaFigli);

    int numeroIscritti = 0;
    Boolean tutteFemmine = true;
    String suffissoInBaseAlSesso = "";
    String testoIscritti = "";
    String nomiIscritti = "";

    for (UtenteServiziRistorazione figlio : listaFigliMensa) {
      log.debug(
          "UtenteServiziRistorazioneUtenteServiziRistorazioneUtenteServiziRistorazione: " + figlio);
      if (figlio.getSesso().equalsIgnoreCase("M")) {
        tutteFemmine = false;
      }
      if (numeroIscritti > 0) {
        nomiIscritti += " e ";
      }
      nomiIscritti += figlio.getNome();
      numeroIscritti++;
    }

    if (numeroIscritti == 1) {
      if (tutteFemmine) {
        suffissoInBaseAlSesso = " è iscritta";
      } else {
        suffissoInBaseAlSesso = " è iscritto";
      }
    } else if (numeroIscritti > 1) {
      if (tutteFemmine) {
        suffissoInBaseAlSesso = " sono iscritte";
      } else {
        suffissoInBaseAlSesso = " sono iscritti";
      }
    }
    testoIscritti = nomiIscritti.concat(suffissoInBaseAlSesso);
    add(new Label("figliIscritti", testoIscritti));
  }

  private void addElencoIscrizioniFigliOld() {
    List<UtenteServiziRistorazione> listaFigli = getUtente().getListaFigli();
    List<UtenteServiziRistorazione> listaFigliMensa = getListaFigliMensa(listaFigli);
    List<UtenteServiziRistorazione> listaIscritti = new ArrayList<UtenteServiziRistorazione>();
    List<String> listaNonIscritti = new ArrayList<>();

    for (UtenteServiziRistorazione figlio : listaFigli) {
      if (figlio
              .getStatoIscrizioneServiziRistorazione()
              .equalsIgnoreCase(
                  UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
                      .value())
          && figlio.getBambinoMangia()
          && figlio.getScuolaConMensa()) {
        listaNonIscritti.add(figlio.getNome());
      } else {
        listaIscritti.add(figlio);
      }
    }
    String testoIscritti = "";

    String sessoFiglio = "";
    if (listaIscritti.size() == 1) {
      if (listaIscritti.get(0).getSesso().equalsIgnoreCase("F")) {
        sessoFiglio = " è iscritta";
      } else {
        sessoFiglio = " è iscritto";
      }
      testoIscritti = listaIscritti.get(0).getNome().concat(sessoFiglio);
    } else if (listaIscritti.size() > 1) {
      String sessoFigli = " sono iscritti";
      for (UtenteServiziRistorazione nome : listaFigliMensa) {
        testoIscritti = testoIscritti + nome.getNome() + " e ";
      }

      for (UtenteServiziRistorazione nome : listaFigliMensa) {
        if (nome.getSesso().equalsIgnoreCase("M")) {
          break;
        }
        sessoFigli = " sono iscritte";
      }

      testoIscritti = testoIscritti.substring(0, testoIscritti.length() - 3).concat(sessoFigli);
    }
    add(new Label("figliIscritti", testoIscritti));
  }

  private String calcolaMeseCorrente() {
    return day.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN) + " " + day.getYear();
  }

  private void creaComboFigli(UtenteServiziRistorazione iscrizione) {

    IModel<UtenteServiziRistorazione> modello = new Model<>(iscrizione);
    List<UtenteServiziRistorazione> listaFigli = getUtente().getListaFigli();
    List<UtenteServiziRistorazione> listaFigliMensa = getListaFigliMensa(listaFigli);
    final DropDownChoice<UtenteServiziRistorazione> comboFigli =
        creaComboSceltaFiglio(modello, listaFigliMensa);
    add(comboFigli);
  }

  @SuppressWarnings("unchecked")
  private DropDownChoice<UtenteServiziRistorazione> creaComboSceltaFiglio(
      IModel<UtenteServiziRistorazione> modello, List<UtenteServiziRistorazione> listaFigliMensa) {

    DropDownChoice<UtenteServiziRistorazione> dropdown =
        new DropDownChoice<UtenteServiziRistorazione>(
            "comboFigli",
            modello,
            new ComboLoadableDetachableModel(listaFigliMensa),
            new UtenteServiziRistorazioneChoiceRenderer());

    dropdown.add(
        new FormComponentUpdatingBehavior() {

          private static final long serialVersionUID = -8418621730334667906L;

          @Override
          protected void onUpdate() {

            setResponsePage(new PresenzeInMensaPage(dropdown.getModelObject(), day));
          }

          @Override
          protected void onError(RuntimeException ex) {
            super.onError(ex);
          }
        });

    // @Override
    // protected boolean wantOnSelectionChangedNotifications() {
    // return true;
    // }
    //
    // @Override
    // protected void onSelectionChanged(final UtenteServiziRistorazione
    // newSelection) {
    // setResponsePage(new PresenzeInMensaPage(newSelection, day));
    // }
    // };
    return dropdown;
  }

  private List<UtenteServiziRistorazione> getListaFigliMensa(
      List<UtenteServiziRistorazione> listaFigli) {
    List<UtenteServiziRistorazione> listaFigliMensa = new ArrayList<UtenteServiziRistorazione>();
    for (UtenteServiziRistorazione utenteServiziRistorazione : listaFigli) {
      if (utenteServiziRistorazione
          .getStatoIscrizioneServiziRistorazione()
          .equalsIgnoreCase(
              UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA.value())) {
        listaFigliMensa.add(utenteServiziRistorazione);
      }
    }
    return listaFigliMensa;
  }

  private int contaGiorniPresenza(List<DatiPresenzaServiziRistorazione> listaPresenze) {
    int totale = 0;
    for (DatiPresenzaServiziRistorazione presenza : listaPresenze) {
      if (presenza
          .getPresenza()
          .equalsIgnoreCase(
              DatiPresenzaServiziRistorazione.PresenzaEnum.PRESENTE_SIA_A_SCUOLA_CHE_IN_MENSA
                  .value())) {
        totale = totale + 1;
      }
    }
    return totale;
  }

  private List<DatiPresenzaServiziRistorazione> getDatiPresenza(
      UtenteServiziRistorazione iscritto) {
    log.debug("[CalendarioMensaPanel] - getDatiPresenza:");
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getGiorniDiPresenzaPerFiglioPerMese(iscritto, day);
    } catch (BusinessException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  public void aggiornaMenu(UtenteServiziRistorazione iscritto, LocalDate day) {
    log.debug("[CalendarioMensaPanel] - aggiornaMenu");
    panelMenu.replace(new MenuMensaPanel(iscritto, day));
  }
}
