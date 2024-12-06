package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.icon.LdIconType;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.AssicurazioneRevisionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.BolloAutoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.template.IMieiMezziTemplate;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioVeicolo;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class IMieiMezziPanel extends IMieiMezziTemplate {

  private static final long serialVersionUID = 7904952970309030478L;

  private static final String ICON_AUTOVEICOLO = "color-cyan col-3 icon-autoveicolo";
  private static final String ICON_MOTOVEICOLO = "color-cyan col-3 icon-motoveicolo";
  private static final String ICON_VEICOLI = "color-cyan col-3 icon-mezzi";

  public IMieiMezziPanel(String id) {
    super(id);

    setOutputMarkupId(true);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    container.addOrReplace(boxMessaggi);

    List<Veicolo> listaVeicoli = getUtente().getListaVeicoliAttivi();

    if (listaVeicoli.isEmpty()) {
      String messaggioErrore = getString("IMieiMezziTemplate.messaggioInfo");
      info(messaggioErrore);

      container.addOrReplace(containerBox);
      containerBox.setVisible(false);
      container.addOrReplace(containerMessaggioListaVuota);
      containerMessaggioListaVuota.setVisible(false);
    } else {
      container.addOrReplace(containerMessaggioListaVuota);
      containerMessaggioListaVuota.setVisible(false);

      fillDati(listaVeicoli);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void logicaBusinessDelPannello(Object dati) throws BusinessException, ApiException {
    List<Veicolo> lista = (List<Veicolo>) dati;
    ListView<Veicolo> listView =
        new ListView<Veicolo>("box", lista) {

          private static final long serialVersionUID = 6684733405639816072L;

          @Override
          protected void populateItem(ListItem<Veicolo> item) {
            final Veicolo veicolo = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaVeicolo(veicolo));
            item.add(icona);

            Label targa = new Label("targa", veicolo.getTarga());
            Label tipoVeicolo = new Label("tipoVeicolo", veicolo.getTipoVeicolo());
            item.add(targa);
            item.add(tipoVeicolo);

            DettaglioVeicolo dettaglioVeicolo = popolaDettagliVeicolo(veicolo);

            String dataInizioProprietaValue = "";
            if (veicolo.getDataInizioProprieta().equals("30/12/1998")) {
              dataInizioProprietaValue = "Antecedente al ".concat(veicolo.getDataInizioProprieta());
            } else {
              dataInizioProprietaValue = veicolo.getDataInizioProprieta();
            }
            Label dataInizioProprieta = new Label("dataInizioProprieta", dataInizioProprietaValue);
            dataInizioProprieta.setVisible(PageUtil.isStringValid(dataInizioProprietaValue));
            item.add(dataInizioProprieta);

            Label categoriaEuro = new Label("categoriaEuro", dettaglioVeicolo.getCategoriaEuro());
            categoriaEuro.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getCategoriaEuro()));
            item.add(categoriaEuro);

            Label uso = new Label("uso", dettaglioVeicolo.getUso());
            uso.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getUso()));
            item.add(uso);

            Label carrozzeria = new Label("carrozzeria", dettaglioVeicolo.getCarrozzeria());
            carrozzeria.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getCarrozzeria()));
            item.add(carrozzeria);

            Label portata = new Label("portata", dettaglioVeicolo.getPortata());
            portata.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getPortata()));
            item.add(portata);

            Label cavalliFiscali =
                new Label("cavalliFiscali", dettaglioVeicolo.getCavalliFiscali());
            cavalliFiscali.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getCavalliFiscali()));
            item.add(cavalliFiscali);

            Label cilindrata = new Label("cilindrata", dettaglioVeicolo.getCilindrata());
            cilindrata.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getCilindrata()));
            item.add(cilindrata);

            Label flagEcoDiesel = new Label("flagEcoDiesel", dettaglioVeicolo.getFlagEcoDiesel());
            flagEcoDiesel.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getFlagEcoDiesel()));
            item.add(flagEcoDiesel);

            Label numeroAssi = new Label("numeroAssi", dettaglioVeicolo.getNumeroAssi());
            numeroAssi.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getNumeroAssi()));
            item.add(numeroAssi);

            Label rimorchiabilita =
                new Label("rimorchiabilita", dettaglioVeicolo.getRimorchiabilita());
            rimorchiabilita.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getRimorchiabilita()));
            item.add(rimorchiabilita);

            Label emissioneCO2 = new Label("emissioneCO2", dettaglioVeicolo.getEmissioneCO2());
            emissioneCO2.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getEmissioneCO2()));
            item.add(emissioneCO2);

            Label dataImmatricolazione =
                new Label("dataImmatricolazione", dettaglioVeicolo.getDataImmatricolazione());
            dataImmatricolazione.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getDataImmatricolazione()));
            item.add(dataImmatricolazione);

            Label destinazione = new Label("destinazione", dettaglioVeicolo.getDestinazione());
            destinazione.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getDestinazione()));
            item.add(destinazione);

            Label trasportoMerci =
                new Label("trasportoMerci", dettaglioVeicolo.getTrasportoMerci());
            trasportoMerci.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getTrasportoMerci()));
            item.add(trasportoMerci);

            Label massaComplessiva =
                new Label("massaComplessiva", dettaglioVeicolo.getMassaComplessiva());
            massaComplessiva.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getMassaComplessiva()));
            item.add(massaComplessiva);

            Label potenzaEffettiva =
                new Label("potenzaEffettiva", dettaglioVeicolo.getPotenzaEffettiva());
            potenzaEffettiva.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getPotenzaEffettiva()));
            item.add(potenzaEffettiva);

            Label alimentazione = new Label("alimentazione", dettaglioVeicolo.getAlimentazione());
            alimentazione.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getAlimentazione()));
            item.add(alimentazione);

            Label sospensionePneumatica =
                new Label("sospensionePneumatica", dettaglioVeicolo.getSospensionePneumatica());
            sospensionePneumatica.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getSospensionePneumatica()));
            item.add(sospensionePneumatica);

            Label numeroPosti = new Label("numeroPosti", dettaglioVeicolo.getNumeroPosti());
            numeroPosti.setVisible(PageUtil.isStringValid(dettaglioVeicolo.getNumeroPosti()));
            item.add(numeroPosti);

            Label presenzaGancioTraino =
                new Label("presenzaGancioTraino", dettaglioVeicolo.getPresenzaGancioTraino());
            presenzaGancioTraino.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getPresenzaGancioTraino()));
            item.add(presenzaGancioTraino);

            Label massaRimorchiabile =
                new Label("massaRimorchiabile", dettaglioVeicolo.getMassaRimorchiabile());
            massaRimorchiabile.setVisible(
                PageUtil.isStringValid(dettaglioVeicolo.getMassaRimorchiabile()));
            item.add(massaRimorchiabile);

            item.add(creaLinkBolloAuto(veicolo));
            item.add(creaLinkAssicurazioneRevisione(veicolo));
          }

          private LaddaAjaxLink<Object> creaLinkAssicurazioneRevisione(final Veicolo veicolo) {
            LaddaAjaxLink<Object> linkAssicurazioneRevisione =
                new LaddaAjaxLink<Object>("linkAssicurazioneRevisione", Type.Primary) {

                  private static final long serialVersionUID = -5990530981068235902L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {
                    target.add(IMieiMezziPanel.this);
                    try {
                      VerificaAssicurazioneVeicoli verificaAssicurazioneVeicoli =
                          ServiceLocator.getInstance()
                              .getServiziMieiMezzi()
                              .getAssicurazione(veicolo);
                      AssicurazioneRevisionePage page =
                          new AssicurazioneRevisionePage(veicolo, verificaAssicurazioneVeicoli);
                      throw new RestartResponseAtInterceptPageException(page);
                    } catch (BusinessException | ApiException e) {
                      log.debug("Errore durante la chiamata delle API", e);
                      error("Servizio attualmente non disponibile");
                      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
                    }
                  }
                };
            linkAssicurazioneRevisione.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString(
                            "IMieiMezziPanel.linkAssicurazioneRevisione", IMieiMezziPanel.this)));

            linkAssicurazioneRevisione.setIconType(new LdIconType("icon-revisione-auto mx-1"));

            return linkAssicurazioneRevisione;
          }

          private LaddaAjaxLink<String> creaLinkBolloAuto(final Veicolo veicolo) {
            LaddaAjaxLink<String> linkBolloAuto =
                new LaddaAjaxLink<String>("linkBolloAuto", Type.Primary) {

                  private static final long serialVersionUID = 11099574403912454L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {
                    target.add(IMieiMezziPanel.this);
                    BolloAutoPage page = new BolloAutoPage(veicolo);
                    throw new RestartResponseAtInterceptPageException(page);
                    // setResponsePage(page);
                  }
                };
            linkBolloAuto.setIconType(new LdIconType("icon-tributi mx-1"));
            linkBolloAuto.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString("IMieiMezziPanel.linkBolloAuto", IMieiMezziPanel.this)));
            return linkBolloAuto;
          }

          private AttributeAppender getCssIconaVeicolo(Veicolo veicolo) {
            String css = "";

            if (veicolo.getTipoVeicolo().equalsIgnoreCase("motoveicolo")) {
              css = ICON_MOTOVEICOLO;
            } else if (veicolo.getTipoVeicolo().equalsIgnoreCase("autoveicolo")) {
              css = ICON_AUTOVEICOLO;
            } else {
              css = ICON_VEICOLI;
            }
            return new AttributeAppender("class", " " + css);
          }
        };

    listView.setRenderBodyOnly(true);
    myAdd(listView);
  }

  private DettaglioVeicolo popolaDettagliVeicolo(Veicolo veicolo) {
    try {
      return ServiceLocator.getInstance().getServiziMieiMezzi().getDettagliVeicolo(veicolo);
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
  }
}
