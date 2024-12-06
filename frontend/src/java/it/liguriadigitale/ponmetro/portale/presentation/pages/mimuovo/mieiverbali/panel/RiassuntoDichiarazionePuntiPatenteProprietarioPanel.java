package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.AnteprimaRicevutaDppPropCondPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.AnteprimaRicevutaDppPropPropPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DichiarazionePuntiPatentePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiassuntoDichiarazionePuntiPatenteProprietarioPanel extends BasePanel {

  private static final long serialVersionUID = 6060017920121013420L;

  private DettaglioVerbale dettaglioVerbale;

  private Verbale verbale;

  public RiassuntoDichiarazionePuntiPatenteProprietarioPanel(
      PuntiPatenteProprietario patenteProprietario,
      DettaglioVerbale dettaglioVerbale,
      Verbale verbale) {
    super("dppProprietario");

    this.dettaglioVerbale = dettaglioVerbale;
    this.verbale = verbale;

    createFeedBackPanel();
    fillDati(patenteProprietario);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {
    PuntiPatenteProprietario puntiPatenteProprietario = (PuntiPatenteProprietario) dati;

    Label numeroAvviso = new Label("numeroAvviso", puntiPatenteProprietario.getNumeroAvviso());
    numeroAvviso.setVisible(PageUtil.isStringValid(puntiPatenteProprietario.getNumeroAvviso()));
    addOrReplace(numeroAvviso);

    Label numeroProtocollo =
        new Label("numeroProtocollo", puntiPatenteProprietario.getNumeroProtocollo());
    numeroProtocollo.setVisible(
        PageUtil.isStringValid(puntiPatenteProprietario.getNumeroProtocollo()));
    addOrReplace(numeroProtocollo);

    LocalDateLabel dataVerbale =
        new LocalDateLabel("dataVerbale", puntiPatenteProprietario.getDataVerbale());
    dataVerbale.setVisible(puntiPatenteProprietario.getDataVerbale() != null);
    addOrReplace(dataVerbale);

    Label targa = new Label("targa", puntiPatenteProprietario.getTarga());
    targa.setVisible(PageUtil.isStringValid(puntiPatenteProprietario.getTarga()));
    addOrReplace(targa);

    WebMarkupContainer containerDatiProprietario =
        new WebMarkupContainer("containerDatiProprietario");
    if (puntiPatenteProprietario.isDichiarazioneProprietario()) {
      Label nomeCognomeProprietario =
          new Label(
              "nomeCognomeProprietario",
              puntiPatenteProprietario
                  .getNomeProprietario()
                  .concat(" ")
                  .concat(puntiPatenteProprietario.getCognomeProprietario()));
      nomeCognomeProprietario.setVisible(
          PageUtil.isStringValid(
              puntiPatenteProprietario
                  .getNomeProprietario()
                  .concat(" ")
                  .concat(puntiPatenteProprietario.getCognomeProprietario())));
      containerDatiProprietario.addOrReplace(nomeCognomeProprietario);

      Label cfProprietario =
          new Label("cfProprietario", puntiPatenteProprietario.getCfProprietario());
      cfProprietario.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getCfProprietario()));
      containerDatiProprietario.addOrReplace(cfProprietario);

      Label proprietarioNatoA =
          new Label("proprietarioNatoA", puntiPatenteProprietario.getProprietarioNatoA());
      proprietarioNatoA.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getProprietarioNatoA()));
      containerDatiProprietario.addOrReplace(proprietarioNatoA);

      LocalDateLabel proprietarioNatoIl =
          new LocalDateLabel(
              "proprietarioNatoIl", puntiPatenteProprietario.getProprietarioNatoIl());
      proprietarioNatoIl.setVisible(puntiPatenteProprietario.getProprietarioNatoIl() != null);
      containerDatiProprietario.addOrReplace(proprietarioNatoIl);

      String indirizzoProprietario =
          puntiPatenteProprietario
              .getProprietarioResidenteIn()
              .concat(" ")
              .concat(
                  puntiPatenteProprietario
                      .getProprietarioResidenteCitta()
                      .concat(" ")
                      .concat(puntiPatenteProprietario.getProprietarioResidenteCap()));
      Label proprietarioResidenteIn = new Label("proprietarioResidenteIn", indirizzoProprietario);
      proprietarioResidenteIn.setVisible(PageUtil.isStringValid(indirizzoProprietario));
      containerDatiProprietario.addOrReplace(proprietarioResidenteIn);

      Label categoriaPatente =
          new Label("categoriaPatente", puntiPatenteProprietario.getCategoriaPatente());
      categoriaPatente.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getCategoriaPatente()));
      containerDatiProprietario.addOrReplace(categoriaPatente);

      Label numeroPatente = new Label("numeroPatente", puntiPatenteProprietario.getNumeroPatente());
      numeroPatente.setVisible(PageUtil.isStringValid(puntiPatenteProprietario.getNumeroPatente()));
      containerDatiProprietario.addOrReplace(numeroPatente);

      Label patenteRilasciataDa =
          new Label("patenteRilasciataDa", puntiPatenteProprietario.getPatenteRilasciataDa());
      patenteRilasciataDa.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getPatenteRilasciataDa()));
      containerDatiProprietario.addOrReplace(patenteRilasciataDa);

      LocalDateLabel patenteRilasciataIl =
          new LocalDateLabel(
              "patenteRilasciataIl", puntiPatenteProprietario.getPatenteRilasciataIl());
      patenteRilasciataIl.setVisible(puntiPatenteProprietario.getPatenteRilasciataIl() != null);
      containerDatiProprietario.addOrReplace(patenteRilasciataIl);

      LocalDateLabel patenteValidaFinoAl =
          new LocalDateLabel(
              "patenteValidaFinoAl", puntiPatenteProprietario.getPatenteValidaFinoAl());
      patenteValidaFinoAl.setVisible(puntiPatenteProprietario.getPatenteValidaFinoAl() != null);
      containerDatiProprietario.addOrReplace(patenteValidaFinoAl);

      Label emailProprietario =
          new Label("emailProprietario", puntiPatenteProprietario.getEmailProprietario());
      emailProprietario.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getEmailProprietario()));
      containerDatiProprietario.addOrReplace(emailProprietario);

      Label patenteProprietarioAllegata =
          new Label(
              "patenteProprietarioAllegata",
              puntiPatenteProprietario.getNomePatenteProprietarioUpload());
      patenteProprietarioAllegata.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getNomePatenteProprietarioUpload()));
      containerDatiProprietario.addOrReplace(patenteProprietarioAllegata);
    }
    containerDatiProprietario.setVisible(puntiPatenteProprietario.isDichiarazioneProprietario());
    addOrReplace(containerDatiProprietario);

    WebMarkupContainer containerDatiConducente = new WebMarkupContainer("containerDatiConducente");
    if (!puntiPatenteProprietario.isDichiarazioneProprietario()) {
      Label nomeCognomeConducente =
          new Label(
              "nomeCognomeConducente",
              puntiPatenteProprietario
                  .getNomeConducente()
                  .concat(" ")
                  .concat(puntiPatenteProprietario.getCognomeConducente()));
      nomeCognomeConducente.setVisible(
          PageUtil.isStringValid(
              puntiPatenteProprietario
                  .getNomeConducente()
                  .concat(" ")
                  .concat(puntiPatenteProprietario.getCognomeConducente())));
      containerDatiConducente.addOrReplace(nomeCognomeConducente);

      Label cfConducente = new Label("cfConducente", puntiPatenteProprietario.getCfConducente());
      cfConducente.setVisible(PageUtil.isStringValid(puntiPatenteProprietario.getCfConducente()));
      containerDatiConducente.addOrReplace(cfConducente);

      Label natoAConducente =
          new Label(
              "natoAConducente",
              puntiPatenteProprietario
                  .getConducenteNatoA()
                  .concat("(")
                  .concat(puntiPatenteProprietario.getConducenteNatoAProvincia())
                  .concat(")"));
      natoAConducente.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getConducenteNatoA())
              && PageUtil.isStringValid(puntiPatenteProprietario.getConducenteNatoAProvincia()));
      containerDatiConducente.addOrReplace(natoAConducente);

      Label natoIlConducente =
          new Label("natoIlConducente", puntiPatenteProprietario.getConducenteNatoIl());
      natoIlConducente.setVisible(puntiPatenteProprietario.getConducenteNatoIl() != null);
      containerDatiConducente.addOrReplace(natoIlConducente);

      Label residenteInConducente =
          new Label(
              "residenteInConducente",
              puntiPatenteProprietario
                  .getConducenteResidenteIn()
                  .concat(" ")
                  .concat(puntiPatenteProprietario.getConducenteResidenteNumCivico())
                  .concat(" ")
                  .concat(
                      puntiPatenteProprietario
                          .getConducenteResidenteCap()
                          .concat(" ")
                          .concat(puntiPatenteProprietario.getConducenteResidenteA())
                          .concat(" (")
                          .concat(
                              puntiPatenteProprietario
                                  .getConducenteResidenteProvincia()
                                  .concat(")"))));
      residenteInConducente.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getConducenteResidenteIn())
              && PageUtil.isStringValid(puntiPatenteProprietario.getConducenteResidenteNumCivico())
              && PageUtil.isStringValid(puntiPatenteProprietario.getConducenteResidenteCap())
              && PageUtil.isStringValid(puntiPatenteProprietario.getConducenteResidenteA())
              && PageUtil.isStringValid(
                  puntiPatenteProprietario.getConducenteResidenteProvincia()));
      containerDatiConducente.addOrReplace(residenteInConducente);

      Label emailConducente =
          new Label("emailConducente", puntiPatenteProprietario.getEmailConducente());
      emailConducente.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getEmailConducente()));
      containerDatiConducente.addOrReplace(emailConducente);

      Label categoriaPatenteConducente =
          new Label(
              "categoriaPatenteConducente",
              puntiPatenteProprietario.getCategoriaPatenteConducente());
      categoriaPatenteConducente.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getCategoriaPatenteConducente()));
      containerDatiConducente.addOrReplace(categoriaPatenteConducente);

      Label numeroPatenteConducente =
          new Label(
              "numeroPatenteConducente", puntiPatenteProprietario.getNumeroPatenteConducente());
      numeroPatenteConducente.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getNumeroPatenteConducente()));
      containerDatiConducente.addOrReplace(numeroPatenteConducente);

      Label patenteConducenteRilasciataDa =
          new Label(
              "patenteConducenteRilasciataDa",
              puntiPatenteProprietario.getPatenteConducenteRilasciataDa());
      patenteConducenteRilasciataDa.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getPatenteConducenteRilasciataDa()));
      containerDatiConducente.addOrReplace(patenteConducenteRilasciataDa);

      Label patenteConducenteRilasciataIl =
          new Label(
              "patenteConducenteRilasciataIl",
              puntiPatenteProprietario.getPatenteConducenteRilasciataIl());
      patenteConducenteRilasciataIl.setVisible(
          puntiPatenteProprietario.getPatenteConducenteRilasciataIl() != null);
      containerDatiConducente.addOrReplace(patenteConducenteRilasciataIl);

      Label patenteConducenteValidaFinoAl =
          new Label(
              "patenteConducenteValidaFinoAl",
              puntiPatenteProprietario.getPatenteConducenteValidaFinoAl());
      patenteConducenteValidaFinoAl.setVisible(
          puntiPatenteProprietario.getPatenteConducenteValidaFinoAl() != null);
      containerDatiConducente.addOrReplace(patenteConducenteValidaFinoAl);

      Label patenteConducenteAllegata =
          new Label(
              "patenteConducenteAllegata",
              puntiPatenteProprietario.getNomePatenteConducenteUpload());
      patenteConducenteAllegata.setVisible(
          PageUtil.isStringValid(puntiPatenteProprietario.getNomePatenteConducenteUpload()));
      containerDatiConducente.addOrReplace(patenteConducenteAllegata);
    }
    containerDatiConducente.setVisible(!puntiPatenteProprietario.isDichiarazioneProprietario());
    addOrReplace(containerDatiConducente);

    String dichiarazione = "";
    if (puntiPatenteProprietario.isDichiarazioneProprietario()) {
      dichiarazione =
          getString(
              "RiassuntoDichiarazionePuntiPatenteProprietarioPanel.toggleDichiarazioneProprietario");
    } else {
      dichiarazione =
          getString(
              "RiassuntoDichiarazionePuntiPatenteProprietarioPanel.toggleDichiarazioneConducente");
    }
    CheckBox checkBox = new CheckBox("toggleDichiarazione", Model.of(true));
    checkBox.setVisible(true);
    addOrReplace(checkBox);
    Label labelToggleDichiarazione = new Label("labelToggleDichiarazione", dichiarazione);
    addOrReplace(labelToggleDichiarazione);

    LaddaAjaxLink<Object> linkAnnulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 2803983952994148146L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());

            setResponsePage(new DichiarazionePuntiPatenteConducentePage());
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    linkAnnulla.setLabel(
        Model.of(getString("RiassuntoDichiarazionePuntiPatenteProprietarioPanel.annulla")));
    addOrReplace(linkAnnulla);

    LaddaAjaxLink<Object> linkIndietro =
        new LaddaAjaxLink<Object>("indietro", Type.Primary) {

          private static final long serialVersionUID = -9005554644614099119L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());

            setResponsePage(
                new DichiarazionePuntiPatentePage(
                    puntiPatenteProprietario, dettaglioVerbale, verbale));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    linkIndietro.setLabel(
        Model.of(getString("RiassuntoDichiarazionePuntiPatenteProprietarioPanel.indietro")));
    addOrReplace(linkIndietro);

    LaddaAjaxLink<Object> linkConfermaDPP =
        new LaddaAjaxLink<Object>("confermaDpp", Type.Primary) {

          private static final long serialVersionUID = 4606146074948048898L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());

            if (puntiPatenteProprietario.isDichiarazioneProprietario()) {
              setResponsePage(
                  new AnteprimaRicevutaDppPropPropPage(
                      puntiPatenteProprietario, dettaglioVerbale, verbale));
            } else {
              setResponsePage(
                  new AnteprimaRicevutaDppPropCondPage(
                      puntiPatenteProprietario, dettaglioVerbale, verbale));
            }
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    linkConfermaDPP.setLabel(
        Model.of(getString("RiassuntoDichiarazionePuntiPatenteProprietarioPanel.prosegui")));
    addOrReplace(linkConfermaDPP);
  }
}
