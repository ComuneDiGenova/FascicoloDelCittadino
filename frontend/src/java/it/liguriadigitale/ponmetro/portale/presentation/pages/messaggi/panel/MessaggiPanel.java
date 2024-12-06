package it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.messaggi.utente.model.DatiMessaggioUtente;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumAzione;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.messaggi.impl.ServiziMessaggiImpl;
import it.liguriadigitale.ponmetro.portale.business.messaggi.service.ServiziMessaggiService;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import it.liguriadigitale.ponmetro.portale.presentation.pages.messaggi.MessaggiPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;

public class MessaggiPanel extends BasePanel {

  private static final long serialVersionUID = -4930390410614798571L;

  // private static final String LABEL_BOTTONE_DA_LEGGERE = "Marca da
  // leggere";
  // private static final String LABEL_BOTTONE_LETTO = "Marca letto";
  // JIRA: FASCITT-52:
  private static final String LABEL_BOTTONE_IMPORTANTE = "segna come gi\u00E0 letto";
  private static final String LABEL_BOTTONE_NON_IMPORTANTE = "segna come da leggere";
  private static final Long FLAG_IMPORTANTE = 1L;

  //	private static final Long	FLAG_NON_IMPORTANTE				= 0L;

  public MessaggiPanel(LocalDate date, List<DatiMessaggioUtente> lista) {
    super("messaggiPanel");
    createFeedBackPanel();
    fillDati(lista);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DatiMessaggioUtente> listaMessaggi = (List<DatiMessaggioUtente>) dati;

    List<DatiMessaggioUtente> listaMessaggiDaVisualizzare = new ArrayList<>();

    if (getUtente().getListaFigli() == null || getUtente().getListaFigli().isEmpty()) {
      listaMessaggiDaVisualizzare =
          listaMessaggi.stream()
              .filter(
                  elem ->
                      getUtente().getListaFigli() == null
                          || getUtente().getListaFigli().isEmpty()
                              && !elem.getSezioneDenominazione().equalsIgnoreCase("ioGenitore"))
              .collect(Collectors.toList());
    } else {
      listaMessaggiDaVisualizzare = listaMessaggi;
    }

    ListView<DatiMessaggioUtente> listViewMessaggi =
        new ListView<DatiMessaggioUtente>("listaMessaggi", listaMessaggiDaVisualizzare) {

          private static final long serialVersionUID = -4574527428174497550L;

          @SuppressWarnings({"rawtypes", "serial"})
          @Override
          protected void populateItem(ListItem<DatiMessaggioUtente> item) {
            DatiMessaggioUtente messaggio = item.getModelObject();

            Label compartoDescrizione =
                new Label("compartoDescrizione", messaggio.getCompartoDenominazione());
            Label notificaTesto = new Label("notificaTesto", messaggio.getNotificaTesto());
            String sNotificaData =
                LocalDateTimeUtil.getStringByOffsetDateTime(
                    messaggio.getNotificaData(), "dd-MM-yyyy HH:mm");
            Label notificaData = new Label("notificaData", sNotificaData);

            item.add(compartoDescrizione);
            item.add(notificaTesto);
            item.add(notificaData);
            boolean messaggioImportante = isMessaggioImportante(messaggio);

            LaddaAjaxLink<?> bottoneImportante =
                new LaddaAjaxLink("segnaImportante", Type.Primary) {

                  @Override
                  public void onClick(AjaxRequestTarget target) {
                    ServiziMessaggiService messaggiService = new ServiziMessaggiImpl();
                    log.debug("apriMessaggio - bottone Apri ");
                    try {
                      if (messaggioImportante) {
                        messaggiService.setAzioneMessaggio(
                            getUtente(), messaggio, EnumAzione.SEGNA_COME_NON_IMPORTANTE);
                      } else {
                        messaggiService.setAzioneMessaggio(
                            getUtente(), messaggio, EnumAzione.SEGNA_COME_IMPORTANTE);
                      }
                      setResponsePage(new MessaggiPage());
                    } catch (BusinessException e) {
                      log.error(" -- errore apriMessaggio:", e);
                    } catch (ApiException e) {
                      log.error(" -- errore apriMessaggio:", e);
                    }
                  }

                  @Override
                  public MarkupContainer setDefaultModel(IModel model) {
                    return setDefaultModel(model);
                  }
                };

            String labelBottoneImportante =
                messaggioImportante ? LABEL_BOTTONE_IMPORTANTE : LABEL_BOTTONE_NON_IMPORTANTE;

            IconType iconType =
                new IconType("segnaImportante") {
                  @Override
                  public String cssClassName() {
                    if (messaggioImportante) {
                      String aperta = "fa fa-envelope-o";
                      bottoneImportante.add(
                          AttributeModifier.append("style", "background:#f95e01;"));
                      return aperta;
                    } else {
                      String chiusa = "fa fa-envelope-open-o";
                      return chiusa;
                    }
                  }
                };

            // bottoneImportante.add(new Label("buttontextImp",
            // labelBottoneImportante));
            bottoneImportante.setOutputMarkupId(true);
            bottoneImportante.setVisibilityAllowed(true);
            bottoneImportante.setLabel(Model.of(labelBottoneImportante));
            bottoneImportante.setIconType(iconType);

            item.add(bottoneImportante);

            String uriNotifica = messaggio.getNotificaUri() + "Page";
            Link vaiLinkInfo =
                new Link("vaiLink") {
                  @Override
                  public void onClick() {
                    Class<IRequestablePage> clazz;
                    clazz = (Class<IRequestablePage>) PageClassFinder.findClassByName(uriNotifica);
                    if (clazz.equals(Page404.class)) {
                      setVisible(false);
                    }
                    setResponsePage(clazz);
                  }

                  @Override
                  public MarkupContainer setDefaultModel(IModel model) {
                    return setDefaultModel(model);
                  }
                };
            vaiLinkInfo.setVisible(messaggio.getNotificaUri() != null);
            item.add(vaiLinkInfo);

            // BCRM-239
            //				if (isMessaggioImportante(messaggio) || isMessaggioDaLeggere(messaggio)) {
            //					item.add(new AttributeAppender("class", new Model("table table-warning"), " "));
            //				}
            if (isMessaggioImportante(messaggio) || isMessaggioDaLeggere(messaggio)) {
              item.add(new AttributeAppender("class", new Model("table"), " "));
            }
          }
        };

    WebMarkupContainer cell = new WebMarkupContainer("cell");
    cell.setVisible(!listaMessaggiDaVisualizzare.isEmpty());
    add(cell);

    Label messaggioListaVuota =
        new Label("messaggioListaVuota", getString("MessaggiPanel.listaVuota"));
    messaggioListaVuota.setVisible(listaMessaggiDaVisualizzare.isEmpty());
    add(messaggioListaVuota);

    cell.add(listViewMessaggi);

    // listViewMessaggi.setVisible(!listaMessaggi.isEmpty());
    // add(listViewMessaggi);
  }

  private boolean isMessaggioImportante(DatiMessaggioUtente messaggio) {
    return messaggio != null
        && messaggio.getStatoRecord() != null
        && messaggio.getStatoRecord().equals(FLAG_IMPORTANTE);
  }

  private boolean isMessaggioDaLeggere(DatiMessaggioUtente messaggio) {
    return messaggio != null && messaggio.getVisualizzazioneData() == null;
  }
}
