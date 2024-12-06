package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico.panel;

import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.DatiSegnalazioneTraffico;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class InfoTrafficoPanel extends BasePanel {

  private static final long serialVersionUID = -4930390410614798571L;

  public InfoTrafficoPanel(String id) {
    super(id);
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DatiSegnalazioneTraffico> lista = (List<DatiSegnalazioneTraffico>) dati;

    ListView<DatiSegnalazioneTraffico> listView =
        new ListView<DatiSegnalazioneTraffico>("listaSegnalazioni", lista) {

          private static final long serialVersionUID = -7511025079138842978L;

          @Override
          protected void populateItem(ListItem<DatiSegnalazioneTraffico> item) {

            DatiSegnalazioneTraffico segnalazione = item.getModelObject();

            LocalDateTime start = segnalazione.getStart();
            String localita =
                StringUtils.isNotEmpty(segnalazione.getLocalita())
                    ? segnalazione.getLocalita()
                    : "-";
            String stringaCodiceTipologia = segnalazione.getStringTipologia();
            String stringacodiceDettaglio = segnalazione.getStringDettaglio();
            String inizio = LocalDateTimeUtil.getStringByLocalDateTime(start, "dd-MM-yyyy HH:mm");

            AttributedString attributedString = new AttributedString(inizio);
            attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);

            Label lLocalitaData = new Label("localita", localita);
            Label lInizio = new Label("inizio", inizio);
            AttributeModifier italico =
                new AttributeModifier("style", "font-style:italic; font-size: smaller;");
            lInizio.add(italico);

            String tipoByKey = getString("SegnalazioniTrafficoTipoEnum." + stringaCodiceTipologia);
            if (StringUtils.isEmpty(tipoByKey)) {
              tipoByKey =
                  getString(
                      "SegnalazioniTrafficoTipoEnum." + segnalazione.getDefaultStringTipologia());
            }

            String dettaglioByKey =
                getString("SegnalazioniTrafficoDettaglioTipoEnum." + stringacodiceDettaglio);
            if (StringUtils.isEmpty(dettaglioByKey)) {
              dettaglioByKey =
                  getString(
                      "SegnalazioniTrafficoDettaglioTipoEnum."
                          + segnalazione.getDefaultStringDettaglio());
            }
            Label lDettaglio = new Label("dettaglio", dettaglioByKey);

            AjaxLink<Void> btnGeolocalizzato =
                new AjaxLink<Void>("btnGeolocalizzato") {

                  private static final long serialVersionUID = -5878700383884290343L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {}
                };
            String jsZoom =
                "map.setView([" + segnalazione.getLat() + "," + segnalazione.getLng() + "], 20);";
            btnGeolocalizzato.add(new AttributeAppender("onclick", jsZoom));
            btnGeolocalizzato.setVisible(
                segnalazione.getLat() != 0.0 && segnalazione.getLng() != 0.0);

            item.add(lInizio);
            item.add(lLocalitaData);
            item.add(lDettaglio);
            item.add(btnGeolocalizzato);
          }
        };

    WebMarkupContainer tabellaListaInfoTraffico =
        new WebMarkupContainer("tabellaListaInfoTraffico");
    tabellaListaInfoTraffico.setVisible(!lista.isEmpty());
    add(tabellaListaInfoTraffico);

    Label listaInfoTraffico =
        new Label("listaInfoTraffico", getString("InfoTrafficoPanel.listaVuota"));
    listaInfoTraffico.setVisible(lista.isEmpty());
    add(listaInfoTraffico);

    tabellaListaInfoTraffico.add(listView);
  }
}
