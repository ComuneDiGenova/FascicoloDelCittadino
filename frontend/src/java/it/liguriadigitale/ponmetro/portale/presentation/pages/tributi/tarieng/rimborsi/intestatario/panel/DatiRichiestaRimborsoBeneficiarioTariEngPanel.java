package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCCodiceFiscaleTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCModalitaPagamentoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoPressoTesoreriaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoSuContoCorrentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.ModalitaPagametoTariEngRender;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.FileAllegato;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiRichiestaRimborsoBeneficiarioTariEngPanel extends BasePanel {

  private static final long serialVersionUID = 718691506541603308L;

  private int index;

  private FileUploadField allegatiRimborsoUpload;

  private String nomeAllegatoUpload;

  private byte[] byteAllegatoUpload;

  private Form<?> formUploadAllegati;

  private WebMarkupContainer containerAllegatiUpload;

  private List<FileAllegato> listaFileAllegato = new ArrayList<FileAllegato>();

  private WebMarkupContainer containerUploadAllegati =
      new WebMarkupContainer("containerUploadAllegati");

  private TipologiaRichiedenteRimborsoEnum tipologiaRichiedente;

  private DatiRichiestaRimborsoTariEng datiRimborso;

  @SuppressWarnings("rawtypes")
  private FdCPagamentoPressoTesoreriaPanel ritiroPressoTesoreriaPanel;

  @SuppressWarnings("rawtypes")
  private FdCPagamentoSuContoCorrentePanel accreditoSuContoCorrentePanel;

  private WebMarkupContainer containerErede = new WebMarkupContainer("containerErede");

  private WebMarkupContainer containerIntestario = new WebMarkupContainer("containerIntestario");

  public DatiRichiestaRimborsoBeneficiarioTariEngPanel(String id) {
    super(id);
  }

  public DatiRichiestaRimborsoBeneficiarioTariEngPanel(
      String id, DatiRichiestaRimborsoTariEng datiRimborso, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(datiRimborso);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {

    datiRimborso = (DatiRichiestaRimborsoTariEng) dati;

    containerUploadAllegati.setOutputMarkupId(true);
    containerUploadAllegati.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerUploadAllegati);

    containerErede.setOutputMarkupId(true);
    containerErede.setOutputMarkupPlaceholderTag(true);
    containerErede.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedente())
            && !datiRimborso
                .getTipologiaRichiedente()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));

    containerIntestario.setOutputMarkupId(true);
    containerIntestario.setOutputMarkupPlaceholderTag(true);
    containerIntestario.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedente())
            && datiRimborso
                .getTipologiaRichiedente()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));

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

    FdCTextField nomeBeneficiario =
        new FdCTextField(
            "nomeBeneficiario",
            new PropertyModel(datiRimborso, "nomeBeneficiario"),
            DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);
    nomeBeneficiario.setRequired(true);
    nomeBeneficiario.setEnabled(false);
    addOrReplace(nomeBeneficiario);

    FdCTextField cognomeBeneficiario =
        new FdCTextField(
            "cognomeBeneficiario",
            new PropertyModel(datiRimborso, "cognomeBeneficiario"),
            DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);
    cognomeBeneficiario.setRequired(true);
    cognomeBeneficiario.setEnabled(false);
    addOrReplace(cognomeBeneficiario);

    FdCCodiceFiscaleTextField codiceFiscaleBeneficiario =
        new FdCCodiceFiscaleTextField(
            "codiceFiscaleBeneficiario",
            new PropertyModel(datiRimborso, "codiceFiscaleBeneficiario"),
            DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);
    codiceFiscaleBeneficiario.getTextField().setRequired(true);
    codiceFiscaleBeneficiario.setEnabled(false);
    addOrReplace(codiceFiscaleBeneficiario);

    FdCTextField indirizzoBeneficiario =
        new FdCTextField(
            "indirizzoBeneficiario",
            new PropertyModel(datiRimborso, "indirizzoBeneficiario"),
            DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);
    indirizzoBeneficiario.setRequired(true);
    addOrReplace(indirizzoBeneficiario);

    FdCTextField comuneBeneficiario =
        new FdCTextField(
            "comuneBeneficiario",
            new PropertyModel(datiRimborso, "comuneBeneficiario"),
            DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);
    comuneBeneficiario.setRequired(true);
    addOrReplace(comuneBeneficiario);

    FdCTextField capBeneficiario =
        new FdCTextField(
            "capBeneficiario",
            new PropertyModel(datiRimborso, "capBeneficiario"),
            DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);
    capBeneficiario.setRequired(true);
    addOrReplace(capBeneficiario);

    List<ModalitaPagamentoEnum> listaModalitaPagamento =
        ServiceLocator.getInstance().getServiziTariEng().getListaModalitaPagamento();

    FdCModalitaPagamentoDropDownChoice modalitaDiPagamento =
        new FdCModalitaPagamentoDropDownChoice(
            "modalitaDiPagamento",
            new PropertyModel(datiRimborso, "modalitaDiPagamento"),
            new ModalitaPagametoTariEngRender(),
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

              log.debug("CP modalitaDiPagamento.getValue() = " + modalitaDiPagamento.getValue());

              boolean checkModalitaPagamentoContoCorrente =
                  modalitaDiPagamento.getValue().equalsIgnoreCase("CAB");

              if (checkModalitaPagamentoContoCorrente) {

                log.debug(
                    "CP checkModalitaPagamentoContoCorrente si "
                        + datiRimborso.getCodiceFiscaleDelegato()
                        + " - "
                        + datiRimborso.getIban());

                datiRimborso.setNomeDelegato(null);
                datiRimborso.setCognomeDelegato(null);
                datiRimborso.setCodiceFiscaleDelegato(null);

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

                log.debug(
                    "CP checkModalitaPagamentoContoCorrente no "
                        + datiRimborso.getCodiceFiscaleDelegato());

                datiRimborso.setIban(null);
                datiRimborso.setSwift(null);
                datiRimborso.setNomeDelegato(null);
                datiRimborso.setCognomeDelegato(null);
                datiRimborso.setCodiceFiscaleDelegato(null);

                accreditoSuContoCorrentePanel =
                    new FdCPagamentoSuContoCorrentePanel<>(
                        "accreditoSuContoCorrentePanel", datiRimborso, false, true);
                addOrReplace(accreditoSuContoCorrentePanel);
              }

              accreditoSuContoCorrentePanel.setVisible(checkModalitaPagamentoContoCorrente);
              ritiroPressoTesoreriaPanel.setVisible(!checkModalitaPagamentoContoCorrente);
            }

            target.add(accreditoSuContoCorrentePanel, ritiroPressoTesoreriaPanel);
          }
        });

    containerAllegatiUpload = new WebMarkupContainer("containerAllegatiUpload");
    containerAllegatiUpload.setVisible(false);
    containerAllegatiUpload.setOutputMarkupId(true);

    formUploadAllegati =
        new Form<Void>("formUploadAllegati") {

          private static final long serialVersionUID = -6806533036225320232L;

          @Override
          protected void onSubmit() {}
        };
    formUploadAllegati.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = 5825524470816311229L;

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
            // target.add(feedbackPanel);
            // target.add(DatiRichiestaRimborsoBeneficiarioTariEngPanel.this);

            target.add(getPage());
          }
        });

    formUploadAllegati.setMultiPart(true);
    // formUploadAllegati.setMaxSize(Bytes.kilobytes(2000));

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

    containerUploadAllegati.addOrReplace(formUploadAllegati);

    ListView<FileAllegato> listaAllegati =
        new ListView<FileAllegato>("listaAllegati", listaFileAllegato) {

          private static final long serialVersionUID = 7902644958301300420L;

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

                  private static final long serialVersionUID = -3434396409768272639L;

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

  public FileUploadField getAllegatiRimborsoUpload() {
    return allegatiRimborsoUpload;
  }

  public void setAllegatiRimborsoUpload(FileUploadField allegatiRimborsoUpload) {
    this.allegatiRimborsoUpload = allegatiRimborsoUpload;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    //		sb.append("$.validator.addMethod('filesize', function(value, element, param) {\r\n"
    //				+ "    // param = size (en bytes) \r\n"
    //				+ "    // element = element to validate (<input>)\r\n"
    //				+ "    // value = value of the element (file name)\r\n"
    //				+ "    return this.optional(element) || (element.files[0].size <= param) \r\n"
    //				+ "});"
    //		);
    //
    //		sb.append("$('#formid').validate({\r\n"
    //				+ "    rules: { inputimage: { required: true, accept: \"png|jpe?g|gif\", filesize: 1048576
    //  }},\r\n"
    //				+ "    messages: { inputimage: \"File must be JPG, GIF or PNG, less than 1MB\" }\r\n"
    //				+ "});");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    formUploadAllegati.setVisible(isEnabled());

    containerErede.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedente())
            && !datiRimborso
                .getTipologiaRichiedente()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));

    containerIntestario.setVisible(
        LabelFdCUtil.checkIfNotNull(datiRimborso)
            && LabelFdCUtil.checkIfNotNull(datiRimborso.getTipologiaRichiedente())
            && datiRimborso
                .getTipologiaRichiedente()
                .value()
                .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value()));
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public TipologiaRichiedenteRimborsoEnum getTipologiaRichiedente() {
    return tipologiaRichiedente;
  }

  public void setTipologiaRichiedente(TipologiaRichiedenteRimborsoEnum tipologiaRichiedente) {
    this.tipologiaRichiedente = tipologiaRichiedente;
  }
}
