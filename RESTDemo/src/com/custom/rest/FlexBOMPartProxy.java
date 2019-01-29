package com.custom.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcs.wc.flexbom.FlexBOMPart;
import com.lcs.wc.util.FormatHelper;

import wt.util.WTException;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({ "objectIdentifier", "ptcbomPartName", "FlexBOMLink" })
public class FlexBOMPartProxy{

	public List<FlexBOMLinkProxy> flexBOMLinks;
	private FlexBOMPart flexTyped;
	public FlexBOMPartProxy(FlexBOMPart flexTyped) {
		this.flexTyped = flexTyped;
	}
	
	@JsonProperty("objectIdentifier")
	public String getObjectIdentifier(){
		return (FormatHelper.getObjectId((FlexBOMPart)flexTyped));
	}
	@JsonProperty("ptcbomPartName")
	public String getPtcBomPartName() throws WTException{
		return flexTyped.getValue("ptcbomPartName").toString();
	}
	@JsonProperty("FlexBOMLink")
	public List<FlexBOMLinkProxy> getFlexBOMLinks() {
		return flexBOMLinks;
	}
	public void setFlexBOMLinks(List<FlexBOMLinkProxy> flexBOMLinks) {
		this.flexBOMLinks = flexBOMLinks;
	}
}
