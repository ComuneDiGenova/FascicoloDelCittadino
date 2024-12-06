package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.preferenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.Tratto;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiStradeAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.DettagliServizioAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.util.AllerteCortesiaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class ListaStradeAllerteCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = -5605479762350481253L;

  private int index;

  List<Tratto> listaStrade = new ArrayList<>();

  private FdcAjaxButton precedenti10;
  private FdcAjaxButton prossimi10;

  private FdcAjaxButton aggiungi;

  private int startIndex;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  WebMarkupContainer containerBottoniPaginazioneRisultati;

  int numeroOccorenze = 0;

  public ListaStradeAllerteCortesiaPanel(
      String id, DatiStradeAllerteCortesia datiStrada, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiStrada);
  }

  public ListaStradeAllerteCortesiaPanel(
      String id, DatiStradeAllerteCortesia datiStrada, int index, int startIndex) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    this.index = index;
    this.startIndex = startIndex;

    fillDati(datiStrada);
  }

  public int getStartIndex() {
    return this.startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  @Override
  public void fillDati(Object dati) {
    DatiStradeAllerteCortesia datiStrada = (DatiStradeAllerteCortesia) dati;

    add(new FdCTitoloPanel("titolo", getString("ListaStradeAllerteCortesiaPanel.titolo")));

    if (LabelFdCUtil.checkIfNotNull(datiStrada)
        && LabelFdCUtil.checkIfNotNull(datiStrada.getTratti())) {
      listaStrade = datiStrada.getTratti().getTratto();
      numeroOccorenze = listaStrade.size();
      log.debug("numeroOccorenze_in_fillDati = " + numeroOccorenze);
    }

    @SuppressWarnings("serial")
    ListView<Tratto> boxListaStrade =
        new ListView<Tratto>("boxListaStrade", listaStrade) {

          @Override
          protected void populateItem(ListItem<Tratto> itemStrada) {
            final Tratto strada = itemStrada.getModelObject();

            NotEmptyLabel descrizioneStrada =
                new NotEmptyLabel("descrizioneStrada", strada.getNOMEVIA());
            itemStrada.addOrReplace(descrizioneStrada);

            itemStrada.addOrReplace(
                new AmtCardLabel<>(
                    "codiceStrada",
                    strada.getCODICESTRADA(),
                    ListaStradeAllerteCortesiaPanel.this));

            itemStrada.addOrReplace(
                new AmtCardLabel<>(
                    "codiceDivisione",
                    strada.getCODMUNICIPIO(),
                    ListaStradeAllerteCortesiaPanel.this));

            itemStrada.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneDivisione",
                    strada.getDESMUNICIPIO(),
                    ListaStradeAllerteCortesiaPanel.this));

            itemStrada.addOrReplace(
                new AmtCardLabel<>(
                    "codiceCircoscrizione",
                    strada.getCODCIRCOSCRIZIONE(),
                    ListaStradeAllerteCortesiaPanel.this));

            itemStrada.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneCircoscrizione",
                    strada.getDESCIRCOSCRIZIONE(),
                    ListaStradeAllerteCortesiaPanel.this));

            itemStrada.addOrReplace(
                new AmtCardLabel<>(
                    "listaCivici", strada.getNUMEROCIVICO(), ListaStradeAllerteCortesiaPanel.this));

            itemStrada.addOrReplace(creaBtnAggiungi(datiStrada, strada));
          }
        };

    boxListaStrade.setOutputMarkupId(true);
    boxListaStrade.setOutputMarkupPlaceholderTag(true);
    boxListaStrade.setVisible(
        LabelFdCUtil.checkIfNotNull(listaStrade) && !LabelFdCUtil.checkEmptyList(listaStrade));

    addOrReplace(boxListaStrade);
    /*
    containerBottoniPaginazioneRisultati = new WebMarkupContainer("containerBottoniPaginazioneRisultati");

    containerBottoniPaginazioneRisultati.addOrReplace(creaBtnPrecedenti10(datiStrada));
    containerBottoniPaginazioneRisultati.addOrReplace(creaBtnProssimi10(datiStrada));

    containerBottoniPaginazioneRisultati.setOutputMarkupId(true);
    containerBottoniPaginazioneRisultati.setOutputMarkupPlaceholderTag(true);

    containerBottoniPaginazioneRisultati
    		.setVisible(index == 2 && checkVisibilitaBottoniPrecedenti10Prossimi10(datiStrada));
    addOrReplace(containerBottoniPaginazioneRisultati);
    */

  }

  /*
  private boolean checkVisibilitaBottoniPrecedenti10Prossimi10(DatiStradeAllerteCortesia datiStrada) {
  	boolean visibile = false;

  	return visibile;
  }
  */

  /*
  private FdcAjaxButton creaBtnPrecedenti10(DatiStradeAllerteCortesia datiStrada) {

  	precedenti10 = new FdcAjaxButton("precedenti10") {

  		private static final long serialVersionUID = 6475813093750295989L;

  		@Override
  		protected void onSubmit(AjaxRequestTarget target) {
  			log.debug("CP creaBtnPrecedenti10 " + " - index = " + index + " - star	index = " + startIndex);

  			try {

  				Risposta risposta = ServiceLocator.getInstance().getServiziGeorefToponomastica().getWsGetGeorefToponomastica(datiStrada.getStrada());



  				datiStrada.setTratti(risposta.getRisposta());

  			} catch (BusinessException | ApiException | IOException e) {
  				log.error("Errore get strade cortesia: " + e.getMessage(), e);

  				error("Errore durante ricerca strada");

  			}

  			AggiungiPreferenzaServizioAllerteCortesiaPage page = (AggiungiPreferenzaServizioAllerteCortesiaPage) getParent()
  					.getPage();
  			log.debug("numeroOccorenze_precedenti " + numeroOccorenze);
  			log.debug("startIndex_precedenti " + startIndex);
  			page.refreshListaRisultati(datiStrada, index, numeroOccorenze, startIndex);

  			target.add(page);

  		}

  		@Override
  		protected void onError(AjaxRequestTarget target) {
  			target.add(ListaStradeAllerteCortesiaPanel.this);
  		}

  	};

  	if (startIndex <= 0) {
  		precedenti10.setVisible(false);
  	} else {
  		precedenti10.setVisible(true);
  	}

  	return precedenti10;
  }
  */

  /*
  private FdcAjaxButton creaBtnProssimi10(DatiStradeAllerteCortesia datiStrada) {

  	prossimi10 = new FdcAjaxButton("prossimi10") {

  		private static final long serialVersionUID = 7217385837497338534L;

  		@Override
  		protected void onSubmit(AjaxRequestTarget target) {
  			log.debug("CP prossimi10 " + datiStrada.getStrada() + " - start index = " + startIndex);

  			startIndex = startIndex + 10;

  			log.debug("CP strat index qui = " + startIndex);
  			log.debug("sizeData_test " + listaStrade.size());

  			try {
  				log.debug("dento_try_prossi " + startIndex);


  				StradeCivici stradeCiviciProssimi10 = ServiceLocator.getInstance().getServiziAllerteCortesia()
  						.getWsGetStradeCivici(datiStrada.getEmail(), datiStrada.getIdServizio(),
  								datiStrada.getStrada(), String.valueOf(startIndex));

  				if (LabelFdCUtil.checkIfNotNull(stradeCiviciProssimi10)
  						&& LabelFdCUtil.checkIfNotNull(stradeCiviciProssimi10.getFRONTOFFICEPUTUTENTE())) {
  					numeroOccorenze = stradeCiviciProssimi10.getFRONTOFFICEPUTUTENTE().getNUMEROOCCORRENZE();
  				}

  				//datiStrada.setStrade(stradeCiviciProssimi10);

  			} catch (BusinessException | ApiException | IOException e) {
  				log.error("Errore get strade cortesia: " + e.getMessage(), e);
  				error("Errore durante ricerca strada");

  			}

  			log.debug(" getStartIndex " + getStartIndex());
  			AggiungiPreferenzaServizioAllerteCortesiaPage page = (AggiungiPreferenzaServizioAllerteCortesiaPage) getParent()
  					.getPage();
  			log.debug("prossimi_index_test " + index);
  			log.debug("numeroOccorenze_index_test " + numeroOccorenze);
  			log.debug("startIndex_index_test " + startIndex);

  			page.refreshListaRisultati(datiStrada, index, numeroOccorenze, startIndex);

  			target.add(page);

  		}

  		@Override
  		protected void onError(AjaxRequestTarget target) {
  			target.add(ListaStradeAllerteCortesiaPanel.this);
  		}

  	};

  	log.debug(" startIndex " + startIndex);
  	log.debug("numeroOccorenze_last_test " + numeroOccorenze);
  	log.debug("listaStrade_last_test " + listaStrade.size());

  	if (numeroOccorenze != null) {
  		log.debug("startIndex_in_prossimo " + startIndex);

  		if (startIndex > listaStrade.size()) {

  			prossimi10.setVisible(false);
  		} else {

  			prossimi10.setVisible(true);
  		}

  	}

  	else {
  		prossimi10.setVisible(false);
  	}

  	return prossimi10;
  }
  */

  private FdcAjaxButton creaBtnAggiungi(DatiStradeAllerteCortesia datiStrada, Tratto strada) {

    aggiungi =
        new FdcAjaxButton("btnAggiungi") {

          private static final long serialVersionUID = 8058652866496634106L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug(
                "CP aggiungi "
                    + datiStrada.getStrada()
                    + " - strada scelta = "
                    + strada.getNOMEVIA());

            try {
              if (LabelFdCUtil.checkIfNotNull(datiStrada) && LabelFdCUtil.checkIfNotNull(strada)) {

                String email = datiStrada.getEmail();
                String idServizio = datiStrada.getIdServizio();

                String codiceStrada = "";
                if (LabelFdCUtil.checkIfNotNull(strada.getCODICESTRADA())) {
                  codiceStrada = String.valueOf(strada.getCODICESTRADA());
                }

                String codiceDivisione = strada.getCODMUNICIPIO();

                String codiceCircoscrizione = strada.getCODCIRCOSCRIZIONE();

                if (LabelFdCUtil.checkIfNotNull(codiceDivisione) && codiceDivisione.length() == 1) {
                  codiceDivisione = "0" + codiceDivisione;
                }

                if (LabelFdCUtil.checkIfNotNull(codiceCircoscrizione)
                    && codiceCircoscrizione.length() == 1) {
                  codiceCircoscrizione = "0" + codiceCircoscrizione;
                }

                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putWsAggiuntaPreferenzaServizio(
                        email, idServizio, codiceStrada, codiceDivisione, codiceCircoscrizione);

                VerificaServiziResponse dettagli =
                    AllerteCortesiaUtil.popolaDettagliServizio(idServizio, email);

                setResponsePage(new DettagliServizioAllerteCortesiaPage(dettagli, email));
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore put aggiungi preferenza allerte cortesia: " + e.getMessage(), e);

              error("Errore durante aggiunta preferenza");

              onError();
            }

            target.add(ListaStradeAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP on error lista strade cortesia");

            target.add(ListaStradeAllerteCortesiaPanel.this);
          }
        };

    return aggiungi;
  }
}
