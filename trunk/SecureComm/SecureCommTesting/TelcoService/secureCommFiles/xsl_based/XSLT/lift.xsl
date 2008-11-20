<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns="http://www.qualipso.org/interop/semant/securecomm/TelephoneService/sourceOntology.owl#"
  version="2.0">

  <xsl:output method="xml"/>

  <xsl:template match="/">
  
  <TelcoServiceRequest rdf:ID="TelcoServiceRequest_Example">
  <xsl:for-each select="*">
    <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']" >
      <!-- get the content of 'operation' element -->
      <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='operation']" >
        <operation><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='operation']" /></operation> 
      </xsl:if>
      <!-- get the content of 'entryPoint' element -->
      <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='entryPoint']" >
        <entryPoint><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='entryPoint']" /></entryPoint> 
      </xsl:if>
      <!-- get the content of 'phoneNumber' element -->
      <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='phoneNumber']" >
        <phoneNumber><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='phoneNumber']" /></phoneNumber> 
      </xsl:if>
        
      <user>
        <TelcoClient rdf:ID="KKontis_User">        
          <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='uname']" >
            <uname><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='uname']" /></uname>
          </xsl:if>
          <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='password']" >
            <password><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='password']" /></password>
          </xsl:if>
          <!-- check to see if there is a certificate and fill the 'hasCertificate' element accordingly -->
          <xsl:choose>
            <xsl:when test="//*[name()='env:Envelope']/*[name()='env:Header']/*[local-name()='Security']/*[local-name()='Signature']/*[local-name()='KeyInfo']/*[local-name()='SecurityTokenReference']/*[local-name()='X509Data']" >
              <hasCertificate>true</hasCertificate>
            </xsl:when>
            <xsl:otherwise>
              <hasCertificate>false</hasCertificate>
            </xsl:otherwise>
          </xsl:choose>
           
          <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='fullName']" >
            <fullName><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='fullName']" /></fullName>
          </xsl:if>
          <xsl:if test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='telephone']" >
            <telephone><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='telephone']" /></telephone>
          </xsl:if>
          <!-- decide about the 'id' and the 'hasId' elements -->
          <xsl:choose>
            <xsl:when test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='id']" >
              <id><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='id']" /></id>
              <hasId>true</hasId>
            </xsl:when>
            <xsl:otherwise>
              <hasId>false</hasId>
            </xsl:otherwise>
          </xsl:choose>

          <!-- decide about the 'socsecnum' and the 'hasSocSecNum' elements -->
          <xsl:choose>
            <xsl:when test="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='socsecnum']" >
              <socsecnum><xsl:value-of select="//*[name()='env:Envelope']/*[name()='env:Body']/*[local-name()='getAccountInfo' or local-name()='resetPin']/*[local-name()='socsecnum']" /></socsecnum>
              <hasSocSecNum>true</hasSocSecNum>
            </xsl:when>
            <xsl:otherwise>
              <hasSocSecNum>false</hasSocSecNum>
            </xsl:otherwise>
          </xsl:choose>

        </TelcoClient>
      </user>
    </xsl:if>
  </xsl:for-each>
  </TelcoServiceRequest>
  </xsl:template>
  
</xsl:stylesheet>
