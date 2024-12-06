package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.panel;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.pojo.CzrmMailCommenti;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.StringResourceModel;

public class HelpCzRMDettagliDomandePanel extends BasePanel {

  private static final long serialVersionUID = -1199146446645343227L;

  private String caseNumber;

  public HelpCzRMDettagliDomandePanel(
      String id, List<CzrmMailCommenti> listaMailCommenti, String caseNumber) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    this.caseNumber = caseNumber;

    fillDati(listaMailCommenti);
  }

  @SuppressWarnings({"unchecked", "serial"})
  @Override
  public void fillDati(Object dati) {
    List<CzrmMailCommenti> listaMailCommento = (List<CzrmMailCommenti>) dati;

    NotEmptyLabel titolo =
        new NotEmptyLabel(
            "titolo",
            new StringResourceModel("HelpCzRMDettagliDomandePanel.titolo", this)
                .setParameters(caseNumber));
    addOrReplace(titolo);

    ListView<CzrmMailCommenti> listView =
        new ListView<CzrmMailCommenti>("box", listaMailCommento) {

          @Override
          protected void populateItem(ListItem<CzrmMailCommenti> itemMail) {
            final CzrmMailCommenti mailCommento = itemMail.getModelObject();

            log.debug("CP mail commento = " + mailCommento);

            String dataMessaggio = "";
            if (LabelFdCUtil.checkIfNotNull(mailCommento.getData())) {
              dataMessaggio = LocalDateUtil.getStringOffsetDateTime(mailCommento.getData());
            }

            Label messageDate = new Label("messageDate", dataMessaggio);
            messageDate.setVisible(PageUtil.isStringValid(dataMessaggio));
            itemMail.addOrReplace(messageDate);

            Label category = new Label("category", mailCommento.getTipologia());
            itemMail.addOrReplace(category);

            Label subject = new Label("subject", mailCommento.getOggetto());
            subject.setVisible(PageUtil.isStringValid(mailCommento.getOggetto()));
            itemMail.addOrReplace(subject);

            Label htmlBody = new Label("htmlBody", mailCommento.getBody());
            htmlBody.setVisible(PageUtil.isStringValid(mailCommento.getBody()));
            htmlBody.setOutputMarkupId(true);
            htmlBody.setOutputMarkupPlaceholderTag(true);
            htmlBody.setEscapeModelStrings(false);
            itemMail.addOrReplace(htmlBody);
          }
        };

    addOrReplace(listView);
  }
}
