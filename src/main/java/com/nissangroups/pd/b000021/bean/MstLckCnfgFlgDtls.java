/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch 
 * Function ID     :PST-DRG-B000021
 * Module          :O Ordering
 * Process Outline :Monthly Process Stage Open or Close 
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 28-09-2015  	  z014433(RNTBCI)               Initial Version
 *
 */
package com.nissangroups.pd.b000021.bean;

public class MstLckCnfgFlgDtls {
	
		/** The por cd. */
		private String porCd;

		/** The stage cd. */
		private String stageCd;

		/** The stage status cd. */
		private String stageStatusCd;

		/** The Exporter Flag */
		private String expFlg;

		/** The Order transmission Flag */
		private String OrdrTsmnFlg;
		
		/** The RHQ Flag */
		private String rHQFlg;
		
		/** The NSC Flag */
		private String nSCFlg;
		
		/** The Order take base month */
		private String ordrTkBsMnth;
		
		/**
		 * Gets the porCd
		 *
		 * @return the porCd
		 */
		public String getPorCd() {
			return porCd;
		}

		/**
		 * Sets the porCd
		 *
		 * @param porCd the porCd to set
		 */
		public void setPorCd(String porCd) {
			this.porCd = porCd;
		}

		/**
		 * Gets the stageCd
		 *
		 * @return the stageCd
		 */
		public String getStageCd() {
			return stageCd;
		}

		/**
		 * Sets the stageCd
		 *
		 * @param stageCd the stageCd to set
		 */
		public void setStageCd(String stageCd) {
			this.stageCd = stageCd;
		}

		/**
		 * Gets the stageStatusCd
		 *
		 * @return the stageStatusCd
		 */
		public String getStageStatusCd() {
			return stageStatusCd;
		}

		/**
		 * Sets the stageStatusCd
		 *
		 * @param stageStatusCd the stageStatusCd to set
		 */
		public void setStageStatusCd(String stageStatusCd) {
			this.stageStatusCd = stageStatusCd;
		}

		/**
		 * Gets the expFlg
		 *
		 * @return the expFlg
		 */
		public String getExpFlg() {
			return expFlg;
		}

		/**
		 * Sets the expFlg
		 *
		 * @param expFlg the expFlg to set
		 */
		public void setExpFlg(String expFlg) {
			this.expFlg = expFlg;
		}

		/**
		 * Gets the ordrTsmnFlg
		 *
		 * @return the ordrTsmnFlg
		 */
		public String getOrdrTsmnFlg() {
			return OrdrTsmnFlg;
		}

		/**
		 * Sets the ordrTsmnFlg
		 *
		 * @param ordrTsmnFlg the ordrTsmnFlg to set
		 */
		public void setOrdrTsmnFlg(String ordrTsmnFlg) {
			OrdrTsmnFlg = ordrTsmnFlg;
		}

		/**
		 * Gets the rHQFlg
		 *
		 * @return the rHQFlg
		 */
		public String getrHQFlg() {
			return rHQFlg;
		}

		/**
		 * Sets the rHQFlg
		 *
		 * @param rHQFlg the rHQFlg to set
		 */
		public void setrHQFlg(String rHQFlg) {
			this.rHQFlg = rHQFlg;
		}

		/**
		 * Gets the nSCFlg
		 *
		 * @return the nSCFlg
		 */
		public String getnSCFlg() {
			return nSCFlg;
		}

		/**
		 * Sets the nSCFlg
		 *
		 * @param nSCFlg the nSCFlg to set
		 */
		public void setnSCFlg(String nSCFlg) {
			this.nSCFlg = nSCFlg;
		}

		/**
		 * Gets the ordrTkBsMnth
		 *
		 * @return the ordrTkBsMnth
		 */
		public String getOrdrTkBsMnth() {
			return ordrTkBsMnth;
		}

		/**
		 * Sets the ordrTkBsMnth
		 *
		 * @param ordrTkBsMnth the ordrTkBsMnth to set
		 */
		public void setOrdrTkBsMnth(String ordrTkBsMnth) {
			this.ordrTkBsMnth = ordrTkBsMnth;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((OrdrTsmnFlg == null) ? 0 : OrdrTsmnFlg.hashCode());
			result = prime * result + ((expFlg == null) ? 0 : expFlg.hashCode());
			result = prime * result + ((nSCFlg == null) ? 0 : nSCFlg.hashCode());
			result = prime * result + ((porCd == null) ? 0 : porCd.hashCode());
			result = prime * result + ((rHQFlg == null) ? 0 : rHQFlg.hashCode());
			result = prime * result + ((stageCd == null) ? 0 : stageCd.hashCode());
			result = prime * result
					+ ((stageStatusCd == null) ? 0 : stageStatusCd.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof MstLckCnfgFlgDtls)) {
				return false;
			}
			MstLckCnfgFlgDtls other = (MstLckCnfgFlgDtls) obj;
			if (OrdrTsmnFlg == null) {
				if (other.OrdrTsmnFlg != null) {
					return false;
				}
			} else if (!OrdrTsmnFlg.equals(other.OrdrTsmnFlg)) {
				return false;
			}
			if (expFlg == null) {
				if (other.expFlg != null) {
					return false;
				}
			} else if (!expFlg.equals(other.expFlg)) {
				return false;
			}
			if (nSCFlg == null) {
				if (other.nSCFlg != null) {
					return false;
				}
			} else if (!nSCFlg.equals(other.nSCFlg)) {
				return false;
			}
			if (porCd == null) {
				if (other.porCd != null) {
					return false;
				}
			} else if (!porCd.equals(other.porCd)) {
				return false;
			}
			if (rHQFlg == null) {
				if (other.rHQFlg != null) {
					return false;
				}
			} else if (!rHQFlg.equals(other.rHQFlg)) {
				return false;
			}
			if (stageCd == null) {
				if (other.stageCd != null) {
					return false;
				}
			} else if (!stageCd.equals(other.stageCd)) {
				return false;
			}
			if (stageStatusCd == null) {
				if (other.stageStatusCd != null) {
					return false;
				}
			} else if (!stageStatusCd.equals(other.stageStatusCd)) {
				return false;
			}
			return true;
		}

}
