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

public class AnteprimaRicevutaDppPropCondPanel extends BasePanel {

  private static final long serialVersionUID = -3494436770577896780L;

  private DettaglioVerbale dettaglioVerbale;

  private Verbale verbale;

  public AnteprimaRicevutaDppPropCondPanel(
      PuntiPatenteProprietario puntiPatenteProprietario,
      DettaglioVerbale dettaglioVerbale,
      Verbale verbale) {
    super("anteprimaDppPropCondPanel");

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

    Label proprietario =
        new Label(
            "proprietario",
            puntiPatente
                .getCognomeProprietario()
                .concat(" ")
                .concat(puntiPatente.getNomeProprietario()));
    proprietario.setVisible(
        PageUtil.isStringValid(puntiPatente.getCognomeProprietario())
            && PageUtil.isStringValid(puntiPatente.getNomeProprietario()));
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

    String indirizzoProprietario =
        puntiPatente
            .getProprietarioResidenteCitta()
            .concat(" ")
            .concat(puntiPatente.getProprietarioResidenteIn());
    Label residenteInProprietario = new Label("residenteInProprietario", indirizzoProprietario);
    residenteInProprietario.setVisible(PageUtil.isStringValid(indirizzoProprietario));
    addOrReplace(residenteInProprietario);

    Label capProprietario =
        new Label("capProprietario", puntiPatente.getProprietarioResidenteCap());
    capProprietario.setVisible(PageUtil.isStringValid(puntiPatente.getProprietarioResidenteCap()));
    addOrReplace(capProprietario);

    Label info3 = new Label("info3", puntiPatente.getNumeroProtocollo());
    info3.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroProtocollo()));
    addOrReplace(info3);

    LocalDateLabel del = new LocalDateLabel("del", puntiPatente.getDataVerbale());
    del.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getDataVerbale()));
    addOrReplace(del);

    String conducenteNome =
        puntiPatente.getCognomeConducente().concat(" ").concat(puntiPatente.getNomeConducente());
    Label conducente = new Label("conducente", conducenteNome);
    conducente.setVisible(PageUtil.isStringValid(conducenteNome));
    addOrReplace(conducente);

    Label natoAconducente = new Label("natoAconducente", puntiPatente.getConducenteNatoA());
    natoAconducente.setVisible(PageUtil.isStringValid(puntiPatente.getConducenteNatoA()));
    addOrReplace(natoAconducente);

    Label natoProvinciaConducente =
        new Label("natoProvinciaConducente", puntiPatente.getConducenteNatoAProvincia());
    natoProvinciaConducente.setVisible(
        PageUtil.isStringValid(puntiPatente.getConducenteNatoAProvincia()));
    addOrReplace(natoProvinciaConducente);

    LocalDateLabel natoIlConducente =
        new LocalDateLabel("natoIlConducente", puntiPatente.getConducenteNatoIl());
    natoIlConducente.setVisible(UtilVerbali.checkIfNotNull(puntiPatente.getConducenteNatoIl()));
    addOrReplace(natoIlConducente);

    String indirizzoConducente =
        puntiPatente
            .getConducenteResidenteA()
            .concat(" ")
            .concat(
                puntiPatente
                    .getConducenteResidenteIn()
                    .concat(" ")
                    .concat(puntiPatente.getConducenteResidenteNumCivico()));
    Label residenteInConducente = new Label("residenteInConducente", indirizzoConducente);
    residenteInConducente.setVisible(PageUtil.isStringValid(indirizzoConducente));
    addOrReplace(residenteInConducente);

    Label capConducente = new Label("capConducente", puntiPatente.getConducenteResidenteCap());
    capConducente.setVisible(PageUtil.isStringValid(puntiPatente.getConducenteResidenteCap()));
    addOrReplace(capConducente);

    Label categoriaPatente =
        new Label("categoriaPatente", puntiPatente.getCategoriaPatenteConducente().toUpperCase());
    categoriaPatente.setVisible(
        PageUtil.isStringValid(puntiPatente.getCategoriaPatenteConducente()));
    addOrReplace(categoriaPatente);

    Label numeroPatente =
        new Label("numeroPatente", puntiPatente.getNumeroPatenteConducente().toUpperCase());
    numeroPatente.setVisible(PageUtil.isStringValid(puntiPatente.getNumeroPatenteConducente()));
    addOrReplace(numeroPatente);

    Label patenteRilasciataDa =
        new Label(
            "patenteRilasciataDa", puntiPatente.getPatenteConducenteRilasciataDa().toUpperCase());
    patenteRilasciataDa.setVisible(
        PageUtil.isStringValid(puntiPatente.getPatenteConducenteRilasciataDa()));
    addOrReplace(patenteRilasciataDa);

    Label patenteRilasciataIl =
        new Label("patenteRilasciataIl", puntiPatente.getPatenteConducenteRilasciataIl());
    patenteRilasciataIl.setVisible(
        UtilVerbali.checkIfNotNull(puntiPatente.getPatenteConducenteRilasciataIl()));
    addOrReplace(patenteRilasciataIl);

    Label patenteValida =
        new Label("patenteValida", puntiPatente.getPatenteConducenteValidaFinoAl());
    patenteValida.setVisible(
        UtilVerbali.checkIfNotNull(puntiPatente.getPatenteConducenteValidaFinoAl()));
    addOrReplace(patenteValida);

    Label nomeFilePatente =
        new Label("nomeFilePatente", puntiPatente.getNomePatenteConducenteUpload());
    nomeFilePatente.setVisible(
        PageUtil.isStringValid(puntiPatente.getNomePatenteConducenteUpload()));
    addOrReplace(nomeFilePatente);

    LaddaAjaxLink<Object> linkAnnulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -1245812594747819216L;

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
    linkAnnulla.setLabel(Model.of(getString("AnteprimaRicevutaDppPropCondPanel.annulla")));
    addOrReplace(linkAnnulla);

    LaddaAjaxLink<Object> linkIndietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -3102303832159328433L;

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
    linkIndietro.setLabel(Model.of(getString("AnteprimaRicevutaDppPropCondPanel.indietro")));
    addOrReplace(linkIndietro);

    LaddaAjaxLink<Object> linkConfermaDPP =
        new LaddaAjaxLink<Object>("confermaDpp", Type.Primary) {

          private static final long serialVersionUID = -6943283910919512513L;

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
    linkConfermaDPP.setLabel(Model.of(getString("AnteprimaRicevutaDppPropCondPanel.prosegui")));
    addOrReplace(linkConfermaDPP);
  }
}
