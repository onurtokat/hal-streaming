// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore.dto;

import org.slf4j.LoggerFactory;
import java.util.Iterator;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameter;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import com.alu.motive.hal.datacollector.commons.plugin.dto.augmentation.AugmentingParameters;
import com.alu.motive.hal.datacollector.parser.tr69.TR69Parameter;
import java.util.List;
import org.slf4j.Logger;
import java.io.Serializable;

public class FileStoreTR069InformDTO extends FileStoreDTO implements Serializable
{
    private static final long serialVersionUID = 1299579594L;
    private String type;
    private boolean isAllowedToCOD;
    private Integer ttl;
    private static final Logger LOG;
    private String[] identifiers;
    private List<TR69Parameter> parameterList;
    int sizeOfRow;
    private long timeStamp;
    private int paramInInform;
    private AugmentingParameters augmentingParams;
    private List<Long> sizesOfPersistableRows;
    private String filterDeviceId;
    
    public FileStoreTR069InformDTO() {
        this.type = "parameters";
        this.isAllowedToCOD = true;
        this.ttl = null;
        this.identifiers = null;
        this.sizeOfRow = 0;
        this.augmentingParams = new AugmentingParameters();
        this.sizesOfPersistableRows = new ArrayList<Long>();
        this.filterDeviceId = null;
    }
    
    @Override
    public int getNumberOfPersitableRows() {
        return this.parameterList.size();
    }
    
    public String getFilterDeviceId() {
        if (this.filterDeviceId == null) {
            final StringBuffer sb = new StringBuffer();
            sb.append("oui=");
            sb.append(this.identifiers[1].trim());
            sb.append("-");
            sb.append("pc=");
            sb.append(this.identifiers[2].trim());
            sb.append("-");
            sb.append("sn=");
            sb.append(this.identifiers[3].trim());
            return sb.toString();
        }
        return this.filterDeviceId;
    }
    
    public void setFilterDeviceId(final String filterDeviceId) {
        this.filterDeviceId = filterDeviceId;
    }
    
    @Override
    public RowDTO createPersistableRow(final FileStoreFileFormat fileFormat, final int rowIndex, final char columnDelimiter) {
        final RowDTO rowDTO = this.createEmptyRowDTO(fileFormat, columnDelimiter);
        if (rowDTO == null) {
            return null;
        }
        for (int i = 0; i < this.identifiers.length; ++i) {
            final String id = this.identifiers[i];
            rowDTO.append(id);
        }
        final TR69Parameter pDTO = this.parameterList.get(rowIndex);
        rowDTO.append(pDTO.getName());
        final String columnDelimiterString = String.valueOf(columnDelimiter);
        rowDTO.append(StringUtils.replace(StringUtils.replace(pDTO.getValue(), "\\", "\\\\"), columnDelimiterString, "\\" + columnDelimiterString));
        rowDTO.append(pDTO.getType());
        if (FileStoreTR069InformDTO.LOG.isDebugEnabled()) {
            FileStoreTR069InformDTO.LOG.debug("pDTO.getName()::" + pDTO.getName());
            FileStoreTR069InformDTO.LOG.debug("pDTO.getValue()::" + pDTO.getValue());
            FileStoreTR069InformDTO.LOG.debug("pDTO.getType()::" + pDTO.getType());
        }
        rowDTO.append(Long.toString(this.timeStamp));
        if (this.augmentingParams != null) {
            for (final AugmentingParameter augParam : this.augmentingParams.getParameters()) {
                rowDTO.append(augParam.toParameterDTO().getValue());
            }
        }
        this.sizesOfPersistableRows.add(rowIndex, rowDTO.getSizeOfPersistableRow());
        return rowDTO;
    }
    
    @Override
    public long getSizeOfPersistableRow(final int rowIndex) {
        return this.sizesOfPersistableRows.get(rowIndex);
    }
    
    @Override
    public String[] getIdentifiers() {
        return this.identifiers;
    }
    
    @Override
    public void setIdentifiers(final String[] identifiers) {
        this.identifiers = identifiers;
    }
    
    public List<TR69Parameter> getParameterList() {
        return this.parameterList;
    }
    
    public void setParameterList(final List<TR69Parameter> parameterList) {
        this.parameterList = parameterList;
    }
    
    public void setParamsInInform(final int paramsInInform) {
        this.paramInInform = paramsInInform;
    }
    
    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }
    
    public void setTimeStamp(final long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    protected RowDTO createEmptyRowDTO(final FileStoreFileFormat fileFormat, final char columnDelimiter) {
        RowDTO rowDTO = null;
        switch (fileFormat) {
            case TEXT: {
                rowDTO = new TextRowDTO(columnDelimiter);
                break;
            }
            case SEQUENCE: {
                rowDTO = new SequenceRowDTO(columnDelimiter);
                break;
            }
            case RC: {
                rowDTO = new RCRowDTO();
                break;
            }
        }
        return rowDTO;
    }
    
    @Override
    public String getDataType() {
        return this.type;
    }
    
    @Override
    public void setDataType(final String type) {
        this.type = type;
    }
    
    public int getParamsInInform() {
        return this.paramInInform;
    }
    
    @Override
    public boolean isAllowedToCOD() {
        return this.isAllowedToCOD;
    }
    
    @Override
    public void setIsAllowedToCOD(final boolean writeToCod) {
        this.isAllowedToCOD = writeToCod;
    }
    
    @Override
    public void setTtl(final Integer ttl) {
        this.ttl = ttl;
    }
    
    @Override
    public Integer getTtl() {
        return this.ttl;
    }
    
    @Override
    public void setAugmentingParameters(final AugmentingParameters augmentingParams) {
        this.augmentingParams = augmentingParams;
    }
    
    @Override
    public AugmentingParameters getAugmentingParameters() {
        return this.augmentingParams;
    }
    
    @Override
    public String toString() {
        final StringBuilder strBldr = new StringBuilder();
        strBldr.append("DataCollectorDTO{Identifiers = (");
        if (null != this.getIdentifiers() && this.getIdentifiers().length > 0) {
            for (final String id : this.identifiers) {
                strBldr.append(id);
                strBldr.append(",");
            }
        }
        strBldr.append("), paramList = ");
        strBldr.append(this.getParameterList());
        strBldr.append(", timeStamp = ");
        strBldr.append(this.getTimeStamp());
        strBldr.append("}");
        return strBldr.toString();
    }
    
    static {
        LOG = LoggerFactory.getLogger(FileStoreTR069InformDTO.class);
    }
}
