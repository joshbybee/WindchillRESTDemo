/**
 * Return a PTC.CSRFToken entity which contains some inforation about the nonce.
 * The first property NonceKey is the name of the header that must be provided
 * on the request to Windchill. The second property, NonceValue actually
 * contains the value of the nonce.
 */
function function_GetCSRFToken(data, params) {
    var ValueType = Java.type('org.apache.olingo.commons.api.data.ValueType');
    var PropertyValueType = Java.type('com.ptc.odata.core.entity.property.PropertyValueType');
    var EntityAttribute = Java.type('com.ptc.odata.core.entity.property.EntityAttribute');
    var CSRFProtector = Java.type('com.ptc.core.appsec.CSRFProtector');
    var ComplexValue = Java.type('org.apache.olingo.commons.api.data.ComplexValue');
    var Property = Java.type('org.apache.olingo.commons.api.data.Property');

    var nonce = CSRFProtector.getNonce(data.getHttpRequest());
    var nonceHeader = "CSRF_NONCE";
    var nonceValue = "NonceValue";
    var nonceKey = "NonceKey";
	//var customKey = "CustomAtt";

    var complexValue = new ComplexValue();
    complexValue.getValue().add(new Property("String", nonceKey, ValueType.PRIMITIVE, nonceHeader));
    complexValue.getValue().add(new Property("String", nonceValue, ValueType.PRIMITIVE, nonce));
	//complexValue.getValue().add(new Property("String", customKey, ValueType.PRIMITIVE, nonce));
    return new EntityAttribute(null, 'CSRFToken', PropertyValueType.COMPLEX, complexValue);
}


function function_GetProductName(data, params) {
    var ValueType = Java.type('org.apache.olingo.commons.api.data.ValueType');
    var PropertyValueType = Java.type('com.ptc.odata.core.entity.property.PropertyValueType');
    var EntityAttribute = Java.type('com.ptc.odata.core.entity.property.EntityAttribute');
    var ComplexValue = Java.type('org.apache.olingo.commons.api.data.ComplexValue');
    var Property = Java.type('org.apache.olingo.commons.api.data.Property');
    
	var LCSProduct = Java.type('com.lcs.wc.product.LCSProduct');
	var LCSQuery = Java.type('com.lcs.wc.foundation.LCSQuery');
	var oidParam = params.get("oid");
	var oid = oidParam.getValue();
	var product = LCSQuery.findObjectById(oid);
	var productName = product.getValue("productName").toString();
	
    var nonceValue = "productName";
    var nonceKey = "oid";
    
	var complexValue = new ComplexValue();
    complexValue.getValue().add(new Property("String", nonceKey, ValueType.PRIMITIVE, oid));
    complexValue.getValue().add(new Property("String", nonceValue, ValueType.PRIMITIVE, productName));
	return new EntityAttribute(null, 'LCSProduct', PropertyValueType.COMPLEX, complexValue);

}
