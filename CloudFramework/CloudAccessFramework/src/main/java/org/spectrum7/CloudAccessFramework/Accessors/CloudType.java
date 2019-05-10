package org.spectrum7.CloudAccessFramework.Accessors;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public enum CloudType {
    AWS("aws"),
    GOOGLE("google"),
    UNKNOWN(null);
    private final String value;
    
    private CloudType(String value) {
        this.value = value;
    }
    
    /**
     * Use this in place of valueOf to convert the raw string returned by the service into the enum value.
     *
     * @param value real value
     * @return CloudType corresponding to the value
     */
    public static CloudType fromValue(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(CloudType.values()).filter(e -> e.toString().equals(value)).findFirst()
                .orElse(UNKNOWN);
    }
    
    /**
     * Use this in place of {@link #values()} to return a {@link Set} of all values known to the SDK. This will return
     * all known enum values except {@link #UNKNOWN}.
     *
     * @return a {@link Set} of known {@link CloudType}s
     */
    public static Set<CloudType> knownValues() {
        return Stream.of(values()).filter(v -> v != UNKNOWN).collect(toSet());
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    
}
