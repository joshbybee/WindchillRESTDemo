package com.custom.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.custom.rest.UpdateResponse;
import com.custom.rest.NodeType;

import com.lcs.wc.client.ClientContext;
import com.lcs.wc.flexbom.FlexBOMLink;
import com.lcs.wc.flexbom.FlexBOMPart;
import com.lcs.wc.flexbom.LCSFlexBOMQuery;
import com.lcs.wc.flextype.FlexTypeCache;
import com.lcs.wc.foundation.LCSQuery;
import com.lcs.wc.material.LCSMaterial;
import com.lcs.wc.material.LCSMaterialMaster;
import com.lcs.wc.material.LCSMaterialQuery;
import com.lcs.wc.product.LCSProduct;
import com.lcs.wc.product.LCSProductClientModel;
import com.lcs.wc.util.LCSProperties;
import com.lcs.wc.util.VersionHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/custom")
@Api(value="/Custom")
public class CustomService{
	
	private String adminGroup = LCSProperties.get("jsp.main.administratorsGroup", "Administrators");
	
		@GET
		@Produces({MediaType.APPLICATION_JSON})
		@Path("/example/{param}")
		@ApiOperation(
				value="Get a String of Hello World",
				notes="Returns Hello World and the parameter in the query parameter",	
				response=String.class)
		public String getDemo(@ApiParam(value = "a string parameter!") @PathParam("param") String param) throws Exception {

			String response = "Hello World" + param;
			ObjectMapper mapper = new ObjectMapper(); 
			String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
			
			return output;
		}
		
		@GET
		@Produces({MediaType.APPLICATION_JSON})
		@Path("/flexbom/{productOID}")
		@ApiOperation(
				value="Get FlexPLM BOM for a Product OID",
				notes="Returns all FlexPLM BOMs for a Product OID",	
				response=FlexBOMResponse.class)
		public String flexBOM(@ApiParam(value = "the OID of the LCSProduct to be fetched") @PathParam("productOID") String productOID) throws Exception {
			
			LCSProduct product = (LCSProduct)LCSQuery.findObjectById(productOID);
			
			Collection<FlexBOMPart> flexBOMs = LCSFlexBOMQuery.findBOMObjects(product, null, null, "MAIN");
			List<FlexBOMPartProxy> productBOMs = new ArrayList<>();
			
			for(FlexBOMPart productBOM : flexBOMs)
			{
				
				FlexBOMPartProxy flexBOMProxy = new FlexBOMPartProxy(productBOM);

					List<FlexBOMLinkProxy> links = new ArrayList<>();
					Collection<FlexBOMLink> bomLinks = LCSFlexBOMQuery.findFlexBOMLinks(productBOM, null, null, null, null, null, "WIP_ONLY", null, false, "ALL_APPLICABLE_TO_DIMENSION", "ALL_SKUS", null, null);
					
					for(FlexBOMLink link : bomLinks)
					{
						FlexBOMLinkProxy hmkFlexBOMLink = new FlexBOMLinkProxy(link);
						LCSMaterialMaster materialMaster = link.getChild();
						
						if(!LCSMaterialQuery.PLACEHOLDER.equals(materialMaster))
						{
							LCSMaterial material = (LCSMaterial) VersionHelper.latestIterationOf(materialMaster);
							MaterialProxy materialProxy = new MaterialProxy(material);
							hmkFlexBOMLink.setMaterial(materialProxy);
						}
						links.add(hmkFlexBOMLink);
					} 
					flexBOMProxy.setFlexBOMLinks(links);
				productBOMs.add(flexBOMProxy);
		     }
			
			ProductProxy productProxy = new ProductProxy(product);
			
			FlexBOMResponse response = new FlexBOMResponse(productProxy,productBOMs); 

			ObjectMapper mapper = new ObjectMapper(); 
			String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response); //.withDefaultPrettyPrinter()
			
			return output;
		}
		
		/** 
		 * @param nonce The required CSRF nonce for invoking a PUT/POST operation
		 * @param nodeType The type of node the product is to be created under. Ex: "Product\\custom\\customBag"
		 * */
		@POST
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON}) 
		@Path("/products")
		@ApiOperation(
				value="Create a new product.",
				notes="Creates a new product and responds with its OID.",
				response=UpdateResponse.class)
		public Response createProduct(@NotNull 
									  @ApiParam(value = "The CSRF nonce as returned from the /security/csrf endpoint") @HeaderParam("CSRF_NONCE") String nonce,
									  @ApiParam(value = "The type of node the product is to be created under. Ex: Product\\custom\\customBag") NodeType nodeType) throws Exception {
			try{			
				// 401 unauthorized if user is not an admin
				ClientContext lcsContext = ClientContext.getContext();
				if(!lcsContext.inGroup(adminGroup.toUpperCase())){
					UpdateResponse data = new UpdateResponse(null);
					data.setMessage("logged in user is not authorized to invoke this action.");
					return Response.status(401).entity(data).build(); 
				} 

				//201 Created new Product.
				LCSProductClientModel model = new LCSProductClientModel();
				model.setFlexType(FlexTypeCache.getFlexTypeFromPath(nodeType.getNodeType()));
				model.save();
				
				UpdateResponse data = new UpdateResponse(model.getBusinessObject().toString()); 
				
				return Response.status(201).entity(data).build();
			}
			catch(Exception e){
				e.printStackTrace();
				UpdateResponse data = new UpdateResponse(null);
				data.setMessage("something died, review error logs on server.");
				return Response.status(500).entity(data).build();
			}
		}		
}