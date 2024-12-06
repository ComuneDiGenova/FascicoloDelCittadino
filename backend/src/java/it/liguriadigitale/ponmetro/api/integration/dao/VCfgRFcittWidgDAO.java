package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * VCfgRFcittWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:40:04.957
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittWidg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgRFcittWidgDAO extends AbstractSearchDAO {

  protected VCfgRFcittWidg vcfgrfcittwidg;

  public VCfgRFcittWidgDAO(VCfgRFcittWidg vcfgrfcittwidg) {
    super();
    this.vcfgrfcittwidg = vcfgrfcittwidg;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT * from V_CFG_R_FCITT_WIDG WHERE 1=1";
    if (vcfgrfcittwidg.getIdFcitt() != null) sql += " and ID_FCITT  = ? ";
    if (vcfgrfcittwidg.getIdSez() != null) sql += " and ID_SEZ  = ? ";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (vcfgrfcittwidg.getIdFcitt() != null) st.setLong(index++, vcfgrfcittwidg.getIdFcitt());
    if (vcfgrfcittwidg.getIdSez() != null) st.setLong(index++, vcfgrfcittwidg.getIdSez());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgRFcittWidg obj = new VCfgRFcittWidg();

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setOrdinamentoS(r.getLong("ORDINAMENTO_S"));

    obj.setOrdinamentoC(r.getLong("ORDINAMENTO_C"));

    obj.setOrdinamentoW(r.getLong("ORDINAMENTO_W"));

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setDataCatalogazioneSez(
        r.getTimestamp("DATA_CATALOGAZIONE_SEZ") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_SEZ").toLocalDateTime()
            : null);
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setFlagAbilitazioneSez(r.getBoolean("FLAG_ABILITAZIONE_SEZ"));
    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    obj.setUriComp(r.getString("URI_COMP"));
    obj.setDataCatalogazioneComp(
        r.getTimestamp("DATA_CATALOGAZIONE_COMP") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime()
            : null);
    obj.setDataInvioMsg(
        r.getTimestamp("DATA_INVIO_MSG") != null
            ? r.getTimestamp("DATA_INVIO_MSG").toLocalDateTime()
            : null);
    obj.setFlagAbilitazioneComp(r.getBoolean("FLAG_ABILITAZIONE_COMP"));
    obj.setIdFunz(r.getLong("ID_FUNZ"));

    obj.setDenominazioneFunz(r.getString("DENOMINAZIONE_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    obj.setWicketLabelIdStd(r.getString("WICKET_LABEL_ID_STD"));
    obj.setWicketLabelIdAlt(r.getString("WICKET_LABEL_ID_ALT"));
    obj.setClassePaginaStd(r.getString("CLASSE_PAGINA_STD"));
    obj.setClassePaginaAlt(r.getString("CLASSE_PAGINA_ALT"));
    obj.setWicketTitleStd(r.getString("WICKET_TITLE_STD"));
    obj.setWicketTitleAlt(r.getString("WICKET_TITLE_ALT"));
    obj.setFlagAbilitazioneFunz(r.getBoolean("FLAG_ABILITAZIONE_FUNZ"));
    obj.setIdFunzSosp(r.getLong("ID_FUNZ_SOSP"));

    obj.setDataInizioSosp(
        r.getTimestamp("DATA_INIZIO_SOSP") != null
            ? r.getTimestamp("DATA_INIZIO_SOSP").toLocalDateTime()
            : null);
    obj.setDataFineSosp(
        r.getTimestamp("DATA_FINE_SOSP") != null
            ? r.getTimestamp("DATA_FINE_SOSP").toLocalDateTime()
            : null);
    obj.setTipoSosp(r.getLong("TIPO_SOSP"));

    obj.setFlagAbilitazioneFunzSosp(r.getBoolean("FLAG_ABILITAZIONE_FUNZ_SOSP"));
    obj.setIdWidg(r.getLong("ID_WIDG"));

    obj.setDenominazioneWidg(r.getString("DENOMINAZIONE_WIDG"));
    obj.setDescrizioneWidg(r.getString("DESCRIZIONE_WIDG"));
    obj.setUriWidg(r.getString("URI_WIDG"));
    obj.setDataCatalogazioneWidg(
        r.getTimestamp("DATA_CATALOGAZIONE_WIDG") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_WIDG").toLocalDateTime()
            : null);
    obj.setFlagDefault(r.getBoolean("FLAG_DEFAULT"));
    obj.setFlagAbilitazioneWidg(r.getBoolean("FLAG_ABILITAZIONE_WIDG"));
    obj.setIdCfgRFcittWidg(r.getLong("ID_CFG_R_FCITT_WIDG"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setDataAssociazioneFcittWidg(
        r.getTimestamp("DATA_ASSOCIAZIONE_FCITT_WIDG") != null
            ? r.getTimestamp("DATA_ASSOCIAZIONE_FCITT_WIDG").toLocalDateTime()
            : null);
    obj.setFlagAbilitazioneFcittWidg(r.getBoolean("FLAG_ABILITAZIONE_FCITT_WIDG"));
    obj.setPersonId(r.getLong("PERSON_ID"));

    obj.setFlagResidente(r.getBoolean("FLAG_RESIDENTE"));
    obj.setFlagNonResidente(r.getBoolean("FLAG_NON_RESIDENTE"));

    return obj;
  }
}
