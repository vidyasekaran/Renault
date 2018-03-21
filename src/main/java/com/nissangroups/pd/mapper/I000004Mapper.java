/*
 * System Name     :Post Dragon 
 * Sub system Name :Batch / Interface
 * Function ID     :PST-DRG-I000004Mapper
 * Module          :@Module
 * Process Outline :@Process_Outline
 *
 * <Revision History>
 * Date       Name(Company Name)             Description 
 * ---------- ------------------------------ ---------------------
 * 2015/07/24  	  @author(RNTBCI)               New Creation
 *
 */ 
package com.nissangroups.pd.mapper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;



/**
 * The Class I000004Mapper.
 *
 * @author z002548
 */
@Entity
public class I000004Mapper implements Serializable{
    
    /** Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Variable id. */
    @EmbeddedId
    private I000004Mapperpk id;
    
    /** Variable sequence no. */
    @Column(name="SEQ_NO")
    private String sequenceNo;
    
    
    /** Variable file id. */
    @Column(name="IF_FILE_ID")
    private String fileID;
    
    /** Variable col14. */
    @Column(name="COL14")
    private String col14;
    
    /** Variable col15. */
    @Column(name="COL15")
    private String col15;
    
    /** Variable col16. */
    @Column(name="COL16")
    private String col16;
    
    /** Variable col18. */
    @Column(name="COL18")
    private String col18;
    
    /** Variable col19. */
    @Column(name="COL19")
    private String col19;
    
    /** Variable col20. */
    @Column(name="COL20")
    private String col20;
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public I000004Mapperpk getId() {
        return id;
    }
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(I000004Mapperpk id) {
        this.id = id;
    }
    
    /**
     * Gets the sequence no.
     *
     * @return the sequence no
     */
    public String getSequenceNo() {
        return sequenceNo;
    }
    
    /**
     * Sets the sequence no.
     *
     * @param sequenceNo the new sequence no
     */
    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
    
    /**
     * Gets the col14.
     *
     * @return the col14
     */
    public String getCol14() {
        return col14;
    }
    
    /**
     * Sets the col14.
     *
     * @param col14 the new col14
     */
    public void setCol14(String col14) {
        this.col14 = col14;
    }
    
    /**
     * Gets the col15.
     *
     * @return the col15
     */
    public String getCol15() {
        return col15;
    }
    
    /**
     * Sets the col15.
     *
     * @param col15 the new col15
     */
    public void setCol15(String col15) {
        this.col15 = col15;
    }
    
    /**
     * Gets the col16.
     *
     * @return the col16
     */
    public String getCol16() {
        return col16;
    }
    
    /**
     * Sets the col16.
     *
     * @param col16 the new col16
     */
    public void setCol16(String col16) {
        this.col16 = col16;
    }

    /**
     * Gets the col18.
     *
     * @return the col18
     */
    public String getCol18() {
        return col18;
    }
    
    /**
     * Sets the col18.
     *
     * @param col18 the new col18
     */
    public void setCol18(String col18) {
        this.col18 = col18;
    }
    
    /**
     * Gets the col19.
     *
     * @return the col19
     */
    public String getCol19() {
        return col19;
    }
    
    /**
     * Sets the col19.
     *
     * @param col19 the new col19
     */
    public void setCol19(String col19) {
        this.col19 = col19;
    }
    
    /**
     * Gets the col20.
     *
     * @return the col20
     */
    public String getCol20() {
        return col20;
    }
    
    /**
     * Sets the col20.
     *
     * @param col20 the new col20
     */
    public void setCol20(String col20) {
        this.col20 = col20;
    }
    
    /**
     * Gets the file id.
     *
     * @return the file id
     */
    public String getFileID() {
        return fileID;
    }
    
    /**
     * Sets the file id.
     *
     * @param fileID the new file id
     */
    public void setFileID(String fileID) {
        this.fileID = fileID;
    }
    
    
}
