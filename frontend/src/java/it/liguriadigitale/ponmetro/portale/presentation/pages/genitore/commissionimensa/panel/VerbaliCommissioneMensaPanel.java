package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.commissionimensa.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.commissionimensa.model.Allegato;
import it.liguriadigitale.ponmetro.commissionimensa.model.Audit;
import it.liguriadigitale.ponmetro.commissionimensa.model.VerbaliAudit;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class VerbaliCommissioneMensaPanel extends BasePanel {

  private static final long serialVersionUID = 558580363530446050L;

  private static final String ICON_COMMISSIONE_MENSA = "color-orange col-3 icon-verbali-mensa";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  protected UtenteServiziRistorazione iscritto;

  public VerbaliCommissioneMensaPanel(String id) {
    super(id);
  }

  public VerbaliCommissioneMensaPanel(UtenteServiziRistorazione iscritto) {
    super("commissioneMensaPanel");
    this.iscritto = iscritto;
    createFeedBackPanel();

    List<Audit> listaVerbaliCommissioneMensa = popolaListaVerbaliCommissioniMensa(iscritto);
    fillDati(listaVerbaliCommissioneMensa);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    setOutputMarkupId(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Audit> listaVerbali = (List<Audit>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(
        (LabelFdCUtil.checkIfNull(listaVerbali))
            || (LabelFdCUtil.checkIfNotNull(listaVerbali)
                && LabelFdCUtil.checkEmptyList(listaVerbali)));
    addOrReplace(listaVuota);

    PageableListView<Audit> listView =
        new PageableListView<Audit>("box", listaVerbali, 4) {

          private static final long serialVersionUID = -4441709262651122866L;

          @Override
          protected void populateItem(ListItem<Audit> item) {

            final Audit audit = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaCommissioniMensa());
            icona.add(getColoreIconaCommissioniMensa());
            item.addOrReplace(icona);

            Label identificativo = new Label("identificativo", audit.getId());
            identificativo.setVisible(LabelFdCUtil.checkIfNotNull(audit.getId()));
            item.addOrReplace(identificativo);

            Label centroProduzione =
                new Label("centroProduzione", audit.getCentroPreparazionePasti());
            centroProduzione.setVisible(PageUtil.isStringValid(audit.getCentroPreparazionePasti()));
            item.addOrReplace(centroProduzione);

            Label tipologia = new Label("tipologia", audit.getTipologiaDesc());
            tipologia.setVisible(PageUtil.isStringValid(audit.getTipologiaDesc()));
            item.addOrReplace(tipologia);

            LocalDateLabel dataEffettiva =
                new LocalDateLabel("dataEffettiva", audit.getDataEffettiva());
            dataEffettiva.setVisible(LabelFdCUtil.checkIfNotNull(audit.getDataEffettiva()));
            item.addOrReplace(dataEffettiva);

            LocalDateLabel dataFine = new LocalDateLabel("dataFine", audit.getDataFine());
            dataFine.setVisible(LabelFdCUtil.checkIfNotNull(audit.getDataFine()));
            item.addOrReplace(dataFine);

            Label unita = new Label("unita", audit.getUnitaTerrLabel());
            unita.setVisible(PageUtil.isStringValid(audit.getUnitaTerrLabel()));
            item.addOrReplace(unita);

            Label descrizione = new Label("descrizione", audit.getDescrizione());
            descrizione.setVisible(PageUtil.isStringValid(audit.getDescrizione()));
            item.addOrReplace(descrizione);

            Label commissario1 = new Label("commissario1", audit.getCommissario1());
            commissario1.setVisible(PageUtil.isStringValid(audit.getCommissario1()));
            item.addOrReplace(commissario1);

            Label commissario2 = new Label("commissario2", audit.getCommissario2());
            commissario2.setVisible(PageUtil.isStringValid(audit.getCommissario2()));
            item.addOrReplace(commissario2);

            Label protocollo = new Label("protocollo", audit.getProtocollo());
            protocollo.setVisible(PageUtil.isStringValid(audit.getProtocollo()));
            item.addOrReplace(protocollo);

            Label azienda = new Label("azienda", audit.getAzienda());
            azienda.setVisible(PageUtil.isStringValid(audit.getAzienda()));
            item.addOrReplace(azienda);

            List<VerbaliAudit> listaVerbali = new ArrayList<VerbaliAudit>();
            if (LabelFdCUtil.checkIfNotNull(audit.getVerbali())) {
              listaVerbali = audit.getVerbali();
            }
            ListView<VerbaliAudit> listViewVerbali =
                new ListView<VerbaliAudit>("boxVerbali", listaVerbali) {

                  private static final long serialVersionUID = 6154152512355899437L;

                  @Override
                  protected void populateItem(ListItem<VerbaliAudit> itemVerbale) {
                    VerbaliAudit verbale = itemVerbale.getModelObject();

                    Label idVerbale = new Label("idVerbale", verbale.getId());
                    idVerbale.setVisible(LabelFdCUtil.checkIfNotNull(verbale.getId()));
                    itemVerbale.addOrReplace(idVerbale);

                    Label tipoMensa = new Label("tipoMensa", verbale.getTipoMensa());
                    tipoMensa.setVisible(LabelFdCUtil.checkIfNotNull(verbale.getTipoMensa()));
                    itemVerbale.addOrReplace(tipoMensa);

                    itemVerbale.addOrReplace(scaricaVerbale("btnScarica", verbale.getId()));
                  }
                };
            listViewVerbali.setRenderBodyOnly(true);
            item.addOrReplace(listViewVerbali);

            WebMarkupContainer titoloVerbali = new WebMarkupContainer("titoloVerbali");
            titoloVerbali.setVisible(!LabelFdCUtil.checkEmptyList(listaVerbali));
            item.addOrReplace(titoloVerbali);
          }
        };

    listView.setRenderBodyOnly(true);
    listView.setVisible(
        LabelFdCUtil.checkIfNotNull(listaVerbali) && !LabelFdCUtil.checkEmptyList(listaVerbali));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(listaVerbali)
            && !LabelFdCUtil.checkEmptyList(listaVerbali)
            && listaVerbali.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private AttributeAppender getCssIconaCommissioniMensa() {
    String css = ICON_COMMISSIONE_MENSA;
    return new AttributeAppender("class", " " + css);
  }

  private AttributeModifier getColoreIconaCommissioniMensa() {
    AttributeModifier coloreIcona = new AttributeModifier("style", "color: #f95e01");

    return coloreIcona;
  }

  private List<Audit> popolaListaVerbaliCommissioniMensa(UtenteServiziRistorazione iscritto) {
    try {
      String codiceScuola = "";
      if (PageUtil.isStringValid(iscritto.getCodiceScuola())) {
        codiceScuola = iscritto.getCodiceScuola();
      }
      return ServiceLocator.getInstance().getServiziCommissioniMensa().getAllAudit(codiceScuola);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private Component scaricaVerbale(String wicketId, BigDecimal idVerbale) {

    ResourceLink<?> linkFile = downloadPdf(wicketId, idVerbale);

    boolean isBtnVisibile = LabelFdCUtil.checkIfNotNull(idVerbale);
    if (LabelFdCUtil.checkIfNotNull(linkFile)) {
      linkFile.setVisible(isBtnVisibile);
      return linkFile;
    } else {
      WebMarkupContainer btnWicketId = new WebMarkupContainer(wicketId);
      btnWicketId.setVisible(false);
      addOrReplace(btnWicketId);
      return btnWicketId;
    }
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdf(String wicketId, final BigDecimal idVerbale) {

    final AbstractResource fileResourceByte;
    ResourceLink linkFile = null;

    try {
      if (LabelFdCUtil.checkIfNotNull(idVerbale)) {

        Allegato allegatoVerbale =
            ServiceLocator.getInstance().getServiziCommissioniMensa().getPdfVerbale(idVerbale);
        fileResourceByte =
            new AbstractResource() {

              private static final long serialVersionUID = -5641394300284431132L;

              @Override
              protected ResourceResponse newResourceResponse(final Attributes attributes) {
                final ResourceResponse r = new ResourceResponse();
                try {

                  String mimeType =
                      FileFdCUtil.getMimeTypeFileUploadAllegato(allegatoVerbale.getFile());

                  r.setFileName(allegatoVerbale.getNomeFile());
                  r.setContentType(mimeType);
                  r.setContentDisposition(ContentDisposition.INLINE);
                  r.setContentLength(allegatoVerbale.getFile().length);
                  r.setWriteCallback(
                      new WriteCallback() {
                        @Override
                        public void writeData(final Attributes attributes) {
                          attributes.getResponse().write(allegatoVerbale.getFile());
                        }
                      });

                  r.disableCaching();
                } catch (final Exception e) {
                  log.error("Errore durante scarico pdf allegato");
                }

                return r;
              }
            };

        linkFile =
            new ResourceLink(wicketId, fileResourceByte) {

              private static final long serialVersionUID = -125032136714917360L;

              @Override
              protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("target", "_blank");
                tag.put("title", "scarica FILE: " + allegatoVerbale.getNomeFile());
              }
            };
      }

    } catch (ApiException | BusinessException | IOException e) {
      log.error("Errore durante download allegato verbali commissioni mensa: " + e.getMessage());
    }

    return linkFile;
  }
}
