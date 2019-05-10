package org.spectrum7.CloudAccessFramework.AWS;

import com.google.common.flogger.FluentLogger;
import org.spectrum7.CloudAccessFramework.Accessors.CloudAccessor;
import org.spectrum7.CloudAccessFramework.Accessors.Exceptions.CloudExceptions;
import org.spectrum7.CloudAccessFramework.Accessors.LaunchSpecifications;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class AWSAccessor implements CloudAccessor {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    
    /**
     * Create an instance without specifying the access keys. This function should be used only when
     * running your application on an Amazon EC2 instance. The IAM credentials * which exist within
     * the instance metadata associated with the IAM role for the EC2 instance will be used to *
     * create the new instance
     *
     * @param instanceSpecifications Values for creation of the instance. Either the launchtemplate ID
     *                               or details regarding the instance needs to be specified. If launchtemplate ID is
     *                               not
     *                               specified, at a bare minimum, the following fields should be set in
     *                               AWSLaunchSpecifications
     *                               class 1. AMI ID - The AMI ID from which the instance should be launched 2.
     *                               Instance Type -
     *                               Type of the instance 3. Subnet ID - The subnet in which the instance should be
     *                               launched.
     *                               The VPC is taken from the subnet ID 4. SecurityGroupIDs - List of security group
     *                               ids for
     *                               that instance.
     */
    @Override
    public void createComputeInstance(LaunchSpecifications instanceSpecifications)
            throws CloudExceptions {
        if (instanceSpecifications instanceof AWSLaunchSpecifications) {
            Ec2Client ec2Client = getEC2Client(instanceSpecifications.getRegion());
            if (ec2Client != null) {
                createComputeInstance(ec2Client, instanceSpecifications);
                
            }
            else {
                logger.atSevere().log(
                        "EC2 Client is null. Please check %s", instanceSpecifications.getRegion());
                throw new NullPointerException("Unable to obtain an EC2 client instance");
            }
        }
        else {
            logger.atSevere().log(
                    "The launch specifications is not of a suitable type. Expected AWS instance");
        }
    }
    
    private void createComputeInstance(
            Ec2Client ec2Client, LaunchSpecifications instanceSpecifications) throws CloudExceptions {
        String instanceName = instanceSpecifications.getInstanceName();
        String launchTemplateID = null;
        RunInstancesRequest.Builder runInstancesRequestBuilder = RunInstancesRequest.builder();
        String privateKey = generateKeyPair(instanceName, ec2Client, true);
        instanceSpecifications.setPrivateKey(privateKey);
        if (instanceSpecifications.getTemplateID() != null) {
            launchTemplateID = instanceSpecifications.getTemplateID();
        }
        if (privateKey != null && !privateKey.isEmpty()) {
            if (launchTemplateID != null) {
                LaunchTemplateSpecification launchTemplateSpecification =
                        LaunchTemplateSpecification.builder().launchTemplateId(launchTemplateID).build();
                runInstancesRequestBuilder.launchTemplate(launchTemplateSpecification);
            }
            else {
            }
            runInstancesRequestBuilder.maxCount(1).minCount(1).keyName(instanceName);
        }
    }
    
    /**
     * Create an instance by specifying the access keys. Inherently this is a less secure method.
     * Ensure that the credentials are transmitted on a secure protocol like tls. Create a specific
     * IAM role for this purpose and obtain the access and secret keys instead of using a global key
     * pair.
     *
     * @param accessKey              AWS Accesskey.
     * @param secretKey              AWS Secretkey
     * @param instanceSpecifications Values for creation of the instance. Either the launchtemplate ID
     *                               or details regarding the instance needs to be specified. If launchtemplate ID is
     *                               not
     *                               specified, at a bare minimum, the following fields should be set in
     *                               AWSLaunchSpecifications
     *                               class 1. AMI ID - The AMI ID from which the instance should be launched 2.
     *                               Instance Type -
     *                               Type of the instance 3. Subnet ID - The subnet in which the instance should be
     *                               launched.
     *                               The VPC is taken from the subnet ID 4. SecurityGroupIDs - List of security group
     *                               ids for
     *                               that instance.
     */
    @Override
    public void createComputeInstance(
            String accessKey, String secretKey, LaunchSpecifications instanceSpecifications)
            throws CloudExceptions {
        Ec2Client ec2Client = getEC2Client(accessKey, secretKey, instanceSpecifications.getRegion());
        if (ec2Client != null) {
            createComputeInstance(ec2Client, instanceSpecifications);
            
        }
        else {
            logger.atSevere().log(
                    "EC2 Client is null. Please check %s", instanceSpecifications.getRegion());
            throw new NullPointerException("Unable to obtain an EC2 client instance");
        }
    }
    
    /**
     * Returns an Ec2Client with the credentials of the instance on which the application is running
     *
     * @param region String with region ID. This string should be one of those defined by AWS.
     * @return Ec2Client instance
     */
    private Ec2Client getEC2Client(String region) {
        InstanceProfileCredentialsProvider ipcp = InstanceProfileCredentialsProvider.create();
        Region awsRegion = Region.of(region);
        return Ec2Client.builder().credentialsProvider(ipcp).region(awsRegion).build();
    }
    
    /**
     * Returns an Ec2Client with the credentials of the instance on which the application is running
     *
     * @param accessKey The access key for the instance
     * @param secretKey The secret key for the instance
     * @param region    String with region ID. This string should be one of those defined by AWS.
     * @return Ec2Client instance
     */
    private Ec2Client getEC2Client(String accessKey, String secretKey, String region) {
        Region awsRegion = Region.of(region);
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return Ec2Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(awsRegion)
                .build();
    }
    
    /**
     * Generates a key pair based on the name provided. This key pair is used to create EC2 instances.
     * Key pairs are combination of public and private keys and is used for secure PKI communication.
     * The created EC2 instances can be accessed only using this keypair.
     *
     * @param keyPairName The name for the key pair.
     * @param ec2         The EC2 client for creating the key pair
     * @param forceCreate if true, earlier keypairs with the keyPairName are deleted and a fresh
     *                    keypair is created. If false, a new keypair with the keyPairName is created.
     * @return The key material of the private key
     */
    private String generateKeyPair(String keyPairName, Ec2Client ec2, boolean forceCreate) {
        DescribeKeyPairsResponse response = ec2.describeKeyPairs();
    /*
    Checks if the keypair is already present.
     */
        if (forceCreate) {
            for (KeyPairInfo key_pair : response.keyPairs()) {
                if (key_pair.keyName().equalsIgnoreCase(keyPairName)) {
                    DeleteKeyPairRequest request =
                            DeleteKeyPairRequest.builder().keyName(keyPairName).build();
                    ec2.deleteKeyPair(request);
                }
            }
        }
        CreateKeyPairRequest request = CreateKeyPairRequest.builder().keyName(keyPairName).build();
        CreateKeyPairResponse createKeyPairResponse = ec2.createKeyPair(request);
        return createKeyPairResponse.keyMaterial(); // Returns the private key.
    }
}
