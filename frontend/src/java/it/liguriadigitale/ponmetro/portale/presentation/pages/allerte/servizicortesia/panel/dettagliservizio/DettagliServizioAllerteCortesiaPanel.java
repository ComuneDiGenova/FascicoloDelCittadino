package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.dettagliservizio;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.FRUIBILITA;
import it.liguriadigitale.ponmetro.allertecortesia.model.PREFERENZA;
import it.liguriadigitale.ponmetro.allertecortesia.model.PREFERENZE;
import it.liguriadigitale.ponmetro.allertecortesia.model.SERVIZI;
import it.liguriadigitale.ponmetro.allertecortesia.model.SERVIZIO;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiStradeAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.AggiungiPreferenzaServizioAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.DettagliServizioAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.util.AllerteCortesiaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class DettagliServizioAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -761594776344030329L;

  private FdcAjaxButton btnRegistraServizio;
  private FdcAjaxButton btnCancellaServizio;
  private FdcAjaxButton btnCancellaPreferenza;

  private String email;
  private String idServizio;

  public DettagliServizioAllerteCortesiaPanel(String id, Object verificaServizi, String email) {
    super(id, verificaServizi);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.email = email;

    fillDati(verificaServizi);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    VerificaServiziResponse verificaServizi = (VerificaServiziResponse) dati;

    form.add(
        new FdCTitoloPanel("titolo", getString("DettagliServizioAllerteCortesiaPanel.titolo")));

    SERVIZI servizi = null;
    List<SERVIZIO> listaServizi = new ArrayList<SERVIZIO>();

    List<PREFERENZA> listaPreferenze = new ArrayList<PREFERENZA>();

    if (LabelFdCUtil.checkIfNotNull(verificaServizi)
        && LabelFdCUtil.checkIfNotNull(verificaServizi.getFRONTOFFICEPUTUTENTE())) {
      servizi = verificaServizi.getFRONTOFFICEPUTUTENTE().getSERVIZI();
    }

    if (LabelFdCUtil.checkIfNotNull(servizi)) {
      listaServizi = servizi.getSERVIZIO();
    }

    PREFERENZE preferenze = servizi.getPREFERENZE();
    if (LabelFdCUtil.checkIfNotNull(preferenze)) {
      listaPreferenze = preferenze.getPREFERENZA();
    }

    ListView<SERVIZIO> boxListaServizi =
        new ListView<SERVIZIO>("boxListaServizi", listaServizi) {

          private static final long serialVersionUID = 6403807077902192713L;

          @Override
          protected void populateItem(ListItem<SERVIZIO> itemServizio) {
            final SERVIZIO servizio = itemServizio.getModelObject();

            idServizio = servizio.getID();

            itemServizio.addOrReplace(creaBtnAggiungiPreferenza(email, idServizio));

            NotEmptyLabel descrizioneBreve =
                new NotEmptyLabel("descrizioneBreve", servizio.getDESCRIZIONEBREVE());
            itemServizio.addOrReplace(descrizioneBreve);

            itemServizio.addOrReplace(
                new AmtCardLabel<>(
                        "id", servizio.getID(), DettagliServizioAllerteCortesiaPanel.this)
                    .setVisible(false));

            itemServizio.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneEstesa",
                    servizio.getDESCRIZIONEESTESA(),
                    DettagliServizioAllerteCortesiaPanel.this));

            List<FRUIBILITA> listaFruibilita = new ArrayList<FRUIBILITA>();
            if (LabelFdCUtil.checkIfNotNull(servizio.getFRUIBILITA())) {
              listaFruibilita = servizio.getFRUIBILITA();
            }
            ListView<FRUIBILITA> boxListaFruibilita =
                new ListView<FRUIBILITA>("boxListaFruibilita", listaFruibilita) {

                  @Override
                  protected void populateItem(ListItem<FRUIBILITA> itemFruibilita) {
                    final FRUIBILITA fruibilita = itemFruibilita.getModelObject();

                    NotEmptyLabel descrizioneCanale =
                        new NotEmptyLabel("descrizioneCanale", fruibilita.getDESCRIZIONECANALE());
                    itemFruibilita.addOrReplace(descrizioneCanale);

                    itemFruibilita.addOrReplace(
                        new AmtCardLabel<>(
                                "idRelazioneServizioCanale",
                                fruibilita.getIDRELAZIONESERVIZIOCANALE(),
                                DettagliServizioAllerteCortesiaPanel.this)
                            .setVisible(false));

                    itemFruibilita.addOrReplace(
                        new AmtCardLabel<>(
                                "idCanale",
                                fruibilita.getIDCANALE(),
                                DettagliServizioAllerteCortesiaPanel.this)
                            .setVisible(false));

                    itemFruibilita.addOrReplace(
                        new AmtCardLabel<>(
                                "verificaCellulare",
                                AllerteCortesiaUtil.decodificaBooleanoComeString(
                                    fruibilita.getVERIFICACELLULARE()),
                                DettagliServizioAllerteCortesiaPanel.this)
                            .setVisible(false));

                    itemFruibilita.addOrReplace(
                        new AmtCardLabel<>(
                            "utenteIscritto",
                            AllerteCortesiaUtil.decodificaBooleanoComeString(
                                fruibilita.getUTENTEISCRITTO()),
                            DettagliServizioAllerteCortesiaPanel.this));
                  }
                };
            boxListaFruibilita.setOutputMarkupId(true);
            boxListaFruibilita.setVisible(
                LabelFdCUtil.checkIfNotNull(listaFruibilita)
                    && !LabelFdCUtil.checkEmptyList(listaFruibilita));
            itemServizio.addOrReplace(boxListaFruibilita);
          }
        };
    boxListaServizi.setOutputMarkupId(true);
    boxListaServizi.setVisible(
        LabelFdCUtil.checkIfNotNull(listaServizi) && !LabelFdCUtil.checkEmptyList(listaServizi));
    form.addOrReplace(boxListaServizi);

    form.add(
        new FdCTitoloPanel(
            "titoloPreferenze",
            getString("DettagliServizioAllerteCortesiaPanel.titoloPreferenze")));

    ListView<PREFERENZA> boxListaPreferenze =
        new ListView<PREFERENZA>("boxListaPreferenze", listaPreferenze) {

          private static final long serialVersionUID = 2820598455330775365L;

          @Override
          protected void populateItem(ListItem<PREFERENZA> itemPreferenza) {
            final PREFERENZA preferenza = itemPreferenza.getModelObject();

            NotEmptyLabel descrizioneStrada =
                new NotEmptyLabel("descrizioneStrada", preferenza.getDESCRIZIONESTRADA());
            itemPreferenza.addOrReplace(descrizioneStrada);

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "codiceStrada",
                    preferenza.getCODICESTRADA(),
                    DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "codiceDivisione",
                    preferenza.getCODICEDIVISIONE(),
                    DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneDivisione",
                    preferenza.getDESCRIZIONEDIVISIONE(),
                    DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "codiceCircoscrizione",
                    preferenza.getCODICECIRCOSCRIZIONE(),
                    DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneCircoscrizione",
                    preferenza.getDESCRIZIONECIRCOSCRIZIONE(),
                    DettagliServizioAllerteCortesiaPanel.this));

            //				itemPreferenza.addOrReplace(new AmtCardLabel<>("codiceUnitaUrbanistica",
            //						preferenza.getCODICEUNITAURBANISTICA(),
            // DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneUnitaUrbanistica",
                    preferenza.getDESCRIZIONEUNITAURBANISTICA(),
                    DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(
                new AmtCardLabel<>(
                    "listaCivici",
                    preferenza.getLISTACIVICI(),
                    DettagliServizioAllerteCortesiaPanel.this));

            itemPreferenza.addOrReplace(creaBtnCancellaPreferenza(email, idServizio, preferenza));
          }
        };
    boxListaPreferenze.setOutputMarkupId(true);
    boxListaPreferenze.setVisible(
        LabelFdCUtil.checkIfNotNull(listaPreferenze)
            && !LabelFdCUtil.checkEmptyList(listaPreferenze));
    form.addOrReplace(boxListaPreferenze);
  }

  private FdcAjaxButton creaBtnCancellaPreferenza(
      String email, String idServizio, PREFERENZA preferenza) {
    btnCancellaPreferenza =
        new FdcAjaxButton("btnCancellaPreferenza") {

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP clic onSubmit  btnCancellaPreferenza");

            try {
              String codiceStrada = "";
              String codiceDivisione = "";
              String codiceCircoscrizione = "";
              String codiceUnitaUrbanistica = "";

              if (LabelFdCUtil.checkIfNotNull(preferenza)) {
                codiceStrada = preferenza.getCODICESTRADA();
                codiceDivisione = preferenza.getCODICEDIVISIONE();

                if (LabelFdCUtil.checkIfNotNull(preferenza.getCODICECIRCOSCRIZIONE())) {
                  codiceCircoscrizione = String.valueOf(preferenza.getCODICECIRCOSCRIZIONE());
                }

                codiceUnitaUrbanistica = preferenza.getCODICEUNITAURBANISTICA();
              }

              ServiceLocator.getInstance()
                  .getServiziAllerteCortesia()
                  .putWsCancellazionePreferenzaServizio(
                      email,
                      idServizio,
                      codiceStrada,
                      codiceDivisione,
                      codiceCircoscrizione,
                      codiceUnitaUrbanistica);

              // success("Cancellazione effettuata con successo");

              VerificaServiziResponse dettagli =
                  AllerteCortesiaUtil.popolaDettagliServizio(idServizio, email);

              setResponsePage(new DettagliServizioAllerteCortesiaPage(dettagli, email));

            } catch (ApiException e) {
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

              Integer indexOf = messaggioRicevuto.indexOf(":");
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              error(messaggioDaVisualizzare);

            } catch (IOException | BusinessException e) {

              error("Errore durante aggiornamento del servizio.");
              log.error("BusinessException gestito durante la chiamata delle API:", e);
            }

            target.add(DettagliServizioAllerteCortesiaPanel.this, feedbackPanel);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DettagliServizioAllerteCortesiaPanel.this);

            log.error("CP errore cancella preferenza allerta cortesia");
          }
        };

    btnCancellaPreferenza.setLabel(
        Model.of(getString("DettagliServizioAllerteCortesiaPanel.cancellaPreferenza")));

    return btnCancellaPreferenza;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAggiungiPreferenza(
      String email, String idServizio) {
    FdCButtonBootstrapAjaxLink<Object> btnAggiungiPreferenza =
        new FdCButtonBootstrapAjaxLink<Object>("btnAggiungiPreferenza", Type.Primary) {

          private static final long serialVersionUID = -7732779463893125344L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic btnAggiungiPreferenza");

            DatiStradeAllerteCortesia datiStrada = new DatiStradeAllerteCortesia();
            datiStrada.setEmail(email);
            datiStrada.setIdServizio(idServizio);
            datiStrada.setStart("0");

            setResponsePage(new AggiungiPreferenzaServizioAllerteCortesiaPage(datiStrada));
          }
        };

    btnAggiungiPreferenza.setLabel(
        Model.of(getString("DettagliServizioAllerteCortesiaPanel.aggiungiPreferenza")));

    return btnAggiungiPreferenza;
  }

  //	public VerificaServiziResponse popolaDettagliServizio(String servizio, String email) {
  //		VerificaServiziResponse verificaServizi = null;
  //		try {
  //			verificaServizi =
  // ServiceLocator.getInstance().getServiziAllerteCortesia().getWsGetServizioById(email,
  //					servizio);
  //		} catch (BusinessException | ApiException | IOException e) {
  //			log.debug("Errore durante la chiamata delle API", e);
  //			throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Allerte Servizi
  // Cortesia"));
  //		}
  //
  //		return verificaServizi;
  //	}

}
