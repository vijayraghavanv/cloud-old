package org.spectrum7.CloudAccessFramework.AWS;

import com.google.common.flogger.FluentLogger;
import org.spectrum7.CloudAccessFramework.Accessors.Exceptions.CloudExceptions;
import org.spectrum7.CloudAccessFramework.Accessors.LaunchSpecifications;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;


public class AWSLaunchSpecifications implements LaunchSpecifications {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private InstanceType awsInstanceType = null;
    private Region awsRegion = null;
    private List<String> securityGroupIDs = null;
    private String reservationID = null;
    private List<String> networkInterfaces = null;
    private String userData = null;
    private HashMap<String, String> tags = null;
    private String privateKey = null;
    private String templateID = null;
    private String imageID = null;
    private String instanceID = null;
    private String publicIPAddress = null;
    private String privateIPAddress = null;
    private String publicHostName = null;
    private String subnetID = null;
    private String vpcID = null;
    private String instanceName = null;
    private List<Tag> awsTags = null;
    
    @Override
    public String getPrivateKey() {
        return privateKey;
    }
    
    @Override
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    
    @Override
    public String getRegion() {
        return awsRegion.toString();
    }
    
    @Override
    public void setRegion(String region) throws CloudExceptions {
        this.awsRegion =
                Region.regions().stream().filter(regionName -> region.equals(regionName.toString())).findFirst().orElse(null);
        if (awsRegion == null) {
            logger.atSevere().log("Region not found %s", region);
            throw new CloudExceptions("Specified Region cannot be determined in AWS. Please check and try again");
        }
        
    }
    
    @Override
    public String getInstanceType() {
        return this.awsInstanceType.toString();
    }
    
    @Override
    public void setInstanceType(String instanceType) throws CloudExceptions {
        this.awsInstanceType = InstanceType.fromValue(instanceType);
        if (this.awsInstanceType == null || this.awsInstanceType.compareTo(InstanceType.UNKNOWN_TO_SDK_VERSION) == 0) {
            logger.atSevere().log("Unknown instance type specified %s", instanceType);
            throw new CloudExceptions("Specified instance type not found in AWS");
            
            
        }
        
        
    }
    
    @Override
    public List<String> getSecurityGroupIDs() {
        return securityGroupIDs;
    }
    
    @Override
    public void setSecurityGroupIDs(List<String> securityGroupIDs) {
        this.securityGroupIDs = securityGroupIDs;
    }
    
    @Override
    public String getReservationID() {
        return reservationID;
    }
    
    @Override
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    
    @Override
    public String getUserData() {
        return userData;
    }
    
    @Override
    public void setUserData(String userData) {
        this.userData = userData;
    }
    
    @Override
    public Map<String, String> getTags() {
        return awsTags.stream().collect(toMap(Tag::key, Tag::value));
        
        
    }
    
    @Override
    public void setTags(Map<String, String> tags) {
        if (awsTags == null) {
            awsTags = new ArrayList<>();
        }
        tags.forEach((k, v) -> {
            Tag tag = Tag.builder().key(k).value(v).build();
            awsTags.add(tag);
            
        });
        
    }
    
    @Override
    public String getTemplateID() {
        return templateID;
    }
    
    @Override
    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }
    
    @Override
    public String getImageID() {
        return imageID;
    }
    
    @Override
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
    
    @Override
    public String getInstanceID() {
        return instanceID;
    }
    
    @Override
    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }
    
    @Override
    public String getPublicIPAddress() {
        return publicIPAddress;
    }
    
    @Override
    public void setPublicIPAddress(String publicIPAddress) {
        this.publicIPAddress = publicIPAddress;
    }
    
    @Override
    public String getPrivateIPAddress() {
        return privateIPAddress;
    }
    
    @Override
    public void setPrivateIPAddress(String privateIPAddress) {
        this.privateIPAddress = privateIPAddress;
    }
    
    @Override
    public String getPublicHostName() {
        return publicHostName;
    }
    
    @Override
    public void setPublicHostName(String publicHostName) {
        this.publicHostName = publicHostName;
    }
    
    @Override
    public String getSubnetID() {
        return subnetID;
    }
    
    @Override
    public void setSubnetID(String subnetID) {
        this.subnetID = subnetID;
    }
    
    @Override
    public String getVpcID() {
        return vpcID;
    }
    
    @Override
    public void setVpcID(String vpcID) {
        this.vpcID = vpcID;
    }
    
    @Override
    public String getInstanceName() {
        return instanceName;
    }
    
    @Override
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
