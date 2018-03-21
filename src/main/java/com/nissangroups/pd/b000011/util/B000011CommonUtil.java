/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch
 * Function ID     :PST-DRG-B000011
 * Module          : Ordering					
 * Process Outline : RHQ/NSC-wise Volume/OCF Allocation
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 04-11-2015  	  z015060(RNTBCI)               New Creation
 *
 */
package com.nissangroups.pd.b000011.util;

import java.util.List;

import javax.persistence.Query;
import com.nissangroups.pd.util.PDConstants;

public class B000011CommonUtil {

	/**
	 * Instantiates a new B000011CommonUtil
	 */
	private B000011CommonUtil() {

	}

	/**
	 * @param prcsOlyFlg
	 * @param buyerGrpLvlOCF
	 * @return
	 */
	public static StringBuilder addOrdrTkBsMnthPrcssFlg(String prcsOlyFlg,
			StringBuilder buyerGrpLvlOCF) {
		if (prcsOlyFlg.equals(PDConstants.Y)) {
			buyerGrpLvlOCF.append(B000011Constants.IN_ORDER_TAKE_BASE_MONTH);
		} else {
			buyerGrpLvlOCF.append(B000011Constants.GR_EQ_ORDER_TAKE_BASE_MONTH);
		}
		return buyerGrpLvlOCF;
	}

	/**
	 * @param query
	 * @param ordrTkBsMnth
	 * @param prcsOlyFlg
	 * @param ordrTkBsMnth2
	 * @return
	 */
	public static Query addqueryParam(Query query, List<String> ordrTkBsMnth,
			String prcsOlyFlg, String ordrTkBsMnth2) {
		if (prcsOlyFlg.equals(PDConstants.Y)) {
			query.setParameter(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
					ordrTkBsMnth);
		} else {
			query.setParameter(PDConstants.PRMTR_ORDER_TAKE_BASE_MONTH,
					ordrTkBsMnth2);
		}
		return query;
	}

}
