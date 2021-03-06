/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package com.sun.mirror.type;


import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.sun.mirror.declaration.Declaration;


/**
 * Thrown when an application attempts to access a sequence of {@link Class}
 * objects each corresponding to a {@link TypeMirror}.
 *
 * @see MirroredTypeException
 * @see Declaration#getAnnotation(Class)
 */
public class MirroredTypesException extends RuntimeException {

    private static final long serialVersionUID = 1;

    private transient Collection<TypeMirror> types;	// cannot be serialized
    private Collection<String> names;		// types' qualified "names"

    /**
     * Constructs a new MirroredTypesException for the specified types.
     *
     * @param types  an ordered collection of the types being accessed
     */
    public MirroredTypesException(Collection<TypeMirror> types) {
	super("Attempt to access Class objects for TypeMirrors " + types);
	this.types = types;
	names = new ArrayList<String>();
	for (TypeMirror t : types) {
	    names.add(t.toString());
	}
    }

    /**
     * Returns the type mirrors corresponding to the types being accessed.
     * The type mirrors may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * @return the type mirrors in order, or <tt>null</tt> if unavailable
     */
    public Collection<TypeMirror> getTypeMirrors() {
	return (types != null)
		? Collections.unmodifiableCollection(types)
		: null;
    }

    /**
     * Returns the fully qualified names of the types being accessed.
     * More precisely, returns the canonical names of each class,
     * interface, array, or primitive, and <tt>"void"</tt> for
     * the pseudo-type representing the type of <tt>void</tt>.
     *
     * @return the fully qualified names, in order, of the types being
     *		accessed
     */
    public Collection<String> getQualifiedNames() {
	return Collections.unmodifiableCollection(names);
    }
}
