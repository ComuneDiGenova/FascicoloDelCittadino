package it.liguriadigitale.ponmetro.portale.presentation.components.alert;

import it.liguriadigitale.ponmetro.abbonamentiamt.model.Messaggio.TypeEnum;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class AlertBoxPanel<T extends Component> extends BasePanel {

  /*
   * Mostra messaggio salvato nella tabella: CFG_T_TESTI
   *
   */

  private static final long serialVersionUID = 6932115358510275930L;

  private static String DEFAULT_WICKET_ID = "messaggiInformativi";

  public AlertBoxPanel(String id, List<MessaggiInformativi> listaMessaggi) {
    super(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    fillDati(listaMessaggi);
  }

  public AlertBoxPanel(List<MessaggiInformativi> listaMessaggi) {
    this(DEFAULT_WICKET_ID, listaMessaggi);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<MessaggiInformativi> lista = (List<MessaggiInformativi>) dati;

    ListView<MessaggiInformativi> listView =
        new ListView<MessaggiInformativi>("lista", lista) {

          private static final long serialVersionUID = -6761858167284918287L;

          @Override
          protected void populateItem(ListItem<MessaggiInformativi> item) {
            final MessaggiInformativi messaggio = item.getModelObject();

            MultiLineLabel testo = new MultiLineLabel("testo", messaggio.getMessaggio());
            testo.setVisible(LabelFdCUtil.checkIfNotNull(messaggio.getMessaggio()));

            String type = StringUtils.lowerCase(messaggio.getType());
            if (LabelFdCUtil.checkIfNotNull(type)) {
              if (type.equalsIgnoreCase(TypeEnum.ERROR.value())) {
                type = "danger";
              }
            }
            String alert = "alert alert-".concat(type);
            AttributeAppender attributeModifier = new AttributeAppender("class", alert);
            testo.add(attributeModifier);
            testo.setEscapeModelStrings(false);
            item.addOrReplace(testo);
          }
        };
    addOrReplace(listView);
  }
}
