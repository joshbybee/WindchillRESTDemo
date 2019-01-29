package com.custom.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcs.wc.product.LCSProduct;
import com.lcs.wc.util.FormatHelper;

import wt.util.WTException;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({ "objectIdentifier", "productName" })
public class ProductProxy{

	private LCSProduct product;
	public ProductProxy(LCSProduct product) {
		this.product = product;
	}
	
	@JsonProperty("objectIdentifier")
	public String getObjectIdentifier(){
		return (FormatHelper.getObjectId(product));
	}
	
	@JsonProperty("productName")
	public String getProductName() throws WTException {
		return product.getValue("productName").toString();
	}
}
