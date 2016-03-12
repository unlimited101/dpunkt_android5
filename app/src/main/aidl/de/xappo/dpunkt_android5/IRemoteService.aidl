// IRemoteService.aidl
package de.xappo.dpunkt_android5;

// Declare any non-default types here with import statements

import de.xappo.dpunkt_android5.Triple;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    long calcResult();

    int getTripleSum(in Triple triple);


}
