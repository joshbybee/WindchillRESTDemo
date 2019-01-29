package com.custom.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcs.wc.flextype.FlexTyped;
import com.lcs.wc.material.LCSMaterial;
import com.lcs.wc.util.FormatHelper;

import wt.util.WTException;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({ "objectIdentifier", "ptcmaterialName" })
public class MaterialProxy {

	FlexTyped flexTyped;
	public MaterialProxy(FlexTyped flexTyped) {
		this.flexTyped = flexTyped;
	}
	@JsonProperty("objectIdentifier")
	public String getObjectIdentifier(){
		return (FormatHelper.getObjectId((LCSMaterial)flexTyped));
	}
	
	@JsonProperty("ptcmaterialName")
	public String getPtcMaterialName() throws WTException{
		return flexTyped.getValue("ptcmaterialName").toString();
	}
}
