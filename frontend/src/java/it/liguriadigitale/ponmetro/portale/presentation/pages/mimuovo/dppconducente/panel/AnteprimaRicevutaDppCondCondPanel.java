package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.ConfermaDppConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.RiassuntoDichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class AnteprimaRicevutaDppCondCondPanel extends BasePanel {

  private static final long serialVersionUID = 2300737658810670723L;

  public AnteprimaRicevutaDppCondCondPanel(RicercaVerbaleConducente ricercaVerbaleConducente) {
    super("anteprimaDppCondCondPanel");

    fillDati(ricercaVerbaleConducente);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    RicercaVerbaleConducente puntiPatente = (RicercaVerbaleConducente) dati;

    Label numeroAvviso = new Label("numeroAvviso", puntiPatente.getNumeroAvviso());
    numeroAvviso.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroAvviso()));
    addOrReplace(numeroAvviso);

    LocalDateLabel dataVerbale = new LocalDateLabel("dataVerbale", puntiPatente.getDataVerbale());
    dataVerbale.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getDataVerbale()));
    addOrReplace(dataVerbale);

    String nomeProprietario =
        puntiPatente.getCognomeConducente().concat(" ").concat(puntiPatente.getNomeConducente());
    Label proprietario = new Label("proprietario", nomeProprietario);
    proprietario.setVisible(PageUtil.isStringValid(nomeProprietario));
    addOrReplace(proprietario);

    Label cfProprietario = new Label("cfProprietario", puntiPatente.getCodiceFiscaleConducente());
    cfProprietario.setVisible(PageUtil.isStringValid(puntiPatente.getCodiceFiscaleConducente()));
    addOrReplace(cfProprietario);

    Label natoAProprietario =
        new Label("natoAProprietario", puntiPatente.getCittaNascitaConducente());
    natoAProprietario.setVisible(PageUtil.isStringValid(puntiPatente.getCittaNascitaConducente()));
    addOrReplace(natoAProprietario);

    Label natoProvinciaProprietario =
        new Label("natoProvinciaProprietario", puntiPatente.getProvinciaNascitaConducente());
    natoProvinciaProprietario.setVisible(
        PageUtil.isStringValid(puntiPatente.getProvinciaNascitaConducente()));
    addOrReplace(natoProvinciaProprietario);

    LocalDateLabel natoIlProprietario =
        new LocalDateLabel("natoIlProprietario", puntiPatente.getDataNascitaConducente());
    natoIlProprietario.setVisible(
        UtilVerbali.checkIfNotNull(puntiPatente.getDataNascitaConducente()));
    addOrReplace(natoIlProprietario);

    String indirizzo =
        puntiPatente.getCittaConducente().concat(" ").concat(puntiPatente.getIndirizzoConducente());
    Label proprietarioResidenteIn = new Label("proprietarioResidenteIn", indirizzo);
    proprietarioResidenteIn.setVisible(PageUtil.isStringValid(indirizzo));
    addOrReplace(proprietarioResidenteIn);

    Label proprietarioResidenteCap =
        new Label("proprietarioResidenteCap", puntiPatente.getCapConducente());
    proprietarioResidenteCap.setVisible(PageUtil.isStringValid(puntiPatente.getCapConducente()));
    addOrReplace(proprietarioResidenteCap);

    Label info3 = new Label("info3", puntiPatente.getNumeroAvviso());
    info3.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroAvviso()));
    addOrReplace(info3);

    LocalDateLabel del = new LocalDateLabel("del", puntiPatente.getDataVerbale());
    del.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getDataVerbale()));
    addOrReplace(del);

    Label categoriaPatente =
        new Label("categoriaPatente", puntiPatente.getCategoriaPatente().toUpperCase());
    categoriaPatente.setVisible(PageUtil.isStringValid(puntiPatente.getCategoriaPatente()));
    addOrReplace(categoriaPatente);

    Label numeroPatente = new Label("numeroPatente", puntiPatente.getNumeroPatente().toUpperCase());
    numeroPatente.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroPatente()));
    addOrReplace(numeroPatente);

    Label patenteRilasciataDa =
        new Label("patenteRilasciataDa", puntiPatente.getPatenteRilasciataDa().toUpperCase());
    patenteRilasciataDa.setVisible(PageUtil.isStringValid(puntiPatente.getPatenteRilasciataDa()));
    addOrReplace(patenteRilasciataDa);

    LocalDateLabel patenteRilasciataIl =
        new LocalDateLabel("patenteRilasciataIl", puntiPatente.getPatenteRilasciataIl());
    patenteRilasciataIl.setVisible(
        UtilVerbali.checkIfNotNull(puntiPatente.getPatenteRilasciataIl()));
    addOrReplace(patenteRilasciataIl);

    LocalDateLabel patenteValida =
        new LocalDateLabel("patenteValida", puntiPatente.getPatenteValidaFinoAl());
    patenteValida.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getPatenteValidaFinoAl()));
    addOrReplace(patenteValida);

    Label info9 = new Label("info9", puntiPatente.getNomeFilePatente());
    info9.setVisible(PageUtil.isStringValid(puntiPatente.getNomeFilePatente()));
    addOrReplace(info9);

    LaddaAjaxLink<Object> linkAnnulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 8581531259873382840L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(new DichiarazionePuntiPatenteConducentePage());
          }

          @Override
          public MarkupContainer setDefaultModel(IModel<?> model) {
            return setDefaultModel(model);
          }
        };
    linkAnnulla.setLabel(Model.of(getString("AnteprimaRicevutaDppCondCondPanel.annulla")));
    addOrReplace(linkAnnulla);

    LaddaAjaxLink<Object> linkIndietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -640477763875011771L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(new RiassuntoDichiarazionePuntiPatenteConducentePage(puntiPatente));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel<?> model) {
            return setDefaultModel(model);
          }
        };
    linkIndietro.setLabel(Model.of(getString("AnteprimaRicevutaDppCondCondPanel.indietro")));
    addOrReplace(linkIndietro);

    LaddaAjaxLink<Object> linkConfermaDPP =
        new LaddaAjaxLink<Object>("confermaDpp", Type.Primary) {

          private static final long serialVersionUID = 7645972068639729020L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(new ConfermaDppConducentePage(puntiPatente));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel<?> model) {
            return setDefaultModel(model);
          }
        };
    linkConfermaDPP.setLabel(Model.of(getString("AnteprimaRicevutaDppCondCondPanel.prosegui")));
    addOrReplace(linkConfermaDPP);
  }
}
