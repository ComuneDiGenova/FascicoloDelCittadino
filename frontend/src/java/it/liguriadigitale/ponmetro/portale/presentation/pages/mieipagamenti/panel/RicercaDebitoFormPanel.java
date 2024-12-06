package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.pojo.mipGlobali.TipologieSelezionate;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.form.RicercaDebitoForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliPage;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class RicercaDebitoFormPanel extends BasePanel {

  private static final long serialVersionUID = -4340631250434188638L;

  private TipologiaEntrata tipologiaEntrata;

  public RicercaDebitoFormPanel(
      String id, TipologiaEntrata tipologiaEntrata, List<TipologiaEntrata> listaTipologia) {
    super(id);
    setOutputMarkupId(true);

    this.tipologiaEntrata = tipologiaEntrata;

    fillDati(listaTipologia);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<TipologiaEntrata> listaTipologia = (List<TipologiaEntrata>) dati;

    TipologieSelezionate selezioneAttuale = impostaDefaultCombo();

    RicercaDebitoForm ricercaDebitoForm =
        new RicercaDebitoForm("form", selezioneAttuale, listaTipologia, getUtente());

    ricercaDebitoForm.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);
    add(ricercaDebitoForm);

    WebMarkupContainer containerMessaggiInfo = new WebMarkupContainer("containerMessaggiInfo");
    containerMessaggiInfo.add(creaLinkVerbali());
    add(containerMessaggiInfo);

    createFeedBackPanel();
  }

  private TipologieSelezionate impostaDefaultCombo() {
    TipologieSelezionate selezioneAttuale = new TipologieSelezionate(tipologiaEntrata);
    return selezioneAttuale;
  }

  private LaddaAjaxLink<Object> creaLinkVerbali() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("mieiVerbali", Type.Link) {

          private static final long serialVersionUID = -2038738257669579490L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RicercaDebitoFormPanel.this);
            setResponsePage(new MieiVerbaliPage());
          }
        };

    IconType iconType =
        new IconType("mieiVerbali") {

          private static final long serialVersionUID = 2481225803231283571L;

          @Override
          public String cssClassName() {
            return "icon-vigile-verbale color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(Model.of(getString("RicercaDebitoFormPanel.verbali")));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
