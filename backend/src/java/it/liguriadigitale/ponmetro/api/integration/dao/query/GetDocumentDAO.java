package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrPrivacyVer;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyVersioneRequest;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetDocumentDAO extends AbstractSearchDAO {

  private PrivacyVersioneRequest request;

  public GetDocumentDAO(PrivacyVersioneRequest request) {
    super();
    this.request = request;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {

    String sql =
        "select pv.id_privacy_ver, pv.pdf_file "
            + "from pr_privacy_ver pv "
            + " inner join pr_servizi s on pv.id_privacy = s.id_privacy where 1=1 ";
    if (request.getCodApplicazione() != null) sql += " and  s.cod_servizio = ?";
    if (request.getIdVersionePrivacy() != null) sql += " and pv.id_privacy_ver = ?";

    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (request.getCodApplicazione() != null) st.setString(index++, request.getCodApplicazione());
    if (request.getIdVersionePrivacy() != null) st.setLong(index++, request.getIdVersionePrivacy());
  }

  @Override
  protected Object getFromResultSet(ResultSet rs) throws SQLException {

    PrPrivacyVer obj = new PrPrivacyVer();
    obj.setIdPrivacyVer(rs.getLong("ID_PRIVACY_VER"));
    Blob tmpPDF_FILE = rs.getBlob("PDF_FILE");
    if (tmpPDF_FILE != null) {
      obj.setPdfFile(new javax.sql.rowset.serial.SerialBlob(tmpPDF_FILE));
    } else {
      obj.setPdfFile(null);
    }
    return obj;
  }
}
