package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta.panel;

import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiIseeExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextFieldCurrency;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class IseeContributoTariPanel extends BasePanel {

  private static final long serialVersionUID = -5713812702516798234L;

  private int index;

  public IseeContributoTariPanel(String id, DatiDomandaContributoTari datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaContributoTari datiDomanda = (DatiDomandaContributoTari) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("IseeContributoTariPanel.titolo")));

    boolean domandaPresentabile = checkDomandaPresentabile(datiDomanda);

    WebMarkupContainer containerIseePresente = new WebMarkupContainer("containerIseePresente");
    containerIseePresente.setVisible(checkIseePresentato(datiDomanda) && domandaPresentabile);
    containerIseePresente.setOutputMarkupId(true);
    containerIseePresente.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerIseePresente);

    WebMarkupContainer containerNucleoIseeDiversoNucleoAnagrafico =
        new WebMarkupContainer("containerNucleoIseeDiversoNucleoAnagrafico");

    List<NucleoFamiliareComponenteNucleoInner> listaComponentiNucleoIsee =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();
    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoIsee())) {
      listaComponentiNucleoIsee = datiDomanda.getListaComponentiNucleoIsee();
    }

    ListView<NucleoFamiliareComponenteNucleoInner> listaNucleoIsee =
        new ListView<NucleoFamiliareComponenteNucleoInner>(
            "listaNucleoIsee", listaComponentiNucleoIsee) {

          @Override
          protected void populateItem(ListItem<NucleoFamiliareComponenteNucleoInner> itemIsee) {
            final NucleoFamiliareComponenteNucleoInner membroIsee = itemIsee.getModelObject();

            String nomeCognome = "";
            if (PageUtil.isStringValid(membroIsee.getNome())) {
              nomeCognome = membroIsee.getNome().concat(" ");
            }
            if (PageUtil.isStringValid(membroIsee.getCognome())) {
              nomeCognome = nomeCognome.concat(membroIsee.getCognome());
            }

            Label nominativoIsee = new Label("nominativoIsee", nomeCognome);
            itemIsee.addOrReplace(nominativoIsee);
          }
        };

    containerNucleoIseeDiversoNucleoAnagrafico.addOrReplace(listaNucleoIsee);

    List<Residente> listaComponentiNucleoAnagrafe = new ArrayList<Residente>();
    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoAnagrafe())) {
      listaComponentiNucleoAnagrafe = datiDomanda.getListaComponentiNucleoAnagrafe();
    }

    ListView<Residente> listaNucleoAnagrafe =
        new ListView<Residente>("listaNucleoAnagrafe", listaComponentiNucleoAnagrafe) {

          private static final long serialVersionUID = 2144060146741801108L;

          @Override
          protected void populateItem(ListItem<Residente> itemIsee) {
            final Residente membroAnagrafe = itemIsee.getModelObject();

            String nomeCognome = "";

            if (LabelFdCUtil.checkIfNotNull(membroAnagrafe.getRdfsLabel())) {
              nomeCognome = membroAnagrafe.getRdfsLabel();
            }

            Label nominativoAnagrafe = new Label("nominativoAnagrafe", nomeCognome);
            itemIsee.addOrReplace(nominativoAnagrafe);
          }
        };
    containerNucleoIseeDiversoNucleoAnagrafico.addOrReplace(listaNucleoAnagrafe);
    containerNucleoIseeDiversoNucleoAnagrafico.setOutputMarkupId(true);
    containerNucleoIseeDiversoNucleoAnagrafico.setOutputMarkupPlaceholderTag(true);
    containerNucleoIseeDiversoNucleoAnagrafico.setVisible(
        checkIseePresentato(datiDomanda) && !datiDomanda.isNucleiAnagrafeIseeUguali());
    addOrReplace(containerNucleoIseeDiversoNucleoAnagrafico);

    WebMarkupContainer domandaNonPresentabile = new WebMarkupContainer("domandaNonPresentabile");
    String messaggioInfo = "";
    if (!domandaPresentabile) {
      if (datiDomanda.isUnder70() && !datiDomanda.isRequisitiMinimiEtaENucleo()) {
        messaggioInfo = getString("IseeContributoTariPanel.isRequisitiMinimiEtaENucleoUnder70");
      } else if (!datiDomanda.isUnder70() && !datiDomanda.isRequisitiMinimiEtaENucleo()) {
        messaggioInfo = getString("IseeContributoTariPanel.isRequisitiMinimiEtaENucleoOver70");
      } else if (datiDomanda.isOver70ConAltriIs()) {
        messaggioInfo = getString("IseeContributoTariPanel.isOver70ConAltriIs");
      } else if (!checkIseePresentato(datiDomanda)) {
        messaggioInfo = getString("IseeContributoTariPanel.checkIseePresentato");
      } else if (checkIseePresentato(datiDomanda)) {
        messaggioInfo = getString("IseeContributoTariPanel.issePresenteMaFuoriRange");
      }
    }

    NotEmptyLabel messaggioDomandaNonPresentabile =
        new NotEmptyLabel(
            "messaggioDomandaNonPresentabile",
            new StringResourceModel("IseeContributoTariPanel.domandaNonPresentabile", this)
                .setParameters(messaggioInfo));
    messaggioDomandaNonPresentabile.setEscapeModelStrings(false);
    domandaNonPresentabile.addOrReplace(messaggioDomandaNonPresentabile);
    domandaNonPresentabile.setVisible(!domandaPresentabile);
    domandaNonPresentabile.setOutputMarkupId(true);
    domandaNonPresentabile.setOutputMarkupPlaceholderTag(true);
    addOrReplace(domandaNonPresentabile);

    ListView<DatiIseeExtend> containerDatiIsee =
        new ListView<DatiIseeExtend>("containerDatiIsee", datiDomanda.getDatiIseeCompleti()) {

          private static final long serialVersionUID = -3528564551443726335L;

          @Override
          protected void populateItem(ListItem<DatiIseeExtend> itemIsee) {
            final DatiIseeExtend iseeIS = itemIsee.getModelObject();

            FdCTextFieldCurrency importoIsee =
                new FdCTextFieldCurrency(
                    "importoIsee",
                    new PropertyModel(iseeIS, "importoDouble"),
                    IseeContributoTariPanel.this,
                    " €");
            importoIsee.setEnabled(false);
            importoIsee.setVisible(
                LabelFdCUtil.checkIfNotNull(iseeIS)
                    && LabelFdCUtil.checkIfNotNull(iseeIS.getImporto()));
            itemIsee.addOrReplace(importoIsee);

            FdCTextField nominativoIsee =
                new FdCTextField(
                    "nominativoIsee",
                    new PropertyModel(iseeIS, "nominativo"),
                    IseeContributoTariPanel.this);
            nominativoIsee.setEnabled(false);
            itemIsee.addOrReplace(nominativoIsee);

            FdCTextField protocolloDSU =
                new FdCTextField(
                    "protocolloDSU",
                    new PropertyModel(iseeIS, "protocolloDSU"),
                    IseeContributoTariPanel.this);
            protocolloDSU.setEnabled(false);
            protocolloDSU.setVisible(
                LabelFdCUtil.checkIfNotNull(iseeIS)
                    && LabelFdCUtil.checkIfNotNull(iseeIS.getProtocolloDSU()));
            itemIsee.addOrReplace(protocolloDSU);

            WebMarkupContainer iseeAssente = new WebMarkupContainer("iseeAssente");
            iseeAssente.setEnabled(false);
            iseeAssente.setVisible(
                LabelFdCUtil.checkIfNotNull(iseeIS)
                    && LabelFdCUtil.checkIfNull(iseeIS.getImporto()));
            itemIsee.addOrReplace(iseeAssente);
          }
        };

    addOrReplace(containerDatiIsee);

    FdCTextFieldCurrency importoIseeTotale =
        new FdCTextFieldCurrency(
            "importoIseeTotale",
            new PropertyModel(datiDomanda, "importoIsee"),
            IseeContributoTariPanel.this,
            " €");
    importoIseeTotale.setEnabled(false);
    importoIseeTotale.setVisible(LabelFdCUtil.checkIfNotNull(datiDomanda.getImportoIsee()));
    addOrReplace(importoIseeTotale);
  }

  private boolean checkDomandaPresentabile(DatiDomandaContributoTari datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda) && datiDomanda.isDomandaPresentabile();
  }

  private boolean checkIseePresentato(DatiDomandaContributoTari datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda) && datiDomanda.isIseePresentato();
  }
}
