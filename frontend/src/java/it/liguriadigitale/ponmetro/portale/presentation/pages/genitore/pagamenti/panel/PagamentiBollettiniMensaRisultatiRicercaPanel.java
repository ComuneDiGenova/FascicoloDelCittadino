package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.resource.FascicoloPdfResources;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.ItemBeneficiarioBollettino;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PagamentiBollettiniMensaRisultatiRicercaPanel extends BasePanel {

  private static final long serialVersionUID = 5117716591759958633L;

  String cfImpegnato = "";

  public PagamentiBollettiniMensaRisultatiRicercaPanel(String id) {
    super(id);
    setOutputMarkupId(true);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {

    List<DatiPagamentiBollettiniRiepilogativiEstesi> lista =
        (List<DatiPagamentiBollettiniRiepilogativiEstesi>) dati;

    List<Legenda> listaLegenda =
        ServiceLocator.getInstance().getServiziRistorazione().getListaLegenda();
    LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
    addOrReplace(legendaPanel);

    List<DatiPagamentiBollettiniRiepilogativiEstesi> listaOrdinataPerAnno =
        new ArrayList<DatiPagamentiBollettiniRiepilogativiEstesi>();
    if (lista != null) {
      listaOrdinataPerAnno =
          lista.stream()
              .sorted(
                  Comparator.comparing(
                          DatiPagamentiBollettiniRiepilogativiEstesi::getAnnoRiferimento)
                      .reversed())
              .collect(Collectors.toList());
    }

    Integer annoCorrente = LocalDateUtil.today().getYear();
    final Integer anniDaMostrare = 5;
    Integer ultimoAnnoDaMostrare = annoCorrente - anniDaMostrare;
    List<ITab> listaTabsAnni = new ArrayList<>();

    while (annoCorrente > ultimoAnnoDaMostrare) {
      final String anno = annoCorrente.toString();

      List<DatiPagamentiBollettiniRiepilogativiEstesi> listaDatiPagamentiFiltrataPerAnno =
          listaOrdinataPerAnno.stream()
              .filter(elem -> (String.valueOf(elem.getAnnoRiferimento()).equalsIgnoreCase(anno)))
              .collect(Collectors.toList());

      Integer annoScolastico = annoCorrente + 1;
      String auxAnnoScolastico = anno.concat("/").concat(annoScolastico.toString());

      AbstractTab tab =
          new AbstractTab(new Model<>(auxAnnoScolastico)) {

            private static final long serialVersionUID = 8497174587034781906L;

            @Override
            public Panel getPanel(String panelId) {
              return new FascicoloTabContentPagamentiPanel(
                  panelId, listaDatiPagamentiFiltrataPerAnno);
            }
          };
      if (listaDatiPagamentiFiltrataPerAnno != null
          && !listaDatiPagamentiFiltrataPerAnno.isEmpty()) {
        listaTabsAnni.add(tab);
      }

      annoCorrente = annoCorrente - 1;
    }

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsAnni);
    fascicoloTabPanel.setOutputMarkupId(true);
    // TODO gestire visibilita se e solo se fa l'impegnato
    addOrReplace(fascicoloTabPanel);

    WebMarkupContainer containerNonImpegnato = new WebMarkupContainer("containerNonImpegnato");
    boolean containerNonImpegnatoVisible = false;
    if (cfImpegnato.equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
      containerNonImpegnatoVisible = true;
    }
    containerNonImpegnato.setVisible(containerNonImpegnatoVisible);
    addOrReplace(containerNonImpegnato);

    WebMarkupContainer listaTabsBollettiniVuota =
        new WebMarkupContainer("listaTabsBollettiniVuota");
    listaTabsBollettiniVuota.setVisible(listaTabsAnni.isEmpty());
    addOrReplace(listaTabsBollettiniVuota);

    WebMarkupContainer info = new WebMarkupContainer("info");
    addOrReplace(info);
  }

  @SuppressWarnings("unused")
  private AttributeAppender getCssIconaBollettino(DatiPagamentiBollettiniRiepilogativiEstesi dati) {
    String css = "";
    if (dati.getIsPagato()) {
      css = "col-3 color-fc-success icon-tributi";
    } else {
      css = "col-3 color-fc-danger icon-tributi";
    }

    if (LabelFdCUtil.checkIfNotNull(dati.getDatiRiepilogativi())
        && LabelFdCUtil.checkIfNotNull(dati.getDatiRiepilogativi())
        && dati.getDatiRiepilogativi().getAnnullato()) {
      css = "col-3 color-fc-secondary icon-tributi";
    }

    return new AttributeAppender("class", " " + css);
  }

  private String calculateString(final DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    String cfImpegnato = bollettino.getCfImpegnato();
    String annoScolastico = bollettino.getAnnoRiferimento().toString();
    String periodo = bollettino.getPeriodoPagamentoTestuale();
    String idDebito = bollettino.getIdentificativoDebito();
    return cfImpegnato + "_" + annoScolastico + "_" + periodo + "_" + idDebito + ".pdf";
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
    toRet.setVisible(bollettino.getPdfBollettino() != null);
    return toRet;
  }

  @SuppressWarnings("unused")
  private Component createLinkBollettino(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    ResourceLink<?> linkPdfBollettino = downloadPdfBollettino(idWicket, bollettino);
    linkPdfBollettino.setVisible(true);
    return linkPdfBollettino;
  }
}

