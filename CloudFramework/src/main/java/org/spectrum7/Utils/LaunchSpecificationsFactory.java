package org.spectrum7.Utils;

import org.spectrum7.CloudAccessFramework.AWS.AWSLaunchSpecifications;
import org.spectrum7.CloudAccessFramework.Accessors.CloudType;
import org.spectrum7.CloudAccessFramework.Accessors.Exceptions.CloudExceptions;
import org.spectrum7.CloudAccessFramework.Accessors.LaunchSpecifications;

public class LaunchSpecificationsFactory {
    /**
     * Obtain a launch template specification relevant to cloud provider being used.
     *
     * @param cloudType valid string as specified in CloudType class.
     * @return LaunchSpecifications class. Null could be returned if value passed is null
     * @throws CloudExceptions Exception thrown when the specified cloud provider is not found.
     */
    public static LaunchSpecifications getLaunchSpecifications(String cloudType) throws CloudExceptions {
        CloudType c = CloudType.fromValue(cloudType);
        LaunchSpecifications l = null;
        if (c != null && c != CloudType.UNKNOWN) {
            if (c.equals(CloudType.AWS)) {
                l = new AWSLaunchSpecifications();
            }
            
        }
        else {
            throw new CloudExceptions("Illegal cloud provider specified" + cloudType);
        }
        return l;
    }
}
