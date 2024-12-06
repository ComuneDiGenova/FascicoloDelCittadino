package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicolo;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.TentativoDiPagamento;
import it.liguriadigitale.ponmetro.portale.presentation.common.mip.MipErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.EuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.utilmieipagamenti.UtilMieiPagamenti;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class RateMipPanel extends BasePanel {

  private static final long serialVersionUID = -2341402078774644341L;

  private static final String ICON_RATA_PAGATA =
      "color-green-pagamenti-in-regola col-3 icon-tributi";
  private static final String ICON_RATA_DA_PAGARE =
      "color-red-pagamenti-non-in-regola col-3 icon-tributi";

  private static final String ID_SERVIZIO_TEST = "10025WEBIUVES";

  public RateMipPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    DebitoMipFascicolo debitoMipFascicolo = (DebitoMipFascicolo) dati;

    int indiceCard = debitoMipFascicolo.getIndex();

    List<TentativoDiPagamento> listaPagamenti = debitoMipFascicolo.getTentativiDiPagamento();

    ListView<TentativoDiPagamento> listView =
        new ListView<TentativoDiPagamento>("box", listaPagamenti) {

          private static final long serialVersionUID = -4126053312846429238L;

          @Override
          protected void populateItem(ListItem<TentativoDiPagamento> item) {
            TentativoDiPagamento tentativo = item.getModelObject();

            int indice = item.getIndex();

            item.setOutputMarkupId(true);
            item.setMarkupId("boxRata" + indice);

            WebMarkupContainer btnAccordion = new WebMarkupContainer("btnAccordion");
            btnAccordion.setOutputMarkupId(true);
            btnAccordion.setOutputMarkupPlaceholderTag(true);
            btnAccordion.setMarkupId("btnRata" + indiceCard);

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaRata(tentativo));
            btnAccordion.addOrReplace(icona);

            Label descrizione = getDescrizione(tentativo);
            AttributeAppender daPagare =
                new AttributeAppender("class", "color-red-pagamenti-non-in-regola");
            AttributeAppender pagato =
                new AttributeAppender("class", "color-green-pagamenti-in-regola");
            if (tentativo.isEsitoPagamento()) {
              descrizione.add(pagato);
            } else {
              descrizione.add(daPagare);
            }
            descrizione.setOutputMarkupId(true);
            descrizione.setMarkupId("rata" + indice);
            icona.addOrReplace(descrizione);

            String indiceCardERata = indiceCard + item.getMarkupId();

            AttributeAppender collapseAppender1 =
                new AttributeAppender("data-target", indiceCardERata);
            AttributeAppender collapseAppender2 =
                new AttributeAppender("aria-controls", indiceCardERata);

            AttributeModifier accordionAppender1 =
                new AttributeModifier("aria-labelledby", "rata" + indiceCardERata);
            AttributeAppender appenderShow = new AttributeAppender("class", " show");

            btnAccordion.add(collapseAppender1);
            btnAccordion.add(collapseAppender2);

            WebMarkupContainer accordionBody = new WebMarkupContainer("accordionBody");
            accordionBody.add(accordionAppender1);
            accordionBody.add(appenderShow);
            accordionBody.setOutputMarkupId(true);
            accordionBody.setMarkupId("accordion" + indiceCardERata);
            accordionBody.addOrReplace(getRata(tentativo));

            item.addOrReplace(btnAccordion);
            item.addOrReplace(accordionBody);

            item.setVisible(UtilMieiPagamenti.checkVisibilitaRata(tentativo));
          }

          private AttributeAppender getCssIconaRata(TentativoDiPagamento tentativoDiPagamento) {
            String css = "";
            if (tentativoDiPagamento.isEsitoPagamento()) {
              css = ICON_RATA_PAGATA;
            } else {
              css = ICON_RATA_DA_PAGARE;
            }
            return new AttributeAppender("class", " " + css);
          }
        };

    listView.setVisible(!listaPagamenti.isEmpty());
    listView.setOutputMarkupId(true);
    addOrReplace(listView);

    Double sumImportoPagato = 0.0;
    if (UtilMieiPagamenti.checkIfNull(debitoMipFascicolo.getImportoTotalePagato())) {
      sumImportoPagato =
          listaPagamenti.stream()
              .filter(elem -> UtilMieiPagamenti.checkINotNull(elem.getImportoPagato()))
              .map(elem -> elem.getImportoPagato())
              .collect(Collectors.summingDouble(Double::doubleValue));
    } else {
      sumImportoPagato = debitoMipFascicolo.getImportoTotalePagato();
    }
    EuroLabel importoTotalePagato = new EuroLabel("importoTotalePagato", sumImportoPagato);
    importoTotalePagato.setVisible(UtilMieiPagamenti.checkINotNull(sumImportoPagato));
    addOrReplace(importoTotalePagato);

    Double sumImportoDaPagare = 0.0;
    if (UtilMieiPagamenti.checkIfNull(debitoMipFascicolo.getImportoTotaleDaPagare())) {
      sumImportoDaPagare =
          listaPagamenti.stream()
              .filter(elem -> UtilMieiPagamenti.checkINotNull(elem.getImportoDaPagare()))
              .map(elem -> elem.getImportoDaPagare())
              .collect(Collectors.summingDouble(Double::doubleValue));

      boolean esisteRata0 =
          listaPagamenti.stream()
              .filter(elem -> UtilMieiPagamenti.checkINotNull(elem))
              .anyMatch(
                  elem ->
                      UtilMieiPagamenti.checkINotNull(elem.getRata())
                          && elem.getRata().equalsIgnoreCase("0"));
      if (esisteRata0 && listaPagamenti.size() > 1) {
        TentativoDiPagamento rata0 =
            listaPagamenti.stream()
                .filter(elem -> elem.getRata().equalsIgnoreCase("0"))
                .findAny()
                .orElse(null);
        if (UtilMieiPagamenti.checkINotNull(rata0)
            && UtilMieiPagamenti.checkINotNull(rata0.getImportoDaPagare())) {
          BigDecimal importoRata0 = new BigDecimal(rata0.getImportoDaPagare());
          BigDecimal importoTotaleDaPagareSenzaRata0 =
              new BigDecimal(sumImportoDaPagare).subtract(importoRata0);
          sumImportoDaPagare = importoTotaleDaPagareSenzaRata0.doubleValue();
        }
      }
    } else {
      sumImportoDaPagare = debitoMipFascicolo.getImportoTotaleDaPagare();
    }
    EuroLabel importoTotaleDaPagare = new EuroLabel("importoTotaleDaPagare", sumImportoDaPagare);
    importoTotaleDaPagare.setVisible(UtilMieiPagamenti.checkINotNull(sumImportoDaPagare));
    addOrReplace(importoTotaleDaPagare);
  }

  private NotEmptyLabel getDescrizione(TentativoDiPagamento tentativoDiPagamento) {
    String descrizione = "";

    if (UtilMieiPagamenti.checkINotNull(tentativoDiPagamento.getRata())) {
      if (tentativoDiPagamento.getRata().equalsIgnoreCase("0")) {
        if (tentativoDiPagamento.isEsitoPagamento()) {
          descrizione = getString("RateMipPanel.pagata");
        } else {
          descrizione = getString("RateMipPanel.rataUnica");
        }
      } else {
        descrizione =
            getString("RateMipPanel.rata").concat(" ").concat(tentativoDiPagamento.getRata());
        if (tentativoDiPagamento.isEsitoPagamento()) {
          descrizione = descrizione.concat(" - ").concat(getString("RateMipPanel.pagata"));
        } else {
          descrizione = descrizione.concat(" - ").concat(getString("RateMipPanel.daPagare"));
        }
      }
    }

    return new NotEmptyLabel("descrizione", descrizione);
  }

  private WebMarkupContainer getRata(TentativoDiPagamento tentativo) {
    WebMarkupContainer rata = new WebMarkupContainer("rata");

    Label iuv = new Label("iuv", tentativo.getIuv());
    iuv.setVisible(PageUtil.isStringValid(tentativo.getIuv()));
    rata.addOrReplace(iuv);

    Label codiceAvviso = new Label("codiceAvviso", tentativo.getCodiceAvviso());
    codiceAvviso.setVisible(PageUtil.isStringValid(tentativo.getCodiceAvviso()));
    rata.addOrReplace(codiceAvviso);

    EuroLabel importoDaPagare = new EuroLabel("importoDaPagare", tentativo.getImportoDaPagare());
    importoDaPagare.setVisible(UtilMieiPagamenti.checkINotNull(tentativo.getImportoDaPagare()));
    rata.addOrReplace(importoDaPagare);

    EuroLabel importoPagato = new EuroLabel("importoPagato", tentativo.getImportoPagato());
    importoPagato.setVisible(UtilMieiPagamenti.checkINotNull(tentativo.getImportoPagato()));
    rata.addOrReplace(importoPagato);

    // TODO rivedere orario indietro di 2 ore
    if (UtilMieiPagamenti.checkINotNull(tentativo.getDataPagamento())) {
      log.debug("CP tentativo data = " + tentativo.getDataPagamento());
      log.debug(
          "CP data pagamento = "
              + LocalDateUtil.getStringOffsetDateTime(tentativo.getDataPagamento()));
    }

    Label dataPagamento =
        new Label(
            "dataPagamento",
            LocalDateUtil.getStringFormattedByOffsetDateTime(tentativo.getDataPagamento()));
    dataPagamento.setVisibilityAllowed(
        UtilMieiPagamenti.checkINotNull(tentativo.getDataPagamento()));
    rata.addOrReplace(dataPagamento);

    ResourceLink linkAvviso = null;
    ResourceLink linkRicevuta = null;
    AbstractLink linkPaga = null;

    if (tentativo.isEsitoPagamento()) {
      linkRicevuta = downloadRicevutaPagamento(tentativo);
    }
    if (!tentativo.isEsitoPagamento()) {
      if (UtilMieiPagamenti.checkINotNull(tentativo.getCodiceAvviso())
          && !tentativo.getCodiceAvviso().isEmpty()) {
        linkAvviso = downloadAvvisoPagamento(tentativo);
      }

      if (UtilMieiPagamenti.checkINotNull(tentativo.getIuv())
          && !tentativo.getIuv().isEmpty()
          && UtilMieiPagamenti.checkINotNull(tentativo.getImportoDaPagare())) {
        linkPaga = creaBtnPaga(tentativo);
      }
    }

    if (UtilMieiPagamenti.checkINotNull(linkRicevuta)) {
      rata.addOrReplace(linkRicevuta);
    } else {
      rata.addOrReplace(new WebMarkupContainer("btnRicevuta").setVisible(false));
    }

    if (UtilMieiPagamenti.checkINotNull(linkAvviso)) {
      rata.addOrReplace(linkAvviso);
    } else {
      rata.addOrReplace(new WebMarkupContainer("btnAvviso").setVisible(false));
    }

    if (UtilMieiPagamenti.checkINotNull(linkPaga)) {
      rata.addOrReplace(linkPaga);
    } else {
      rata.addOrReplace(new WebMarkupContainer("btnPaga").setVisible(false));
    }

    return rata;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadAvvisoPagamento(TentativoDiPagamento tentativoDiPagamento) {

    AvvisoPagamento avvisoPagamento = getAvvisoPagamento(tentativoDiPagamento);

    final AbstractResource fileResourceByte;

    String nomeFile = avvisoPagamento.getPdfAvviso().getNomeFile();

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = -6236037976430258415L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {
              r.setFileName(nomeFile);
              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(avvisoPagamento.getPdfAvviso().getFile().length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(avvisoPagamento.getPdfAvviso().getFile());
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf avviso pagamento");
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink("btnAvviso", fileResourceByte) {

          private static final long serialVersionUID = -4928715221300886397L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };
    return linkFile;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadRicevutaPagamento(TentativoDiPagamento tentativoDiPagamento) {

    final AbstractResource fileResourceByte;

    String nomeFile = tentativoDiPagamento.getNomeRicevuta();

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = 7156210115098629385L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {
              r.setFileName(nomeFile);
              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(tentativoDiPagamento.getPdfRicevuta().length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(tentativoDiPagamento.getPdfRicevuta());
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf avviso pagamento");
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink("btnRicevuta", fileResourceByte) {

          private static final long serialVersionUID = -8798994734791628023L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };
    return linkFile;
  }

  private AvvisoPagamento getAvvisoPagamento(TentativoDiPagamento tentativoDiPagamento) {
    try {
      boolean ottieniPdf = true;
      boolean attualizza = true;

      return ServiceLocator.getInstance()
          .getServiziMipGlobali()
          .getAvvisoPagamentoDaChiave(
              getUtente(),
              tentativoDiPagamento.getServizio(),
              tentativoDiPagamento.getAnno(),
              tentativoDiPagamento
                  .getNumeroDocumento()
                  .concat("_")
                  .concat(tentativoDiPagamento.getRata()),
              ottieniPdf,
              attualizza);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API MIP GLOBALI", e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    }
  }

  private MipData creaMipData(TentativoDiPagamento tentativoDiPagamento) {
    MipData data = new MipData();
    data.setImportoAvviso(BigDecimal.valueOf(tentativoDiPagamento.getImportoDaPagare()));
    data.setNumeroDocumento(tentativoDiPagamento.getIuv());
    data.setIdServizio(ID_SERVIZIO_TEST);
    data.setUtente(getUtente());
    return data;
  }

  private AbstractLink creaBtnPaga(TentativoDiPagamento tentativoDiPagamento) {
    String wicketID = "btnPaga";
    MipData data = new MipData();
    data = creaMipData(tentativoDiPagamento);
    return creaBottoneMipPagoPA(tentativoDiPagamento, wicketID, data);
  }

  private Link creaBottoneMipPagoPA(
      TentativoDiPagamento tentativoDiPagamento, String wicketID, MipData data) {

    @SuppressWarnings("rawtypes")
    Link link =
        new Link(wicketID) {

          private static final long serialVersionUID = 1530004934584598084L;

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