class ListViewCustom<T> extends ListView<T> {
  private static Log log = LogFactory.getLog(ListViewCustom.class);

  private static final long serialVersionUID = 7078593348610041331L;

  static final String URL_PER_PAGARE =
      "https://pagamenti.comune.genova.it/mip-portale/InitServizio.do?idServizio=PAIUV";
  static final String URL_PER_RICEVUTA =
      "https://mipservercig.comune.genova.it/mip-portale/InitServizio.do?idServizio=VISRT";

  public ListViewCustom(String id, List<T> list) {
    super(id, list);
  }

  @Override
  protected void populateItem(ListItem<T> item) {
    DatiPagamentiBollettiniRiepilogativiEstesi bollettino =
        (DatiPagamentiBollettiniRiepilogativiEstesi) item.getModelObject();

    // item.addOrReplace(new Label("valoreDataEmissione",
    // LocalDateUtil.getDataFormatoEuropeo(bollettino.getDataProtocolloEmissione())));

    item.addOrReplace(new Label("valoreAnnoScolastico", bollettino.getAnnoScolasticoTestuale()));
    item.addOrReplace(new Label("valorePeriodo", bollettino.getPeriodoPagamentoTestuale()));

    ListView<ItemBeneficiarioBollettino> listViewBeneficiari =
        new ListView<ItemBeneficiarioBollettino>(
            "listValoreBeneficiari", bollettino.getBeneficiariBollettino()) {

          private static final long serialVersionUID = -5008809569319344479L;

          @Override
          protected void populateItem(ListItem<ItemBeneficiarioBollettino> itemBeneficiario) {
            ItemBeneficiarioBollettino beneficiario = itemBeneficiario.getModelObject();
            itemBeneficiario.addOrReplace(
                new Label("beneficiarioNominativo", beneficiario.getNominativoBambino()));
            itemBeneficiario.addOrReplace(
                new Label("beneficiarioCodice", beneficiario.getCodiceBambino()));
          }
        };

    item.addOrReplace(listViewBeneficiari);
    item.addOrReplace(
        new Label(
            "valoreDataScadenza",
            LocalDateUtil.getDataFormatoEuropeo(bollettino.getDataScadenza())));
    item.addOrReplace(
        new Label(
            "valoreImporto",
            bollettino.getTotalePagareEuro().setScale(2, BigDecimal.ROUND_HALF_UP) + "€"));

    item.addOrReplace(createLinkBollettino("bottoneDownloadBollettino", bollettino));
    item.addOrReplace(createLinkEsternoPaga("bottonePaga", bollettino));
    item.addOrReplace(createLinkEsternoRicevuta("bottoneDownloadRicevuta", bollettino));
  }

  private ResourceLink<?> createLinkBollettino(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    ResourceLink<?> linkPdfBollettino = downloadPdfBollettino(idWicket, bollettino);
    linkPdfBollettino.setVisible(true);
    return linkPdfBollettino;
  }

  private ExternalLink createLinkEsternoPaga(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    ExternalLink btnPagamento = new ExternalLink(idWicket, URL_PER_PAGARE);
    return btnPagamento;
  }

  private ExternalLink createLinkEsternoRicevuta(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    ExternalLink btnPagamento = new ExternalLink(idWicket, URL_PER_RICEVUTA);
    return btnPagamento;
  }

  @SuppressWarnings("unused")
  private ResourceLink<?> createLinkRicevuta(
      String idWicket, DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    ResourceLink<?> linkPdfRicevuta = downloadPdfRicevuta(idWicket, bollettino);
    return linkPdfRicevuta;
  }

  private String calculateString(final DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    String cfImpegnato = bollettino.getCfImpegnato();
    String annoScolastico = bollettino.getAnnoRiferimento().toString();
    String periodo = bollettino.getPeriodoPagamentoTestuale();
    String idDebito = bollettino.getIdentificativoDebito();
    return cfImpegnato + "_" + annoScolastico + "_" + periodo + "_" + idDebito + ".pdf";
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
    toRet.setVisible(bollettino.getPdfBollettino() != null);
    return toRet;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfRicevuta(
      String idWicket, final DatiPagamentiBollettiniRiepilogativiEstesi bollettino) {
    final FascicoloPdfResources pdfResourceByte;
    String nomePdf = "BollettinoRefezione_" + calculateString(bollettino);

    byte[] pdfBytes = bollettino.getPdfRicevuta();
    if ((pdfBytes == null || pdfBytes.length == 0)
        && (bollettino.getIsPagato() != null && bollettino.getIsPagato())) {
      try {
        pdfBytes =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getPdfRicevuta(bollettino.getIdentificativoDebito());
      } catch (BusinessException | ApiException ex) {
        log.error("downloadPdfRicevuta | getPdfBollettino: " + ex.getMessage());
      }
    }

    pdfResourceByte = new FascicoloPdfResources(nomePdf, pdfBytes);
    ResourceLink toRet = pdfResourceByte.getResourceLink(idWicket);
    toRet.setVisible(bollettino.getPdfRicevuta() != null);
    return toRet;
  }
}
;
