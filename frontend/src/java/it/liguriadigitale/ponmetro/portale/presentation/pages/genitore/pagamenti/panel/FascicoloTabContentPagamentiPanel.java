package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.ImportoEuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.resource.FascicoloPdfResources;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.ItemBeneficiarioBollettino;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FascicoloTabContentPagamentiPanel extends BasePanel {

  private static final long serialVersionUID = -946699189791531484L;

  List<DatiPagamentiBollettiniRiepilogativiEstesi> datiPagamentiBollettiniRiepilogativiEstesi;

  String cfImpegnato = "";

  private boolean esistePdfBollettinoGoadev = false;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private Map<String, Boolean> mapVisibleDettagli;

  private boolean visibilitaDettaglio = false;

  public FascicoloTabContentPagamentiPanel(String id) {
    super(id);

    mapVisibleDettagli = new HashMap<String, Boolean>();

    setOutputMarkupId(true);
  }

  public FascicoloTabContentPagamentiPanel(
      String id,
      List<DatiPagamentiBollettiniRiepilogativiEstesi> datiPagamentiBollettiniRiepilogativiEstesi) {
    super(id);

    this.datiPagamentiBollettiniRiepilogativiEstesi = datiPagamentiBollettiniRiepilogativiEstesi;

    fillDati(datiPagamentiBollettiniRiepilogativiEstesi);

    mapVisibleDettagli = new HashMap<String, Boolean>();

    setOutputMarkupId(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DatiPagamentiBollettiniRiepilogativiEstesi> lista =
        (List<DatiPagamentiBollettiniRiepilogativiEstesi>) dati;

    PageableListView<DatiPagamentiBollettiniRiepilogativiEstesi> listViewPagamenti =
        new PageableListView<DatiPagamentiBollettiniRiepilogativiEstesi>("box", lista, 4) {

          private static final long serialVersionUID = -7879404968602277779L;

          @Override
          protected void populateItem(ListItem<DatiPagamentiBollettiniRiepilogativiEstesi> item) {
            final DatiPagamentiBollettiniRiepilogativiEstesi datiPagamenti = item.getModelObject();

            int indice = item.getIndex();
            item.setMarkupId("boxCard" + indice);

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaBollettino(datiPagamenti));
            item.add(icona);

            Label idBollettino = new Label("idBollettino", datiPagamenti.getIdBollettino());
            idBollettino.setVisible(PageUtil.isStringValid(datiPagamenti.getIdBollettino()));
            item.add(idBollettino);

            String statoPagamento = "";

            if (LabelFdCUtil.checkIfNotNull(datiPagamenti.getDatiRiepilogativi())) {
              if (LabelFdCUtil.checkIfNotNull(datiPagamenti.getDatiRiepilogativi().getAnnullato())
                  && datiPagamenti.getDatiRiepilogativi().getAnnullato()) {
                statoPagamento = "ANNULLATO";
              } else {
                statoPagamento =
                    LabelFdCUtil.checkIfNull(datiPagamenti.getIsPagato())
                            || !datiPagamenti.getIsPagato()
                        ? StatoPagamentoBollettinoMensa.PAGAMENTO_DA_EFFETTUARE.toString()
                        : StatoPagamentoBollettinoMensa.PAGAMENTO_EFFETTUATO.toString();
              }
            }

            Label stato = new Label("statoBollettino", statoPagamento);
            item.add(stato);

            Label idDebito = new Label("idDebito", datiPagamenti.getIdentificativoDebito());
            idDebito.setVisible(PageUtil.isStringValid(datiPagamenti.getIdentificativoDebito()));
            item.add(idDebito);

            Label annoScolastico =
                new Label("annoScolastico", datiPagamenti.getAnnoScolasticoTestuale());
            annoScolastico.setVisible(
                PageUtil.isStringValid(datiPagamenti.getAnnoScolasticoTestuale()));
            item.add(annoScolastico);

            Label periodo = new Label("periodo", datiPagamenti.getPeriodoPagamentoTestuale());
            periodo.setVisible(PageUtil.isStringValid(datiPagamenti.getPeriodoPagamentoTestuale()));
            item.add(periodo);

            LocalDateLabel dataScadenza =
                new LocalDateLabel("dataScadenza", datiPagamenti.getDataScadenza());
            dataScadenza.setVisible(datiPagamenti.getDataScadenza() != null);
            item.add(dataScadenza);

            ImportoEuroLabel importo =
                new ImportoEuroLabel("importo", datiPagamenti.getTotalePagareEuro());
            importo.setVisible(datiPagamenti.getTotalePagareEuro() != null);
            item.add(importo);

            LocalDateLabel dataEmissione =
                new LocalDateLabel("dataEmissione", datiPagamenti.getDataProtocolloEmissione());
            dataEmissione.setVisible(datiPagamenti.getDataProtocolloEmissione() != null);
            item.add(dataEmissione);

            LocalDateLabel dataAnnullamento =
                new LocalDateLabel(
                    "dataAnnullamento", datiPagamenti.getDatiRiepilogativi().getDataAnnullamento());
            dataAnnullamento.setVisible(
                datiPagamenti.getDatiRiepilogativi() != null
                    && datiPagamenti.getDatiRiepilogativi().getDataAnnullamento() != null);
            item.add(dataAnnullamento);

            Label bimestre = new Label("bimestre", datiPagamenti.getBimestreEmissioneRiferimento());
            bimestre.setVisible(datiPagamenti.getBimestreEmissioneRiferimento() != null);
            item.add(bimestre);

            String beneficiariValue = "";
            for (ItemBeneficiarioBollettino elemBeneficiari :
                datiPagamenti.getBeneficiariBollettino()) {
              beneficiariValue =
                  beneficiariValue.concat(
                      elemBeneficiari
                          .getNominativoBambino()
                          .concat(" - ")
                          .concat(elemBeneficiari.getCodiceBambino())
                          .concat("\n"));
            }
            MultiLineLabel beneficiari = new MultiLineLabel("beneficiari", beneficiariValue);
            beneficiari.setVisible(datiPagamenti.getBeneficiariBollettino() != null);
            beneficiari.setEscapeModelStrings(false);
            item.add(beneficiari);

            String impegnatoValue =
                datiPagamenti
                    .getNominativoImpegnato()
                    .concat("\n")
                    .concat(datiPagamenti.getCfImpegnato())
                    .concat("\n")
                    .concat(datiPagamenti.getIdImpegnato());
            MultiLineLabel impegnato = new MultiLineLabel("impegnato", impegnatoValue);
            impegnato.setVisible(PageUtil.isStringValid(impegnatoValue));
            impegnato.setEscapeModelStrings(false);
            item.add(impegnato);

            Label codiceAvviso = new Label("codiceAvviso", datiPagamenti.getCodiceAvviso());
            codiceAvviso.setVisible(PageUtil.isStringValid(datiPagamenti.getCodiceAvviso()));
            item.add(codiceAvviso);

            cfImpegnato = datiPagamenti.getCfImpegnato();

            WebMarkupContainer containerBottoniDettagli =
                new WebMarkupContainer("containerBottoniDettagli");
            containerBottoniDettagli.setVisible(false);
            containerBottoniDettagli.setOutputMarkupId(true);
            containerBottoniDettagli.setOutputMarkupPlaceholderTag(true);
            item.addOrReplace(containerBottoniDettagli);

            mapVisibleDettagli.put(datiPagamenti.getIdBollettino(), false);

            LaddaAjaxLink<String> btnDettagli =
                new LaddaAjaxLink<String>("btnDettagli", Type.Primary) {

                  private static final long serialVersionUID = 9187577520687546197L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {

                    for (String key : mapVisibleDettagli.keySet()) {
                      if (key.equalsIgnoreCase(datiPagamenti.getIdBollettino())) {
                        if (mapVisibleDettagli.get(key)) {
                          mapVisibleDettagli.replace(datiPagamenti.getIdBollettino(), false);
                        } else {
                          mapVisibleDettagli.replace(datiPagamenti.getIdBollettino(), true);
                        }
                      }
                    }

                    containerBottoniDettagli.addOrReplace(
                        createLinkBollettino("pdfBollettino2", datiPagamenti));

                    try {

                      if (!datiPagamenti.getIsPagato()
                          && PageUtil.isStringValid(datiPagamenti.getCodiceAvviso())) {
                        containerBottoniDettagli.addOrReplace(
                            createLinkAvviso("scaricaAvviso2", datiPagamenti));
                      } else {
                        containerBottoniDettagli.addOrReplace(
                            new WebMarkupContainer("scaricaAvviso2").setVisible(false));
                      }

                      if (datiPagamenti.getIsPagato()) {
                        containerBottoniDettagli.addOrReplace(
                            createLinkRicevuta("btnRicevuta2", datiPagamenti));
                      } else {
                        containerBottoniDettagli.addOrReplace(
                            new WebMarkupContainer("btnRicevuta2").setVisible(false));
                      }

                      AbstractLink linkPaga = null;

                      if (!datiPagamenti.getIsPagato()
                          && datiPagamenti.getImportoMIP() != 0.0
                          && (LabelFdCUtil.checkIfNotNull(datiPagamenti.getIuv())
                              && !datiPagamenti.getIuv().equalsIgnoreCase("-"))) {
                        linkPaga = creaBtnPaga(datiPagamenti);
                      }
                      if (linkPaga != null) {
                        containerBottoniDettagli.addOrReplace(linkPaga);
                      } else {
                        containerBottoniDettagli.addOrReplace(
                            new WebMarkupContainer("btnPagamento").setVisible(false));
                      }

                    } catch (BusinessException e) {
                      log.error(
                          "Errore MIP avviso o ricevuta in bollettini scuola: " + e.getMessage());
                    }

                    visibilitaDettaglio = mapVisibleDettagli.get(datiPagamenti.getIdBollettino());
                    setVisible(true);
                    containerBottoniDettagli.setVisible(visibilitaDettaglio);
                    target.add(item);
                  }
                };
            btnDettagli.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString(
                            "FascicoloTabContentPagamentiPanel.dettagli",
                            FascicoloTabContentPagamentiPanel.this)));

            btnDettagli.setOutputMarkupId(true);
            btnDettagli.setOutputMarkupPlaceholderTag(true);
            btnDettagli.setMarkupId("dettagli" + indice);

            item.addOrReplace(btnDettagli);
          }
        };

    addOrReplace(listViewPagamenti);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listViewPagamenti);
    paginazioneFascicolo.setVisible(lista.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private AttributeAppender getCssIconaBollettino(DatiPagamentiBollettiniRiepilogativiEstesi dati) {
    String css = "col-3 color-black icon-tributi";

    if (LabelFdCUtil.checkIfNotNull(dati)) {

      log.debug("CP dati bollettino = " + dati);

      if (LabelFdCUtil.checkIfNotNull(dati.getDatiRiepilogativi())
          && LabelFdCUtil.checkIfNotNull(dati.getDatiRiepilogativi().getAnnullato())
          && dati.getDatiRiepilogativi().getAnnullato()) {
        css = "col-3 color-fc-secondary icon-tributi";
      } else {
        if (dati.getIsPagato()) {
          css = "col-3 color-green-pagamenti-in-regola icon-tributi";
        } else {
          css = "col-3 color-red-pagamenti-non-in-regola icon-tributi";
        }
      }
    }

    return new AttributeAppender("class", " " + css);
  }

  private Component createLinkBollettino(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    ResourceLink<?> linkPdfBollettino = downloadPdfBollettino(idWicket, bollettino);
    return linkPdfBollettino;
  }

  private Component createLinkAvviso(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino)
      throws BusinessException {
    ResourceLink<?> linkPdfAvviso = downloadPdfAvviso(idWicket, bollettino);
    linkPdfAvviso.setVisible(!bollettino.getIsPagato());
    return linkPdfAvviso;
  }

  private Component createLinkRicevuta(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino)
      throws BusinessException {
    ResourceLink<?> linkPdfRicevuta = downloadPdfRicevuta(idWicket, bollettino);
    return linkPdfRicevuta;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfBollettino(
      String idWicket, final DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    final FascicoloPdfResources pdfResourceByte;
    String nomePdf = "BollettinoRefezione_" + calculateString(bollettino);

    byte[] pdfBytes = bollettino.getPdfBollettino();
    if (pdfBytes == null || pdfBytes.length == 0) {
      try {
        pdfBytes =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getPdfBollettino(bollettino.getIdBollettino());
      } catch (BusinessException | ApiException ex) {
        log.error("downloadPdfRicevuta | getPdfBollettino: " + ex.getMessage());
      }
    }

    pdfResourceByte = new FascicoloPdfResources(nomePdf, pdfBytes);
    ResourceLink toRet = pdfResourceByte.getResourceLink(idWicket);
    toRet.setVisible(pdfBytes != null);

    setEsistePdfBollettinoGoadev(pdfBytes != null ? true : false);

    return toRet;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfAvviso(
      String idWicket, final DatiPagamentiBollettiniRiepilogativiEstesi bollettino)
      throws BusinessException {

    try {
      log.debug("CP downloadPdfAvviso ");

      final FascicoloPdfResources pdfResourceByte;
      String nomePdfAvviso = "AvvisoBollettino_";

      String servizio = "SCUOLA_RISTO";
      String numeroDocumento = bollettino.getIdentificativoDebito().trim().concat("_0");
      boolean ottieniPdf = true;
      boolean attualizza = true;

      AvvisoPagamento avvisoPagamento =
          ServiceLocator.getInstance()
              .getServiziMipVerticali()
              .getAvvisoPagamento(
                  bollettino.getCfImpegnato(),
                  servizio,
                  bollettino.getAnnoRiferimento().longValue(),
                  numeroDocumento,
                  ottieniPdf,
                  attualizza);
      byte[] bytePdfAvviso = null;
      if (avvisoPagamento.getPdfAvviso() != null) {
        bytePdfAvviso = avvisoPagamento.getPdfAvviso().getFile();
        nomePdfAvviso = avvisoPagamento.getPdfAvviso().getNomeFile();
      }

      pdfResourceByte = new FascicoloPdfResources(nomePdfAvviso, bytePdfAvviso);
      ResourceLink resourceLink = pdfResourceByte.getResourceLink(idWicket);
      return resourceLink;

    } catch (BusinessException | ApiException ex) {
      log.error("downloadPdfAvviso scuola : " + ex.getMessage());
      throw new BusinessException(ex.getMessage());
    }
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfRicevuta(
      String idWicket, final DatiPagamentiBollettiniRiepilogativiEstesi bollettino)
      throws BusinessException {

    final FascicoloPdfResources pdfResourceByte;
    String nomePdfRicevuta = "";

    RicevutaPagamento ricevutaPagamento = getRicevutaMipPerBollettinoMensa(bollettino);

    byte[] bytePdfRicevuta = null;
    if (ricevutaPagamento != null && ricevutaPagamento.getPdfRicevuta() != null) {
      bytePdfRicevuta = ricevutaPagamento.getPdfRicevuta().getFile();
      nomePdfRicevuta = ricevutaPagamento.getPdfRicevuta().getNomeFile();
    }

    pdfResourceByte = new FascicoloPdfResources(nomePdfRicevuta, bytePdfRicevuta);

    ResourceLink resourceLink = pdfResourceByte.getResourceLink(idWicket);
    resourceLink.setVisible(bytePdfRicevuta != null);
    return resourceLink;
  }

  private String calculateString(final DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    String cfImpegnato = bollettino.getCfImpegnato();
    String annoScolastico = bollettino.getAnnoRiferimento().toString();
    String periodo = bollettino.getPeriodoPagamentoTestuale();
    String idDebito = bollettino.getIdentificativoDebito();
    return cfImpegnato + "_" + annoScolastico + "_" + periodo + "_" + idDebito + ".pdf";
  }

  public boolean isEsistePdfBollettinoGoadev() {
    return esistePdfBollettinoGoadev;
  }

  public void setEsistePdfBollettinoGoadev(boolean esistePdfBollettinoGoadev) {
    this.esistePdfBollettinoGoadev = esistePdfBollettinoGoadev;
  }

  private RicevutaPagamento getRicevutaMipPerBollettinoMensa(
      DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    log.debug("CP getRicevutaMipPerBollettinoMensa");

    RicevutaPagamento ricevutaPagamento = new RicevutaPagamento();

    try {
      List<RicevutaPagamento> listaRicevute =
          ServiceLocator.getInstance()
              .getServiziMipVerticali()
              .getRicevutaPerBollettinoMensa(getUtente(), bollettino);
      ricevutaPagamento =
          listaRicevute.stream()
              .filter(
                  elem ->
                      elem.getNumeroDocumento()
                          .equalsIgnoreCase(
                              bollettino.getIdentificativoDebito().trim().concat("_0")))
              .findAny()
              .orElse(new RicevutaPagamento());
    } catch (BusinessException | ApiException e) {
      log.error("Errore get ricevuta scuola da Mip per codice avviso" + e.getMessage());
    }

    return ricevutaPagamento;
  }

  private AbstractLink creaBtnPaga(DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    String wicketID = "btnPagamento";
    MipData data = new MipData();
    data = creaMipData(bollettino);
    return creaBottoneMipPagoPA(bollettino, wicketID, data);
  }

  private MipData creaMipData(DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    String servizio = "SCUOLA_RISTO";

    MipData mipData = new MipData();
    mipData.setImportoAvviso(BigDecimal.valueOf(bollettino.getImportoMIP()));
    mipData.setNumeroDocumento(bollettino.getIuv());
    mipData.setIdServizio(servizio);
    mipData.setUtente(getUtente());

    return mipData;
  }

  private Link creaBottoneMipPagoPA(
      DatiPagamentiBollettiniRiepilogativiEstesi bollettino, String wicketID, MipData data) {

    @SuppressWarnings("rawtypes")
    Link link =
        new Link(wicketID) {

          private static final long serialVersionUID = 3046533860537802513L;

          @Override
          public void onClick() {
            PagamentoOnLinePage page = new PagamentoOnLinePage(data);
            setResponsePage(page);
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    return link;
  }
}
