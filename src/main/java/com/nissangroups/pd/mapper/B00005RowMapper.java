/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000005
 * Module          :Cretae Orderable Sales  Enditem Feature MST
 * Process Outline :Spec Master 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * @Date  	  z010343(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nissangroups.pd.dao.B00005Dao;
import static com.nissangroups.pd.util.PDConstants.*;

public class B00005RowMapper implements RowMapper<B00005Dao> {

    
    /** Variable batch. */
    private String batch;
    
    /* (non-Javadoc)
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    @Override
    public B00005Dao mapRow(ResultSet rs, int rowNum) throws SQLException {

       B00005Dao rowMapper = new B00005Dao();
       if(BATCH000005.equals(this.batch))
       {
           rowMapper.setExteriorColorCd(rs.getString("EXT_CLR_CD"));
           rowMapper.setInteriorColorCd(rs.getString("INT_CLR_CD"));
           rowMapper.setOseiAdoptDate(rs.getString("OSEI_ADPT_DATE"));
       }
       if(BATCH000004.equals(this.batch))
       {
           rowMapper.setOseiAdoptDate(rs.getString("EIM_MIN_ADPT_DATE"));
       }
       rowMapper.setAbolishDate(rs.getString("ABOLISH_DATE"));
       rowMapper.setAdtnlSpecCd(rs.getString("ADTNL_SPEC_CD"));
       rowMapper.setAppliedModelCd(rs.getString("APPLD_MDL_CD"));
       rowMapper.setBuyerCd(rs.getString("BUYER_CD"));
       rowMapper.setCarSeries(rs.getString("CAR_SRS"));
       rowMapper.setEndItemStatusCd(rs.getString("END_ITM_STTS_CD"));
       rowMapper.setOcfBuyerGroupCd(rs.getString("OCF_BUYER_GRP_CD"));
       rowMapper.setOcfRegionCd(rs.getString("OCF_REGION_CD"));
       rowMapper.setOptionSpecCd(rs.getString("OPTN_SPEC_CODE"));
       
       rowMapper.setPackCd(rs.getString("PCK_CD"));
       rowMapper.setPorCd(rs.getString("POR_CD"));
       rowMapper.setSpecDestinationCd(rs.getString("SPEC_DESTN_CD"));
       rowMapper.setUkOeiBuyerId(rs.getString("OEI_BUYER_ID"));
       rowMapper.setUkOseiId(rs.getString("OSEI_ID"));

       

        return rowMapper;
    }

    /**
     * Sets the batch.
     *
     * @param batch the new batch
     */
    public void setBatch(String batch) {
        this.batch = batch;
    }
    
}