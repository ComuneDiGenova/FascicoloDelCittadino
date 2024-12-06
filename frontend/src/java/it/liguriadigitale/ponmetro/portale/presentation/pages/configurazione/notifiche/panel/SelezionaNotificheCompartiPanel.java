package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.notifiche.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiNotificaComparto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class SelezionaNotificheCompartiPanel extends BasePanel {

  private static final long serialVersionUID = -2890742926096005220L;

  private static Log log = LogFactory.getLog(SelezionaNotificheCompartiPanel.class);

  private static final String ATTRIBUTE_TITLE = "title";

  private Map<DatiNotificaComparto, Boolean> mapDatiNotifiche;

  private WebMarkupContainer containerAlertSalva;

  public SelezionaNotificheCompartiPanel(String id) {
    super(id);
    createFeedBackPanel();

    setOutputMarkupId(true);

    mapDatiNotifiche = new HashMap<DatiNotificaComparto, Boolean>();

    containerAlertSalva = new WebMarkupContainer("containerAlertSalva");
    containerAlertSalva.setVisible(false);
    containerAlertSalva.setOutputMarkupId(true);
    containerAlertSalva.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerAlertSalva);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DatiNotificaComparto> lista = (List<DatiNotificaComparto>) dati;

    ListView<DatiNotificaComparto> listView =
        new ListView<DatiNotificaComparto>("listaNotificheComparti", lista) {

          private static final long serialVersionUID = -7511025079138842978L;

          @Override
          protected void populateItem(ListItem<DatiNotificaComparto> item) {

            final DatiNotificaComparto datiNotifica = item.getModelObject();
            log.debug(
                "notifica:"
                    + datiNotifica.getComparto().getIdComparto()
                    + " "
                    + datiNotifica.getComparto().getDenominazioneComp()
                    + " "
                    + getAbilitazione(datiNotifica));

            String sezione = datiNotifica.getSezione().getDescrizioneSez();
            String comparto = datiNotifica.getComparto().getDenominazioneComp();
            String titleComparto = datiNotifica.getComparto().getDescrizioneComp();
            AttributeModifier modPresente =
                new AttributeModifier("style", "text-decoration:line-through");
            Label lSezione = new Label("sezione", sezione);
            Label lComparto = new Label("comparto", comparto);

            MultiLineLabel lNotifiche = new MultiLineLabel("notifiche", titleComparto);

            Label lAlert = new Label("alert", getString("SelezionaNotificheCompartiPanel.alert"));
            Boolean registrato =
                datiNotifica.getDataRegistrazione() != null
                    && datiNotifica.getDataDeregistrazione() == null;

            CheckBox check = new CheckBox("notifica", new Model<Boolean>(registrato));
            AttributeModifier titleCheckbox = new AttributeModifier(ATTRIBUTE_TITLE, titleComparto);
            check.add(titleCheckbox);
            check.add(
                new AjaxFormComponentUpdatingBehavior("click") {

                  private static final long serialVersionUID = -4422267292971138951L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    log.debug("CP click check " + datiNotifica + " - " + check.getModelObject());
                    mapDatiNotifiche.put(datiNotifica, check.getModelObject());
                    log.debug("CP map dopo " + mapDatiNotifiche);
                  }
                });

            if (!getAbilitazione(datiNotifica)) {
              lAlert.setVisible(true);
              check.setVisible(false);
              lComparto.add(modPresente);
            } else {
              lAlert.setVisible(false);
            }
            check.setOutputMarkupPlaceholderTag(true);
            check.setOutputMarkupId(true);
            item.add(check);
            item.add(lSezione, lComparto, lAlert, lNotifiche);
          }

          private Boolean getAbilitazione(final DatiNotificaComparto datiNotifica) {
            return datiNotifica.getComparto().getFlagAbilitazione()
                && datiNotifica.getSezione().getFlagAbilitazione();
          }
        };

    addOrReplace(listView);

    addOrReplace(creaBtnSalvaNotifiche());
  }

  private LaddaAjaxLink<Object> creaBtnSalvaNotifiche() {

    LaddaAjaxLink<Object> btnSalvaNotifiche =
        new LaddaAjaxLink<Object>("btnSalvaNotifiche", Type.Primary) {

          private static final long serialVersionUID = -5379245228521381646L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            log.debug("CP click salva notifiche " + mapDatiNotifiche);
            try {
              ServiceLocator.getInstance()
                  .getServiziConfigurazione()
                  .updateNotificheConBtnSalva(getUtente(), mapDatiNotifiche);

              containerAlertSalva.setVisible(true);
              target.add(containerAlertSalva);

            } catch (BusinessException | ApiException e) {
              log.error("Errore salvataggio notifiche:", e);
              SelezionaNotificheCompartiPanel.this.error("Errore durante il salvataggio");
            }
          }
        };

    btnSalvaNotifiche.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "SelezionaNotificheCompartiPanel.salva",
                    SelezionaNotificheCompartiPanel.this)));

    btnSalvaNotifiche.setOutputMarkupId(true);

    return btnSalvaNotifiche;
  }
}
