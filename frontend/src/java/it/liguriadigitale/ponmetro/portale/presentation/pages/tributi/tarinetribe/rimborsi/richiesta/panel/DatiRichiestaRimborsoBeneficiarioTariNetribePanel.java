package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCModalitaPagamentoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoPressoTesoreriaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoSuContoCorrentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.ModalitaPagamentoTariNetribeRimborsiRender;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiRichiestaRimborsoBeneficiarioTariNetribePanel extends BasePanel {

  private static final long serialVersionUID = 5727219418582358084L;

  private int index;

  @SuppressWarnings("rawtypes")
  private FdCPagamentoPressoTesoreriaPanel ritiroPressoTesoreriaPanel;

  @SuppressWarnings("rawtypes")
  private FdCPagamentoSuContoCorrentePanel accreditoSuContoCorrentePanel;

  private FileUploadField allegatiRimborsoUpload;

  private String nomeAllegatoUpload;

  private byte[] byteAllegatoUpload;

  private Form<?> formUploadAllegati;

  private WebMarkupContainer containerAllegatiUpload;

  private List<FileAllegato> listaFileAllegato = new ArrayList<FileAllegato>();

  private WebMarkupContainer containerUploadAllegati =
      new WebMarkupContainer("containerUploadAllegati");

  private WebMarkupContainer containerErede = new WebMarkupContainer("containerErede");

  private WebMarkupContainer containerIntestario = new WebMarkupContainer("containerIntestario");

  private DatiRichiestaRimborsoTariNetribe datiRimborso;

  public DatiRichiestaRimborsoBeneficiarioTariNetribePanel(
      String id, DatiRichiestaRimborsoTariNetribe datiRimborso, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(datiRimborso);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {

    datiRimborso = (DatiRichiestaRimborsoTariNetribe) dati;

    FdCTextField nomeRichiedente =
        new FdCTextField(
            "nomeRichiedente",
            new PropertyModel(datiRimborso, "nomeRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    nomeRichiedente.setRequired(true);
    nomeRichiedente.setEnabled(false);
    addOrReplace(nomeRichiedente);

    FdCTextField cognomeRichiedente =
        new FdCTextField(
            "cognomeRichiedente",
            new PropertyModel(datiRimborso, "cognomeRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    cognomeRichiedente.setRequired(true);
    cognomeRichiedente.setEnabled(false);
    addOrReplace(cognomeRichiedente);

    FdCTextField codiceFiscaleRichiedente =
        new FdCTextField(
            "codiceFiscaleRichiedente",
            new PropertyModel(datiRimborso, "codiceFiscaleRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    codiceFiscaleRichiedente.setRequired(true);
    codiceFiscaleRichiedente.setEnabled(false);
    addOrReplace(codiceFiscaleRichiedente);

    FdCTextField emailRichiedente =
        new FdCTextField(
            "emailRichiedente",
            new PropertyModel(datiRimborso, "emailRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    emailRichiedente.setRequired(true);
    addOrReplace(emailRichiedente);

    WebMarkupContainer containerDatiIndirizzoErede =
        new WebMarkupContainer("containerDatiIndirizzoErede");
    containerDatiIndirizzoErede.setOutputMarkupId(true);
    containerDatiIndirizzoErede.setOutputMarkupPlaceholderTag(true);

    FdCTextField indirizzoRichiedente =
        new FdCTextField(
            "indirizzoRichiedente",
            new PropertyModel(datiRimborso, "indirizzoRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    indirizzoRichiedente.setRequired(true);
    indirizzoRichiedente.setEnabled(!datiRimborso.isIntestatario());
    containerDatiIndirizzoErede.addOrReplace(indirizzoRichiedente);

    FdCTextField comuneRichiedente =
        new FdCTextField(
            "comuneRichiedente",
            new PropertyModel(datiRimborso, "comuneRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    comuneRichiedente.setRequired(true);
    comuneRichiedente.setEnabled(!datiRimborso.isIntestatario());
    containerDatiIndirizzoErede.addOrReplace(comuneRichiedente);

    FdCTextField capRichiedente =
        new FdCTextField(
            "capRichiedente",
            new PropertyModel(datiRimborso, "capRichiedente"),
            DatiRichiestaRimborsoBeneficiarioTariNetribePanel.this);
    capRichiedente.setRequired(true);
    capRichiedente.setEnabled(!datiRimborso.isIntestatario());
    containerDatiIndirizzoErede.addOrReplace(capRichiedente);

    containerDatiIndirizzoErede.setVisible(!datiRimborso.isIntestatario());
    addOrReplace(containerDatiIndirizzoErede);

    containerErede.setOutputMarkupId(true);
    containerErede.setOutputMarkupPlaceholderTag(true);
    containerErede.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedenteRimborso())
            && !datiRimborso
                .getTipologiaRichiedenteRimborso()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));

    containerIntestario.setOutputMarkupId(true);
    containerIntestario.setOutputMarkupPlaceholderTag(true);
    containerIntestario.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedenteRimborso())
            && datiRimborso
                .getTipologiaRichiedenteRimborso()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));

    containerAllegatiUpload = new WebMarkupContainer("containerAllegatiUpload");
    containerAllegatiUpload.setVisible(false);
    containerAllegatiUpload.setOutputMarkupId(true);

    formUploadAllegati =
        new Form<Void>("formUploadAllegati") {

          private static final long serialVersionUID = 2465605365240858915L;

          @Override
          protected void onSubmit() {}
        };
    formUploadAllegati.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = -2608959384734654676L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {

            final FileUpload uploadedFile = allegatiRimborsoUpload.getFileUpload();
            if (uploadedFile != null) {

              setAllegatiRimborsoUpload(allegatiRimborsoUpload);

              containerAllegatiUpload.setVisible(true);

              FileAllegato fileAllegato = new FileAllegato();
              fileAllegato.setFile(uploadedFile.getBytes());
              fileAllegato.setNomeFile(uploadedFile.getClientFileName());
              try {
                fileAllegato.setMimeType(
                    FileFdCUtil.getMimeTypeFileUploadAllegato(uploadedFile.getBytes()));
                fileAllegato.setEstensioneFile(
                    FileFdCUtil.getEstensionFileUploadAllegato(uploadedFile.getBytes()));
              } catch (BusinessException | MagicMatchNotFoundException e) {
                log.error("Errore durante mimetype allegato tari " + e.getMessage());
              }
              listaFileAllegato.add(fileAllegato);
              setListaFileAllegato(listaFileAllegato);

              target.add(formUploadAllegati, containerAllegatiUpload);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            log.error("Errore upload file tari ");

            super.onError(target);

            target.add(getPage());
          }
        });

    formUploadAllegati.setMultiPart(true);

    formUploadAllegati.setOutputMarkupId(true);
    formUploadAllegati.setOutputMarkupPlaceholderTag(true);

    formUploadAllegati.addOrReplace(containerAllegatiUpload);

    formUploadAllegati.addOrReplace(containerErede);
    formUploadAllegati.addOrReplace(containerIntestario);

    allegatiRimborsoUpload = new FileUploadField("allegatiRimborsoUpload");
    allegatiRimborsoUpload.setOutputMarkupId(true);
    allegatiRimborsoUpload.setOutputMarkupPlaceholderTag(true);
    allegatiRimborsoUpload.setLabel(Model.of("Upload documenti"));
    allegatiRimborsoUpload.setVisible(isEnabled());
    allegatiRimborsoUpload.add(new FileUploadValidator());

    formUploadAllegati.addOrReplace(allegatiRimborsoUpload);

    containerUploadAllegati.setOutputMarkupId(true);
    containerUploadAllegati.setOutputMarkupPlaceholderTag(true);
    containerUploadAllegati.setVisible(
        !datiRimborso.isIntestatario()
            || (datiRimborso.isIntestatario()
                && PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())
                && !datiRimborso
                    .getCodiceFiscaleDelegato()
                    .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())));
    addOrReplace(containerUploadAllegati);

    containerUploadAllegati.addOrReplace(formUploadAllegati);

    ListView<FileAllegato> listaAllegati =
        new ListView<FileAllegato>("listaAllegati", listaFileAllegato) {

          private static final long serialVersionUID = -1467478069703633408L;

          @Override
          protected void populateItem(ListItem<FileAllegato> itemAllegato) {

            FileAllegato allegato = itemAllegato.getModelObject();

            itemAllegato.setOutputMarkupId(true);

            Label nomeFileAllegato = new Label("nomeFileAllegato", allegato.getNomeFile());
            itemAllegato.addOrReplace(nomeFileAllegato);

            Label dimensioneFileAllegato =
                new Label(
                    "dimensioneFileAllegato", FileFdCUtil.getSizeFile(allegato.getFile().length));
            itemAllegato.addOrReplace(dimensioneFileAllegato);

            AjaxButton eliminaAllegato =
                new AjaxButton("eliminaAllegato") {

                  private static final long serialVersionUID = 4047736735998515022L;

                  @Override
                  protected void onSubmit(AjaxRequestTarget targetElimina) {

                    listaFileAllegato.remove(allegato);

                    targetElimina.add(containerAllegatiUpload);
                  }
                };
            eliminaAllegato.setDefaultFormProcessing(false);
            itemAllegato.addOrReplace(eliminaAllegato);
          }
        };

    datiRimborso.setListaAllegati(listaFileAllegato);

    containerAllegatiUpload.addOrReplace(listaAllegati);
    containerAllegatiUpload.setVisible(
        LabelFdCUtil.checkIfNotNull(listaFileAllegato)
            && !LabelFdCUtil.checkEmptyList(listaFileAllegato)
            && listaFileAllegato.size() > 0);

    containerAllegatiUpload.setEnabled(isEnabled());

    formUploadAllegati.addOrReplace(containerAllegatiUpload);

    if (LabelFdCUtil.checkIfNull(listaFileAllegato)) {
      listaFileAllegato = new ArrayList<FileAllegato>();
    }

    accreditoSuContoCorrentePanel =
        new FdCPagamentoSuContoCorrentePanel<>(
            "accreditoSuContoCorrentePanel", datiRimborso, false, true);
    accreditoSuContoCorrentePanel.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getModalitaDiPagamento())
            && PageUtil.isStringValid(datiRimborso.getModalitaDiPagamento().value())
            && datiRimborso.getModalitaDiPagamento().value().equalsIgnoreCase("CAB"));

    addOrReplace(accreditoSuContoCorrentePanel);

    ritiroPressoTesoreriaPanel =
        new FdCPagamentoPressoTesoreriaPanel<>("ritiroPressoTesoreriaPanel", datiRimborso, false);
    ritiroPressoTesoreriaPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getModalitaDiPagamento())
            && PageUtil.isStringValid(datiRimborso.getModalitaDiPagamento().value())
            && datiRimborso.getModalitaDiPagamento().value().equalsIgnoreCase("CAS"));
    addOrReplace(ritiroPressoTesoreriaPanel);

    List<ModalitaPagamentoEnum> listaModalitaPagamento =
        ServiceLocator.getInstance().getServiziTariRimborsiNetribe().getListaModalitaPagamento();

    FdCModalitaPagamentoDropDownChoice modalitaDiPagamento =
        new FdCModalitaPagamentoDropDownChoice(
            "modalitaDiPagamento",
            new PropertyModel(datiRimborso, "modalitaDiPagamento"),
            new ModalitaPagamentoTariNetribeRimborsiRender(),
            listaModalitaPagamento);

    modalitaDiPagamento.setRequired(true);
    modalitaDiPagamento.setLabel(Model.of("Modalità di pagamento"));
    addOrReplace(modalitaDiPagamento);

    modalitaDiPagamento.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 7948549185815378484L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(modalitaDiPagamento)
                && LabelFdCUtil.checkIfNotNull(modalitaDiPagamento.getValue())) {

              boolean checkModalitaPagamentoContoCorrente =
                  modalitaDiPagamento.getValue().equalsIgnoreCase("CAB");

              if (checkModalitaPagamentoContoCorrente) {

                datiRimborso.setNomeDelegato(null);
                datiRimborso.setCognomeDelegato(null);
                datiRimborso.setCodiceFiscaleDelegato(null);

                listaFileAllegato.removeAll(listaFileAllegato);

                ritiroPressoTesoreriaPanel.getNomeDelegato().getTextField().setRequired(false);
                ritiroPressoTesoreriaPanel.getCognomeDelegato().getTextField().setRequired(false);
                ritiroPressoTesoreriaPanel
                    .getCodiceFiscaleDelegato()
                    .getTextField()
                    .setRequired(false);

                ritiroPressoTesoreriaPanel =
                    new FdCPagamentoPressoTesoreriaPanel<>(
                        "ritiroPressoTesoreriaPanel", datiRimborso, false);
                addOrReplace(ritiroPressoTesoreriaPanel);

              } else {

                datiRimborso.setIban(null);
                datiRimborso.setSwift(null);
                datiRimborso.setNomeDelegato(null);
                datiRimborso.setCognomeDelegato(null);
                datiRimborso.setCodiceFiscaleDelegato(null);

                listaFileAllegato.removeAll(listaFileAllegato);

                accreditoSuContoCorrentePanel =
                    new FdCPagamentoSuContoCorrentePanel<>(
                        "accreditoSuContoCorrentePanel", datiRimborso, false, true);
                addOrReplace(accreditoSuContoCorrentePanel);
              }

              ritiroPressoTesoreriaPanel
                  .getCodiceFiscaleDelegato()
                  .getTextField()
                  .add(
                      new AjaxFormComponentUpdatingBehavior("change") {

                        @Override
                        protected void onError(AjaxRequestTarget target, RuntimeException e) {
                          super.onError(target, e);
                        }

                        @Override
                        protected void onUpdate(AjaxRequestTarget targetCfDelegato) {

                          if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())
                              && !datiRimborso
                                  .getCodiceFiscaleDelegato()
                                  .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
                            containerUploadAllegati.setVisible(true);
                          } else {
                            containerUploadAllegati.setVisible(false);
                            listaFileAllegato.removeAll(listaFileAllegato);
                          }

                          formUploadAllegati.getRootForm().setMultiPart(true);

                          formUploadAllegati.setMultiPart(true);

                          targetCfDelegato.add(containerUploadAllegati, formUploadAllegati);
                        }
                      });

              accreditoSuContoCorrentePanel
                  .getCodiceFiscaleDelegato()
                  .getTextField()
                  .add(
                      new AjaxFormComponentUpdatingBehavior("change") {

                        @Override
                        protected void onError(AjaxRequestTarget target, RuntimeException e) {
                          super.onError(target, e);
                        }

                        @Override
                        protected void onUpdate(AjaxRequestTarget targetCfDelegato) {

                          if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())
                              && !datiRimborso
                                  .getCodiceFiscaleDelegato()
                                  .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
                            containerUploadAllegati.setVisible(true);
                          } else {
                            containerUploadAllegati.setVisible(false);
                            listaFileAllegato.removeAll(listaFileAllegato);
                          }

                          formUploadAllegati.getRootForm().setMultiPart(true);

                          formUploadAllegati.setMultiPart(true);

                          targetCfDelegato.add(containerUploadAllegati, formUploadAllegati);
                        }
                      });

              accreditoSuContoCorrentePanel.setVisible(checkModalitaPagamentoContoCorrente);
              ritiroPressoTesoreriaPanel.setVisible(!checkModalitaPagamentoContoCorrente);

              containerUploadAllegati.setVisible(
                  !datiRimborso.isIntestatario()
                      || (datiRimborso.isIntestatario()
                          && PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())
                          && !datiRimborso
                              .getCodiceFiscaleDelegato()
                              .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())));
            }

            target.add(
                accreditoSuContoCorrentePanel, ritiroPressoTesoreriaPanel, containerUploadAllegati);
          }
        });
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public FileUploadField getAllegatiRimborsoUpload() {
    return allegatiRimborsoUpload;
  }

  public void setAllegatiRimborsoUpload(FileUploadField allegatiRimborsoUpload) {
    this.allegatiRimborsoUpload = allegatiRimborsoUpload;
  }

  public String getNomeAllegatoUpload() {
    return nomeAllegatoUpload;
  }

  public void setNomeAllegatoUpload(String nomeAllegatoUpload) {
    this.nomeAllegatoUpload = nomeAllegatoUpload;
  }

  public byte[] getByteAllegatoUpload() {
    return byteAllegatoUpload;
  }

  public void setByteAllegatoUpload(byte[] byteAllegatoUpload) {
    this.byteAllegatoUpload = byteAllegatoUpload;
  }

  public Form<?> getFormUploadAllegati() {
    return formUploadAllegati;
  }

  public void setFormUploadAllegati(Form<?> formUploadAllegati) {
    this.formUploadAllegati = formUploadAllegati;
  }

  public WebMarkupContainer getContainerAllegatiUpload() {
    return containerAllegatiUpload;
  }

  public void setContainerAllegatiUpload(WebMarkupContainer containerAllegatiUpload) {
    this.containerAllegatiUpload = containerAllegatiUpload;
  }

  public List<FileAllegato> getListaFileAllegato() {
    return listaFileAllegato;
  }

  public void setListaFileAllegato(List<FileAllegato> listaFileAllegato) {
    this.listaFileAllegato = listaFileAllegato;
  }

  @Override
  protected void onBeforeRender() {

    super.onBeforeRender();

    formUploadAllegati.setVisible(isEnabled());

    containerErede.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedenteRimborso())
            && !datiRimborso
                .getTipologiaRichiedenteRimborso()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));

    containerIntestario.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedenteRimborso())
            && datiRimborso
                .getTipologiaRichiedenteRimborso()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));
  }
}
