package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * VCfgTCatWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:40:06.206
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatWidg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgTCatWidgDAO extends AbstractSearchDAO {

  private VCfgTCatWidg vcfgtcatwidg;

  public VCfgTCatWidgDAO(VCfgTCatWidg vcfgtcatwidg) {
    super();
    this.vcfgtcatwidg = vcfgtcatwidg;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CFG_T_CAT_WIDG";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgTCatWidg obj = new VCfgTCatWidg();

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
    obj.setFlagAbilitazioneWidg(r.getBoolean("FLAG_ABILITAZIONE_WIDG"));
    obj.setFlagDefault(r.getBoolean("FLAG_DEFAULT"));
    return obj;
  }
}
