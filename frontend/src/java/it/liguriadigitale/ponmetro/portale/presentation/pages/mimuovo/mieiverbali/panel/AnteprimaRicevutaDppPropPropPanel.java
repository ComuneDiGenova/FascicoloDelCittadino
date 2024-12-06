package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.ConfermaDppProprietarioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.RiassuntoDichiarazionePuntiPatenteProprietarioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class AnteprimaRicevutaDppPropPropPanel extends BasePanel {

  private static final long serialVersionUID = 8834393465299259738L;

  private DettaglioVerbale dettaglioVerbale;

  private Verbale verbale;

  public AnteprimaRicevutaDppPropPropPanel(
      PuntiPatenteProprietario puntiPatenteProprietario,
      DettaglioVerbale dettaglioVerbale,
      Verbale verbale) {
    super("anteprimaDppPropPropPanel");

    this.dettaglioVerbale = dettaglioVerbale;
    this.verbale = verbale;

    fillDati(puntiPatenteProprietario);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    PuntiPatenteProprietario puntiPatente = (PuntiPatenteProprietario) dati;

    Label numeroProtocollo = new Label("numeroProtocollo", puntiPatente.getNumeroProtocollo());
    numeroProtocollo.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroProtocollo()));
    addOrReplace(numeroProtocollo);

    LocalDateLabel dataVerbale = new LocalDateLabel("dataVerbale", puntiPatente.getDataVerbale());
    dataVerbale.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getDataVerbale()));
    addOrReplace(dataVerbale);

    String nomeProprietario =
        puntiPatente
            .getCognomeProprietario()
            .concat(" ")
            .concat(puntiPatente.getNomeProprietario());
    Label proprietario = new Label("proprietario", nomeProprietario);
    proprietario.setVisible(PageUtil.isStringValid(nomeProprietario));
    addOrReplace(proprietario);

    Label cfProprietario = new Label("cfProprietario", puntiPatente.getCfProprietario());
    cfProprietario.setVisible(PageUtil.isStringValid(puntiPatente.getCfProprietario()));
    addOrReplace(cfProprietario);

    Label natoAProprietario = new Label("natoAProprietario", puntiPatente.getProprietarioNatoA());
    natoAProprietario.setVisible(PageUtil.isStringValid(puntiPatente.getProprietarioNatoA()));
    addOrReplace(natoAProprietario);

    Label natoProvinciaProprietario =
        new Label("natoProvinciaProprietario", puntiPatente.getProprietarioNatoAProvincia());
    natoProvinciaProprietario.setVisible(
        PageUtil.isStringValid(puntiPatente.getProprietarioNatoAProvincia()));
    addOrReplace(natoProvinciaProprietario);

    LocalDateLabel natoIlProprietario =
        new LocalDateLabel("natoIlProprietario", puntiPatente.getProprietarioNatoIl());
    natoIlProprietario.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getProprietarioNatoIl()));
    addOrReplace(natoIlProprietario);

    String indirizzo =
        puntiPatente
            .getProprietarioResidenteCitta()
            .concat(" ")
            .concat(puntiPatente.getProprietarioResidenteIn());
    Label proprietarioResidenteIn = new Label("proprietarioResidenteIn", indirizzo);
    proprietarioResidenteIn.setVisible(PageUtil.isStringValid(indirizzo));
    addOrReplace(proprietarioResidenteIn);

    Label proprietarioResidenteCap =
        new Label("proprietarioResidenteCap", puntiPatente.getProprietarioResidenteCap());
    proprietarioResidenteCap.setVisible(
        PageUtil.isStringValid(puntiPatente.getProprietarioResidenteCap()));
    addOrReplace(proprietarioResidenteCap);

    Label info3 = new Label("info3", puntiPatente.getNumeroProtocollo());
    info3.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroProtocollo()));
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

    Label info9 = new Label("info9", puntiPatente.getNomePatenteProprietarioUpload());
    info9.setVisible(PageUtil.isStringValid(puntiPatente.getNomePatenteProprietarioUpload()));
    addOrReplace(info9);

    LaddaAjaxLink<Object> linkAnnulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -8685044958441076299L;

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
    linkAnnulla.setLabel(Model.of(getString("AnteprimaRicevutaDppPropPropPanel.annulla")));
    addOrReplace(linkAnnulla);

    LaddaAjaxLink<Object> linkIndietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -5336627847529803030L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(
                new RiassuntoDichiarazionePuntiPatenteProprietarioPage(
                    puntiPatente, dettaglioVerbale, verbale));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel<?> model) {
            return setDefaultModel(model);
          }
        };
    linkIndietro.setLabel(Model.of(getString("AnteprimaRicevutaDppPropPropPanel.indietro")));
    addOrReplace(linkIndietro);

    LaddaAjaxLink<Object> linkConfermaDPP =
        new LaddaAjaxLink<Object>("confermaDpp", Type.Primary) {

          private static final long serialVersionUID = 7645972068639729020L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(new ConfermaDppProprietarioPage(puntiPatente, verbale));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel<?> model) {
            return setDefaultModel(model);
          }
        };
    linkConfermaDPP.setLabel(Model.of(getString("AnteprimaRicevutaDppPropPropPanel.prosegui")));
    addOrReplace(linkConfermaDPP);
  }
}
