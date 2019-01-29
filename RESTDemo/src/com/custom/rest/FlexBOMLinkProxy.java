package com.custom.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcs.wc.flexbom.FlexBOMLink;
import com.lcs.wc.util.FormatHelper;

import wt.util.WTException;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({ "objectIdentifier","materialDescription","partName", "LCSMaterial"})
public class FlexBOMLinkProxy  {
	
	public MaterialProxy material;
	private FlexBOMLink flexTyped;
	public FlexBOMLinkProxy(FlexBOMLink flexTyped) {
		this.flexTyped = flexTyped;
	}	
	
	@JsonProperty("objectIdentifier")
	public String getObjectIdentifier(){
		return (FormatHelper.getObjectId((FlexBOMLink)flexTyped));
	}
	
	@JsonProperty("LCSMaterial")
	public MaterialProxy getMaterial() {
		return material;
	}
	
	public void setMaterial(MaterialProxy material) {
		this.material = material;
	}
	
	@JsonProperty("materialDescription")
	public String getMaterialDescription() throws WTException {
		return flexTyped.getValue("materialDescription").toString();
	}
	
	@JsonProperty("partName")
	public String getLocation() throws WTException {
		return flexTyped.getValue("partName").toString();
	}
}

