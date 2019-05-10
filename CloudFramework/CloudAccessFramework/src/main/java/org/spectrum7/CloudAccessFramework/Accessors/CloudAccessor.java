package org.spectrum7.CloudAccessFramework.Accessors;

import org.spectrum7.CloudAccessFramework.Accessors.Exceptions.CloudExceptions;

public interface CloudAccessor {
    /**
     * Create an instance without specifying the access keys. This function should be used only when
     * running your application on an Amazon EC2 instance. The IAM credentials which exist within the
     * instance metadata associated with the IAM role for the EC2 instance will be used to create the
     * new instance
     *
     * @param launchSpecifications Values for creation of the instance. Either the template ID
     *                             or details regarding the instance needs to be specified. * If template ID is not
     *                             specified, at a bare minimum, the following fields should be set in
     *                             AWSLaunchSpecifications
     *                             class * 1. AMI ID - The AMI ID from which the instance should be launched * 2.
     *                             Instance
     *                             Type - Type of the instance * 3. Subnet ID - The subnet in which the instance
     *                             should be
     *                             launched. The VPC is taken from the subnet ID * 4. SecurityGroupIDs - List of
     *                             security
     *                             group ids for that instance.
     */
    void createComputeInstance(LaunchSpecifications launchSpecifications)
            throws CloudExceptions;
    
    /**
     * Creates a compute instance with the specified access and secret keys. The metadata information
     * will also be applied to the created instance
     *
     * @param accessKey            The accesskey for creation of the instance
     * @param secretKey            The secret key for the instance
     * @param launchSpecifications Values for creation of the instance. Either the template ID
     *                             or details regarding the instance needs to be specified. * If template ID is not
     *                             specified, at a bare minimum, the following fields should be set in
     *                             AWSLaunchSpecifications
     *                             class * 1. AMI ID - The AMI ID from which the instance should be launched * 2.
     *                             Instance
     *                             Type - Type of the instance * 3. Subnet ID - The subnet in which the instance
     *                             should be
     *                             launched. The VPC is taken from the subnet ID * 4. SecurityGroupIDs - List of
     *                             security
     *                             group ids for that instance.
     */
    void createComputeInstance(
            String accessKey, String secretKey, LaunchSpecifications launchSpecifications) throws CloudExceptions;
}
