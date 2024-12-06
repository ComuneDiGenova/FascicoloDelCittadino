package it.liguriadigitale.ponmetro.api.integration.dao.view;

/**
 * VCfgTCatGlobo
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-03-08T11:51:35.431
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.globo.VCfgTCatGlobo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgTCatGloboDAO extends AbstractSearchDAO {

  private VCfgTCatGlobo vcfgtcatglobo;

  public VCfgTCatGloboDAO(VCfgTCatGlobo vcfgtcatglobo) {
    super();
    this.vcfgtcatglobo = vcfgtcatglobo;
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CFG_T_CAT_GLOBO T ";
    sql += " where 1=1 ";
    if (vcfgtcatglobo.getIdStatoRec() != null && vcfgtcatglobo.getIdStatoRec())
      sql += " and t.id_stato_rec=1 ";
    if (vcfgtcatglobo.getIdStatoRecGlobo() != null && vcfgtcatglobo.getIdStatoRecGlobo())
      sql += " and t.id_stato_rec_globo=1 ";
    if (vcfgtcatglobo.getFlagAbilitazioneGlobo() != null
        && vcfgtcatglobo.getFlagAbilitazioneGlobo()) sql += " and t.flag_abilitazione_globo=1 ";
    if (vcfgtcatglobo.getFlagAbilitazione() != null && vcfgtcatglobo.getFlagAbilitazione())
      sql += " and t.flag_abilitazione=1 ";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgTCatGlobo obj = new VCfgTCatGlobo();

    obj.setIdFunz(r.getLong("ID_FUNZ"));
    if (r.wasNull()) {
      obj.setIdFunz(null);
    }

    obj.setIdComp(r.getLong("ID_COMP"));
    if (r.wasNull()) {
      obj.setIdComp(null);
    }

    obj.setDenominazioneFunz(r.getString("DENOMINAZIONE_FUNZ"));
    obj.setDescrizioneFunz(r.getString("DESCRIZIONE_FUNZ"));
    obj.setWicketLabelIdStd(r.getString("WICKET_LABEL_ID_STD"));
    obj.setWicketLabelIdAlt(r.getString("WICKET_LABEL_ID_ALT"));
    obj.setClassePaginaStd(r.getString("CLASSE_PAGINA_STD"));
    obj.setClassePaginaAlt(r.getString("CLASSE_PAGINA_ALT"));
    obj.setWicketTitleStd(r.getString("WICKET_TITLE_STD"));
    obj.setWicketTitleAlt(r.getString("WICKET_TITLE_ALT"));
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    obj.setFlagResidente(r.getBoolean("FLAG_RESIDENTE"));
    obj.setFlagNonResidente(r.getBoolean("FLAG_NON_RESIDENTE"));
    obj.setIdPagina(r.getString("ID_PAGINA"));
    obj.setIconaCss(r.getString("ICONA_CSS"));
    obj.setIdProcedimento(r.getString("ID_PROCEDIMENTO"));
    obj.setIdGlobo(r.getLong("ID_GLOBO"));
    if (r.wasNull()) {
      obj.setIdGlobo(null);
    }

    obj.setIdMaggioli(r.getLong("ID_MAGGIOLI"));
    if (r.wasNull()) {
      obj.setIdMaggioli(null);
    }

    obj.setDenominazioneProcedimento(r.getString("DENOMINAZIONE_PROCEDIMENTO"));
    obj.setUrlGloboNew(r.getString("URL_GLOBO_NEW"));
    obj.setUrlGloboLista(r.getString("URL_GLOBO_LISTA"));
    obj.setUrlSito(r.getString("URL_SITO"));
    obj.setFlagAbilitazioneGlobo(r.getBoolean("FLAG_ABILITAZIONE_GLOBO"));
    obj.setIdStatoRecGlobo(r.getBoolean("ID_STATO_REC_GLOBO"));
    obj.setFlagResidenteGlobo(r.getBoolean("FLAG_RESIDENTE_GLOBO"));
    obj.setFlagNonResidenteGlobo(r.getBoolean("FLAG_NON_RESIDENTE_GLOBO"));
    obj.setUrnProcedimentoGlobo(r.getString("URN_PROCEDIMENTO_GLOBO"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }
}
