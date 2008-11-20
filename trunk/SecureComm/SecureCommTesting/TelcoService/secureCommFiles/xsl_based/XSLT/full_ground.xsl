<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:xsd="http://qualipso.authenticators/xsd"
  xmlns:exsl="http://exslt.org/common"
  xmlns:env="http://schemas.xmlsoap.org/soap/envelope/"
  version="1.0">

  <xsl:output method="xml" media-type="text/xml" indent="yes" omit-xml-declaration="yes" />

  <xsl:param name="BRIDGE_RESULT">NULL</xsl:param>

  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/> 
    </xsl:copy>
  </xsl:template>

  <xsl:template priority="2" match="*">
    <xsl:variable name="elem" ><xsl:value-of select="name(.)"/></xsl:variable>
    <xsl:element name="{$elem}"
		namespace="{namespace-uri()}"> 
		<xsl:apply-templates select="@*|node()"/> 
    </xsl:element> 
  </xsl:template>

  <!--<xsl:template match="@*[local-name()=mustUnderstand and .='1']"> -->
  <xsl:template priority="10" match="@*[local-name()='mustUnderstand' and .='1']">
    <xsl:variable name="mustUnd"><xsl:value-of select="name(.)"/></xsl:variable>
    <xsl:attribute name="{$mustUnd}">0</xsl:attribute>
  </xsl:template>


  <!-- delete these elements since they are mapped to other names -->
  <xsl:template priority="10" match="*[local-name()='operation' or local-name()='phoneNumber' or local-name()='uname' or local-name()='password']">
    <xsl:apply-templates select="*"/>
  </xsl:template> 


  <!-- there is always an 'entryPoint'. This element is not mapped. So use it to append the
       new, mapped elements --> 
  <xsl:template priority="10" match="*[local-name()='entryPoint'] ">
    <xsl:apply-templates select="*"/>
    <xsl:if test="document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='authType']" >
      <xsl:element name="authType"><xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='authType']" /></xsl:element>
    </xsl:if>
    <xsl:if test="name(document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='action'])" >
      <xsl:element name="action"><xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='action']" /></xsl:element>
    </xsl:if>

    <xsl:if test="name(document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='entryPoint'])" >
      <xsl:element name="entryPoint"><xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='entryPoint']" /></xsl:element>
    </xsl:if> 
    <xsl:if test="name(document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='phoneNumber'])" >
      <xsl:element name="phoneNumber"><xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='phoneNumber']" /></xsl:element>
    </xsl:if>
    <xsl:if test="name(document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='username'])" >
      <xsl:element name="username"><xsl:value-of select="document($BRIDGE_RESULT)//*[local-name()='TelcoServiceRequest' or local-name()='ServiceRequest']/*[local-name()='username']" /></xsl:element>
    </xsl:if>
  </xsl:template>
  

</xsl:stylesheet>


