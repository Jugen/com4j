package com4j;

/**
 * Root of all the com4j interfaces.
 *
 * <p>
 * Java interfaces mapped from COM interfaces always derive from this
 * interface directly/indirectly. This interface provides methods
 * that are common to all the COM interfaces.
 *
 * @author Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public interface Com4jObject {
    /**
     * Tests the identity of two COM objects.
     *
     * <p>
     * This consists of doing <tt>IUnknown::QueryInterface</tt> on
     * two interfaces and test the bit image of the resulting <tt>IUnknown*</tt>.
     *
     * <p>
     * If one COM object implements two interfaces, in Java
     * you see them as two different objects. Thus you
     * cannot rely on <tt>==</tt> to check if they represent
     * the same COM object.
     */
    boolean equals(Object o);

    /**
     * Hash code consistent with {@link #equals(java.lang.Object)} }.
     *
     * <p>
     * This method queries the <tt>IUnknown*</tt> value to the wrapped
     * COM object and returns its pointer bit image as integer.
     *
     * <p>
     * The net result is that the identity of {@link Com4jObject} is based
     * on the identity of the underlying COM objects. Two {@link Com4jObject}
     * that are holding different interfaces of the same COM object is
     * considered "equal".
     */
    int hashCode();

    /**
     * Prints the raw interface pointer that this object represents.
     */
    String toString();

    /**
     * Releases a reference to the wrapped COM object.
     *
     * <p>
     * Since Java objects tend to live longer in memory until it's GC-ed,
     * and applications have generally no control over when it's GC-ed,
     * calling the release method earlier enables applications to release
     * the COM objects deterministically.
     */
    void release();

    /**
     * Checks if this COM object implements a given interface.
     *
     * <p>
     * This is just a convenience method that behaves as follows:
     * <pre>
     * return queryInterface(comInterface)!=null;
     * </pre>
     *
     * @return
     *      true if the wrapped COM object implements a given interface.
     */
    <T extends Com4jObject> boolean is( Class<T> comInterface );

    /**
     * Invokes the queryInterface of the wrapped COM object and attempts
     * to obtain a different interface of the same object.
     *
     * @return null
     *      if the queryInterface fails.
     */
    <T extends Com4jObject> T queryInterface( Class<T> comInterface );
}
