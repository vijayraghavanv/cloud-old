package org.spectrum7.CloudAccessFramework.Accessors;

import org.spectrum7.CloudAccessFramework.Accessors.Exceptions.CloudExceptions;

import java.util.List;
import java.util.Map;

public interface LaunchSpecifications {
    
    public String getPrivateKey();
    
    void setPrivateKey(String privateKey);
    
    public String getRegion();
    
    public void setRegion(String region) throws CloudExceptions;
    
    public String getInstanceType();
    
    public void setInstanceType(String instanceType) throws CloudExceptions;
    
    public List<String> getSecurityGroupIDs();
    
    public void setSecurityGroupIDs(List<String> securityGroupIDs);
    
    public String getReservationID();
    
    public void setReservationID(String reservationID);
    
    public String getUserData();
    
    public void setUserData(String userData);
    
    public Map<String, String> getTags();
    
    public void setTags(Map<String, String> tags);
    
    public String getTemplateID();
    
    public void setTemplateID(String templateID);
    
    public String getImageID();
    
    public void setImageID(String imageID);
    
    public String getInstanceID();
    
    public void setInstanceID(String instanceID);
    
    public String getPublicIPAddress();
    
    public void setPublicIPAddress(String publicIPAddress);
    
    public String getPrivateIPAddress();
    
    public void setPrivateIPAddress(String privateIPAddress);
    
    public String getPublicHostName();
    
    public void setPublicHostName(String publicHostName);
    
    public String getSubnetID();
    
    public void setSubnetID(String subnetID);
    
    public String getVpcID();
    
    public void setVpcID(String vpcID);
    
    public String getInstanceName();
    
    public void setInstanceName(String instanceName);
}
