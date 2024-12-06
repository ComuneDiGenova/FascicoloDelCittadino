package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiPersoneACaricoContributoTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class DatiNucleoContributoTariPanel extends BasePanel {

  private static final long serialVersionUID = -3062735201891653233L;

  private int index;

  public DatiNucleoContributoTariPanel(
      String id, DatiDomandaContributoTari datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked", "serial"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaContributoTari datiDomanda = (DatiDomandaContributoTari) dati;

    addOrReplace(
        new FdCTitoloPanel(
            "titoloAutodichiarazioni",
            getString("DatiNucleoContributoTariPanel.titoloAutodichiarazioni")));

    SiNoDropDownChoice autodichiarazioneCategoriaImmobile =
        new SiNoDropDownChoice(
            "autodichiarazioneCategoriaImmobile",
            new PropertyModel<>(datiDomanda, "autodichiarazioneCategoriaImmobile"));
    autodichiarazioneCategoriaImmobile.setRequired(true);
    autodichiarazioneCategoriaImmobile.setLabel(
        Model.of(getString("DatiNucleoContributoTariPanel.autodichiarazioneCategoriaImmobile")));

    autodichiarazioneCategoriaImmobile.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -8216180860919651429L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // si = 0, no = 1

            if (LabelFdCUtil.checkIfNotNull(autodichiarazioneCategoriaImmobile)
                && LabelFdCUtil.checkIfNotNull(autodichiarazioneCategoriaImmobile.getValue())) {

              if (autodichiarazioneCategoriaImmobile.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setFlagCategoriaImmobileDiversaDaA1A8A9(1);
              } else {
                datiDomanda.setFlagCategoriaImmobileDiversaDaA1A8A9(0);
              }
            }

            target.add(autodichiarazioneCategoriaImmobile);
          }
        });

    addOrReplace(autodichiarazioneCategoriaImmobile);

    String mqMassimi = "";
    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getMqMassimi())) {
      mqMassimi = String.valueOf(datiDomanda.getMqMassimi());
    }

    StringResourceModel mqMassimiDinamico =
        new StringResourceModel(
                "DatiNucleoContributoTariPanel.autodichiarazioneMqImmobileInfo", this)
            .setParameters(mqMassimi);
    Label autodichiarazioneMqImmobileInfo =
        new Label("autodichiarazioneMqImmobileInfo", mqMassimiDinamico);
    addOrReplace(autodichiarazioneMqImmobileInfo);

    SiNoDropDownChoice autodichiarazioneMqImmobile =
        new SiNoDropDownChoice(
            "autodichiarazioneMqImmobile",
            new PropertyModel<>(datiDomanda, "autodichiarazioneMqImmobile"));
    autodichiarazioneMqImmobile.setRequired(true);
    autodichiarazioneMqImmobile.setLabel(Model.of(mqMassimiDinamico.getString()));

    autodichiarazioneMqImmobile.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // si = 0, no = 1

            if (LabelFdCUtil.checkIfNotNull(autodichiarazioneMqImmobile)
                && LabelFdCUtil.checkIfNotNull(autodichiarazioneMqImmobile.getValue())) {

              if (autodichiarazioneMqImmobile.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setFlagSuperficieImmobileEntroMq(1);
              } else {
                datiDomanda.setFlagSuperficieImmobileEntroMq(0);
              }
            }

            target.add(autodichiarazioneMqImmobile);
          }
        });

    addOrReplace(autodichiarazioneMqImmobile);

    SiNoDropDownChoice autodichiarazioneAltreAgevolazioni =
        new SiNoDropDownChoice(
            "autodichiarazioneAltreAgevolazioni",
            new PropertyModel<>(datiDomanda, "autodichiarazioneAltreAgevolazioni"));
    autodichiarazioneAltreAgevolazioni.setRequired(true);
    autodichiarazioneAltreAgevolazioni.setLabel(
        Model.of(getString("DatiNucleoContributoTariPanel.autodichiarazioneAltreAgevolazioni")));

    autodichiarazioneAltreAgevolazioni.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // si = 0, no = 1

            if (LabelFdCUtil.checkIfNotNull(autodichiarazioneAltreAgevolazioni)
                && LabelFdCUtil.checkIfNotNull(autodichiarazioneAltreAgevolazioni.getValue())) {

              if (autodichiarazioneAltreAgevolazioni.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setFlagBeneficarioNonFruitoreAltreAgevolazioni(1);
              } else {
                datiDomanda.setFlagBeneficarioNonFruitoreAltreAgevolazioni(0);
              }
            }

            target.add(autodichiarazioneAltreAgevolazioni);
          }
        });

    addOrReplace(autodichiarazioneAltreAgevolazioni);

    SiNoDropDownChoice autodichiarazioneInRegolaTari =
        new SiNoDropDownChoice(
            "autodichiarazioneInRegolaTari",
            new PropertyModel<>(datiDomanda, "autodichiarazioneInRegolaTari"));
    autodichiarazioneInRegolaTari.setRequired(true);
    autodichiarazioneInRegolaTari.setLabel(
        Model.of(getString("DatiNucleoContributoTariPanel.autodichiarazioneInRegolaTari")));

    autodichiarazioneInRegolaTari.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // si = 0, no = 1

            if (LabelFdCUtil.checkIfNotNull(autodichiarazioneInRegolaTari)
                && LabelFdCUtil.checkIfNotNull(autodichiarazioneInRegolaTari.getValue())) {

              if (autodichiarazioneInRegolaTari.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setFlagInRegolaTari(1);
              } else {
                datiDomanda.setFlagInRegolaTari(0);
              }
            }

            target.add(autodichiarazioneInRegolaTari);
          }
        });

    addOrReplace(autodichiarazioneInRegolaTari);

    addOrReplace(
        new FdCTitoloPanel(
                "titoloListaPersoneDaDichiarare",
                getString("DatiNucleoContributoTariPanel.titoloListaPersoneDaDichiarare"))
            .setVisible(
                LabelFdCUtil.checkIfNotNull(datiDomanda.getNumeroComponenti())
                    && datiDomanda.getNumeroComponenti() > 1));

    ListView<DatiPersoneACaricoContributoTariNetribe> listViewPersoneDaDichiarare =
        new ListView<DatiPersoneACaricoContributoTariNetribe>(
            "listaPersoneDaDichiarare", datiDomanda.getListaComponentiNucleoIseeACarico()) {

          private static final long serialVersionUID = 8999230944193351036L;

          @Override
          protected void populateItem(ListItem<DatiPersoneACaricoContributoTariNetribe> itemIsee) {
            final DatiPersoneACaricoContributoTariNetribe membroIsee = itemIsee.getModelObject();

            itemIsee.setOutputMarkupId(true);
            itemIsee.setOutputMarkupPlaceholderTag(true);

            String nomeCognome = "";
            if (PageUtil.isStringValid(membroIsee.getNome())) {
              nomeCognome = membroIsee.getNome().concat(" ");
            }
            if (PageUtil.isStringValid(membroIsee.getCognome())) {
              nomeCognome = nomeCognome.concat(membroIsee.getCognome());
            }

            Label nominativoIsee = new Label("nominativoIsee", nomeCognome);
            itemIsee.addOrReplace(nominativoIsee);

            SiNoDropDownChoice nominativoIseeACarico =
                new SiNoDropDownChoice(
                    "nominativoIseeACarico", new PropertyModel<>(membroIsee, "flagIsACarico"));
            nominativoIseeACarico.setLabel(Model.of("Nominativo"));

            nominativoIseeACarico.add(
                new AjaxFormComponentUpdatingBehavior("change") {

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    // si = 0, no = 1

                    log.debug("CP nominativoIseeACarico = " + nominativoIseeACarico.getValue());

                    if (LabelFdCUtil.checkIfNotNull(nominativoIseeACarico)
                        && LabelFdCUtil.checkIfNotNull(nominativoIseeACarico.getValue())) {

                      if (nominativoIseeACarico.getValue().equalsIgnoreCase("0")) {
                        membroIsee.setFlagIsACarico("1");
                      } else {
                        membroIsee.setFlagIsACarico("0");
                      }
                    }

                    log.debug("CP membroISee = " + membroIsee);
                  }
                });

            itemIsee.addOrReplace(nominativoIseeACarico);
          }
        };

    log.debug("CP lista a carico dopo = " + datiDomanda.getListaComponentiNucleoIseeACarico());

    listViewPersoneDaDichiarare.setOutputMarkupId(true);
    listViewPersoneDaDichiarare.setOutputMarkupPlaceholderTag(true);

    listViewPersoneDaDichiarare.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda.getNumeroComponenti())
            && datiDomanda.getNumeroComponenti() > 1);
    addOrReplace(listViewPersoneDaDichiarare);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
