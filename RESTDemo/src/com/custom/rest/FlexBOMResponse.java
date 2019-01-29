package com.custom.rest;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



/**
 * Serializable class containing LCSProduct, LCSSKU, LCSProductSeasonLink, LCSSKUSeasonLink, LCSSeason
 * Including the List<FlexBOMPart>(s), FlexBOMLinks & LCSMaterials
 * Classes are ordered in a hierarchical structure and annotated using fasterxml.jackson.annotation
 */
@JsonPropertyOrder({ "LCSProduct", "FlexBOMPart" })
public class FlexBOMResponse {

	private List<FlexBOMPartProxy> flexBOMParts;
	private ProductProxy product;
	
	public FlexBOMResponse(ProductProxy product, List<FlexBOMPartProxy> flexBOMPart) { 
		this.product = product;
		this.flexBOMParts = flexBOMPart;
	}
	
	@JsonProperty("LCSProduct")
	public ProductProxy getLCSProduct() {
		return product;
	}
	
	@JsonProperty("FlexBOMPart")
	public List<FlexBOMPartProxy> getFlexBOMPart() {
		return flexBOMParts;
	}
	
}
