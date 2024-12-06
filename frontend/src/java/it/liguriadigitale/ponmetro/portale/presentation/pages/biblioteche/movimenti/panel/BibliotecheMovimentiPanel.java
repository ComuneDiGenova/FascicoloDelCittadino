package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.icon.LdIconType;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.BibliotecheMovimentiDettaglioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.EnumMovimento;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.modal.panel.ModaleAnnullaMovimentoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.utilbiblioteche.UtilBiblioteche;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.MovimentoInfoContabili;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class BibliotecheMovimentiPanel extends BasePanel {

  private static final long serialVersionUID = 7684644706434535875L;

  private static final String ICON_LIBRI = "col-3 icon-libri";

  private static final String ICON_TABLET = "col-3 icon-tablet";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  protected ModaleAnnullaMovimentoPanel<String> modalViewPanel;

  private EnumMovimento enumMovimento;

  public BibliotecheMovimentiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public BibliotecheMovimentiPanel(
      String id, List<Movimento> listaMovimenti, EnumMovimento enumMovimento) {
    super(id);

    this.enumMovimento = enumMovimento;

    fillDati(listaMovimenti);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<Movimento> listaMovimenti = (List<Movimento>) dati;

    String messaggioListaVuota = "";
    if (enumMovimento.equals(EnumMovimento.PRESTITI)) {
      messaggioListaVuota = getString("BibliotecheMovimentiPanel.listaVuotaPrestiti");
    } else if (enumMovimento.equals(EnumMovimento.PRENOTAZIONI)) {
      messaggioListaVuota = getString("BibliotecheMovimentiPanel.listaVuotaPrenotazioni");
    } else {
      messaggioListaVuota = getString("BibliotecheMovimentiPanel.listaVuotaTutti");
    }
    Label listaMovimentiVuota = new Label("listaMovimentiVuota", messaggioListaVuota);
    listaMovimentiVuota.setVisible(
        !UtilBiblioteche.checkIfNull(listaMovimenti)
            || UtilBiblioteche.checkListIsEmpty(listaMovimenti));
    addOrReplace(listaMovimentiVuota);

    PageableListView<Movimento> listView =
        new PageableListView<Movimento>("box", listaMovimenti, 4) {

          private static final long serialVersionUID = 1465258221971222554L;

          @Override
          protected void populateItem(ListItem<Movimento> item) {
            final Movimento movimento = item.getModelObject();

            boolean checkMovimento = UtilBiblioteche.checkIfNull(movimento);

            if (checkMovimento) {
              WebMarkupContainer icona = new WebMarkupContainer("icona");
              icona.add(getCssIconaLibri(movimento.getCdTipo()));
              if (movimento.getCdStato() != null) {
                icona.add(getColoreCssIconaLibri(movimento.getCdStato()));
              }
              item.addOrReplace(icona);

              try {
                String descrizioneTipoMovimento =
                    ServiceLocator.getInstance()
                        .getServiziBiblioteche()
                        .getDescrizioneTipoMovimento(getUtente(), movimento);

                Label tipoMovimento = new Label("tipoMovimento", descrizioneTipoMovimento);
                tipoMovimento.setVisible(
                    PageUtil.isStringValid(movimento.getCdTipo())
                        && !descrizioneTipoMovimento.isEmpty());
                item.addOrReplace(tipoMovimento);

              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore durante decodifica tipo movimento " + e.getMessage());
              }

              try {
                String descrizioneBiblioteca =
                    ServiceLocator.getInstance()
                        .getServiziBiblioteche()
                        .getDescrizioneBiblioteca(getUtente(), movimento);

                Label codiceMovimentoBiblioteca =
                    new Label("codiceMovimentoBiblioteca", descrizioneBiblioteca);
                codiceMovimentoBiblioteca.setVisible(
                    PageUtil.isStringValid(movimento.getCdBib())
                        && !descrizioneBiblioteca.isEmpty());
                item.addOrReplace(codiceMovimentoBiblioteca);

              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore durante decodifica biblioteca " + e.getMessage());
              }

              try {
                String descrizioneStatoMovimento =
                    ServiceLocator.getInstance()
                        .getServiziBiblioteche()
                        .getDescrizioneStatoMovimento(getUtente(), movimento);

                Label statoMovimento = new Label("statoMovimento", descrizioneStatoMovimento);
                statoMovimento.setVisible(
                    PageUtil.isStringValid(movimento.getCdStato())
                        && !descrizioneStatoMovimento.isEmpty());
                item.addOrReplace(statoMovimento);

              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore durante decodifica stato movimento " + e.getMessage());
              }

              Label isbd = new Label("isbd", movimento.getIsbd());
              isbd.setVisible(PageUtil.isStringValid(movimento.getIsbd()));
              item.addOrReplace(isbd);

              Label inve = new Label("inve", movimento.getInve());
              inve.setVisible(PageUtil.isStringValid(movimento.getInve()));
              item.addOrReplace(inve);

              Label collocazione = new Label("collocazione", movimento.getColloc());
              collocazione.setVisible(PageUtil.isStringValid(movimento.getColloc()));
              item.addOrReplace(collocazione);

              Label dsFasVol = new Label("dsFasVol", movimento.getDsFasVol());
              dsFasVol.setVisible(PageUtil.isStringValid(movimento.getDsFasVol()));
              item.addOrReplace(dsFasVol);

              LocalDateLabel dtInizio = new LocalDateLabel("dtInizio", movimento.getDtInizio());
              dtInizio.setVisible(UtilBiblioteche.checkIfNull(movimento.getDtInizio()));
              item.addOrReplace(dtInizio);

              LocalDateLabel dtStimaFine =
                  new LocalDateLabel("dtStimaFine", movimento.getDtStimaFine());
              dtStimaFine.setVisible(UtilBiblioteche.checkIfNull(movimento.getDtStimaFine()));
              item.addOrReplace(dtStimaFine);

              List<MovimentoInfoContabili> listaMovimentiInfoContabili =
                  movimento.getInfoContabili();
              ListView<MovimentoInfoContabili> listViewMovimentiInfoContabili = null;
              if (UtilBiblioteche.checkIfNull(listaMovimentiInfoContabili)
                  && !UtilBiblioteche.checkListIsEmpty(listaMovimentiInfoContabili)) {
                listViewMovimentiInfoContabili =
                    new ListView<MovimentoInfoContabili>(
                        "listViewMovimentiInfoContabili", listaMovimentiInfoContabili) {

                      private static final long serialVersionUID = 4537244167875225578L;

                      @Override
                      protected void populateItem(ListItem<MovimentoInfoContabili> item) {
                        MovimentoInfoContabili infoContabile = item.getModelObject();

                        item.setOutputMarkupId(true);

                        NumberFormat numberFormatEuro =
                            NumberFormat.getCurrencyInstance(Locale.ITALY);
                        String descrizione =
                            getString("BibliotecheMovimentiPanel.codiceInfoContabile")
                                .concat(" ")
                                .concat(infoContabile.getCd())
                                .concat(" - ")
                                .concat(numberFormatEuro.format(infoContabile.getImporto()));
                        Label infoContabileLabel = new Label("infoContabile", descrizione);
                        item.addOrReplace(infoContabileLabel);
                      }
                    };
              }

              if (UtilBiblioteche.checkIfNull(listViewMovimentiInfoContabili)) {
                listViewMovimentiInfoContabili.setVisible(
                    UtilBiblioteche.checkIfNull(listaMovimentiInfoContabili)
                        && !UtilBiblioteche.checkListIsEmpty(listaMovimentiInfoContabili));
                item.addOrReplace(listViewMovimentiInfoContabili);
              } else {
                item.addOrReplace(
                    new WebMarkupContainer("listViewMovimentiInfoContabili").setVisible(false));
              }

              String descrizioneKit = "";
              String idKit = "";
              String dsKit = "";
              String cdTipoKit = "";
              if (UtilBiblioteche.checkIfNull(movimento.getInfoKit())) {
                if (UtilBiblioteche.checkIfNull(movimento.getInfoKit().getId())) {
                  idKit = String.valueOf(movimento.getInfoKit().getId()).concat(" - ");
                }
                if (UtilBiblioteche.checkIfNull(movimento.getInfoKit().getDs())) {
                  dsKit = movimento.getInfoKit().getDs().concat(" - ");
                }
                if (UtilBiblioteche.checkIfNull(movimento.getInfoKit().getCdTipo())) {
                  cdTipoKit = movimento.getInfoKit().getCdTipo();
                }
              }
              descrizioneKit = idKit.concat(dsKit).concat(cdTipoKit);
              Label infoKit = new Label("infoKit", descrizioneKit);
              infoKit.setVisible(UtilBiblioteche.checkIfNull(movimento.getInfoKit()));
              item.addOrReplace(infoKit);

              String descrizioneIll = "";
              if (UtilBiblioteche.checkIfNull(movimento.getInfoILL())) {
                descrizioneIll = movimento.getInfoILL().getDsBibliotecaPrestante();
              }
              Label infoILL = new Label("infoILL", descrizioneIll);
              infoILL.setVisible(UtilBiblioteche.checkIfNull(movimento.getInfoILL()));
              item.addOrReplace(infoILL);

              modalViewPanel = new ModaleAnnullaMovimentoPanel<String>("modalViewPanel", movimento);

              String movimentoDaAnnullareValue = getString("BibliotecheMovimentiPanel.seiSicuro");
              Label movimentoDaAnnullare =
                  new Label("movimentoDaAnnullare", movimentoDaAnnullareValue);
              modalViewPanel.myAdd(movimentoDaAnnullare);

              Label infoAnnullamento =
                  new Label("infoAnnullamento", getString("BibliotecheMovimentiPanel.info"));
              modalViewPanel.myAdd(infoAnnullamento);

              modalViewPanel.addOrReplace(creaBtnSi(modalViewPanel, movimento));
              modalViewPanel.addOrReplace(creaBtnNo(modalViewPanel, movimento));

              item.addOrReplace(modalViewPanel);
              item.addOrReplace(creaBtnAnnullaMovimento(modalViewPanel, movimento));
            }
          }

          private AttributeAppender getCssIconaLibri(String codiceMovimento) {
            String css = "";

            String digitale =
                popolaListaDigitale().stream()
                    .filter(elem -> elem.equalsIgnoreCase(codiceMovimento))
                    .findAny()
                    .orElse(null);

            if (digitale == null) {
              css = ICON_LIBRI;
            } else {
              css = ICON_TABLET;
            }

            return new AttributeAppender("class", " " + css);
          }

          private AttributeModifier getColoreCssIconaLibri(String statoMovimento) {
            AttributeModifier coloreIcona = null;

            String verde =
                popolaListaVerde().stream()
                    .filter(elem -> elem.equalsIgnoreCase(statoMovimento))
                    .findAny()
                    .orElse(null);

            String giallo =
                popolaListaGiallo().stream()
                    .filter(elem -> elem.equalsIgnoreCase(statoMovimento))
                    .findAny()
                    .orElse(null);

            String rosso =
                popolaListaRosso().stream()
                    .filter(elem -> elem.equalsIgnoreCase(statoMovimento))
                    .findAny()
                    .orElse(null);

            if (verde != null) {
              coloreIcona = new AttributeModifier("style", "color: #008758");
            } else if (giallo != null) {
              coloreIcona = new AttributeModifier("style", "color: #a66300");
            } else if (rosso != null) {
              coloreIcona = new AttributeModifier("style", "color: #d9364f");
            } else {
              coloreIcona = new AttributeModifier("style", "color: #000000");
            }

            return coloreIcona;
          }
        };

    listView.setRenderBodyOnly(true);
    boolean isListaMovimentiVisible =
        UtilBiblioteche.checkIfNull(listaMovimenti)
            && !UtilBiblioteche.checkListIsEmpty(listaMovimenti);
    listView.setVisible(isListaMovimentiVisible);
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationIoLeggo", listView);
    paginazioneFascicolo.setVisible(isListaMovimentiVisible && listaMovimenti.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private LaddaAjaxLink<Object> creaBtnAnnullaMovimento(
      Modal<String> modalViewPanelMovimento, Movimento movimento) {
    LaddaAjaxLink<Object> btnAnnullaMovimento =
        new LaddaAjaxLink<Object>("btnAnnullaMovimento", Type.Primary) {

          private static final long serialVersionUID = 477353687398420404L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modalViewPanelMovimento.show(target);
          }
        };

    btnAnnullaMovimento.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "BibliotecheMovimentiPanel.annullaMovimento", BibliotecheMovimentiPanel.this)));
    btnAnnullaMovimento.setIconType(new LdIconType("icon-no mx-1"));
    btnAnnullaMovimento.setVisible(movimento.getAnnullabile());

    return btnAnnullaMovimento;
  }

  private LaddaAjaxLink<Object> creaBtnSi(
      Modal<String> modalViewPanelMovimento, Movimento movimento) {
    LaddaAjaxLink<Object> btnSi =
        new LaddaAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -1939768425829411648L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            log.debug("CP annulla movimento SI = " + movimento.getId());

            try {
              ServiceLocator.getInstance()
                  .getServiziBiblioteche()
                  .cancellaMovimentoAnnullabile(getUtente().getCodiceFiscaleOperatore(), movimento);
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore durante cancellazione movimento " + e.getMessage());
            }

            // TODO messo cosi perche non riuscivamo ad aggiornare il
            // pannello con il target
            setResponsePage(new BibliotecheMovimentiDettaglioPage());
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("BibliotecheMovimentiPanel.si", BibliotecheMovimentiPanel.this)));

    return btnSi;
  }

  private LaddaAjaxLink<Object> creaBtnNo(
      Modal<String> modalViewPanelMovimento, Movimento movimento) {
    LaddaAjaxLink<Object> btnNo =
        new LaddaAjaxLink<Object>("btnNo", Type.Primary) {

          private static final long serialVersionUID = 3448609230251177846L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modalViewPanelMovimento.close(target);
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("BibliotecheMovimentiPanel.no", BibliotecheMovimentiPanel.this)));

    return btnNo;
  }

  private List<String> popolaListaVerde() {
    try {
      return ServiceLocator.getInstance().getServiziBiblioteche().getStatiVerde();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private List<String> popolaListaGiallo() {
    try {
      return ServiceLocator.getInstance().getServiziBiblioteche().getStatiGiallo();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private List<String> popolaListaRosso() {
    try {
      return ServiceLocator.getInstance().getServiziBiblioteche().getStatiRosso();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private List<String> popolaListaDigitale() {
    try {
      return ServiceLocator.getInstance().getServiziBiblioteche().getDigitale();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
