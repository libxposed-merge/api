package io.github.libxposed.api.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Closeable;

import io.github.libxposed.api.annotations.XposedApiExact;

/**
 * The interface Dex parser.
 */
@SuppressWarnings("unused")
@XposedApiExact(100)
public interface DexParser extends Closeable {
    /**
     * The constant NO_INDEX.
     */
    @XposedApiExact(100)
    int NO_INDEX = 0xffffffff;

    /**
     * The interface Array.
     */
    @XposedApiExact(100)
    interface Array {
        /**
         * Get values value [ ].
         *
         * @return the value [ ]
         */
        @NonNull
        @XposedApiExact(100)
        Value[] getValues();
    }

    /**
     * The interface Annotation.
     */
    @XposedApiExact(100)
    interface Annotation {
        /**
         * Gets visibility.
         *
         * @return the visibility
         */
        @XposedApiExact(100)
        int getVisibility();

        /**
         * Gets type.
         *
         * @return the type
         */
        @NonNull
        @XposedApiExact(100)
        TypeId getType();

        /**
         * Get elements element [ ].
         *
         * @return the element [ ]
         */
        @NonNull
        @XposedApiExact(100)
        Element[] getElements();
    }

    /**
     * The interface Value.
     */
    @XposedApiExact(100)
    interface Value {

        /**
         * Get value byte [ ].
         *
         * @return the byte [ ]
         */
        @Nullable
        @XposedApiExact(100)
        byte[] getValue();

        /**
         * Gets value type.
         *
         * @return the value type
         */
        @XposedApiExact(100)
        int getValueType();
    }

    /**
     * The interface Element.
     */
    @XposedApiExact(100)
    interface Element extends Value {
        /**
         * Gets name.
         *
         * @return the name
         */
        @NonNull
        @XposedApiExact(100)
        StringId getName();
    }
    /**
     * The interface Id.
     */
    @XposedApiExact(100)
    interface Id<Self> extends Comparable<Self> {
        /**
         * Gets id.
         *
         * @return the id
         */
        @XposedApiExact(100)
        int getId();
    }

    /**
     * The interface Type id.
     */
    @XposedApiExact(100)
    interface TypeId extends Id<TypeId> {
        /**
         * Gets descriptor.
         *
         * @return the descriptor
         */
        @NonNull
        @XposedApiExact(100)
        StringId getDescriptor();
    }


    /**
     * The interface String id.
     */
    @XposedApiExact(100)
    interface StringId extends Id<StringId> {
        /**
         * Gets string.
         *
         * @return the string
         */
        @NonNull
        @XposedApiExact(100)
        String getString();
    }

    /**
     * The interface Field id.
     */
    @XposedApiExact(100)
    interface FieldId extends Id<FieldId> {
        /**
         * Gets type.
         *
         * @return the type
         */
        @NonNull
        @XposedApiExact(100)
        TypeId getType();

        /**
         * Gets declaring class.
         *
         * @return the declaring class
         */
        @NonNull
        @XposedApiExact(100)
        TypeId getDeclaringClass();

        /**
         * Gets name.
         *
         * @return the name
         */
        @NonNull
        @XposedApiExact(100)
        StringId getName();
    }

    /**
     * The interface Method id.
     */
    @XposedApiExact(100)
    interface MethodId extends Id<MethodId> {
        /**
         * Gets declaring class.
         *
         * @return the declaring class
         */
        @NonNull
        @XposedApiExact(100)
        TypeId getDeclaringClass();

        /**
         * Gets prototype.
         *
         * @return the prototype
         */
        @NonNull
        @XposedApiExact(100)
        ProtoId getPrototype();

        /**
         * Gets name.
         *
         * @return the name
         */
        @NonNull
        @XposedApiExact(100)
        StringId getName();
    }

    /**
     * The interface Proto id.
     */
    @XposedApiExact(100)
    interface ProtoId extends Id<ProtoId> {
        /**
         * Gets shorty.
         *
         * @return the shorty
         */
        @NonNull
        @XposedApiExact(100)
        StringId getShorty();

        /**
         * Gets return type.
         *
         * @return the return type
         */
        @NonNull
        @XposedApiExact(100)
        TypeId getReturnType();

        /**
         * Get parameters type id [ ].
         *
         * @return the type id [ ]
         */
        @Nullable
        @XposedApiExact(100)
        TypeId[] getParameters();
    }

    /**
     * Get string id string id [ ].
     *
     * @return the string id [ ]
     */
    @NonNull
    @XposedApiExact(100)
    StringId[] getStringId();

    /**
     * Get type id type id [ ].
     *
     * @return the type id [ ]
     */
    @NonNull
    @XposedApiExact(100)
    TypeId[] getTypeId();

    /**
     * Get field id field id [ ].
     *
     * @return the field id [ ]
     */
    @NonNull
    @XposedApiExact(100)
    FieldId[] getFieldId();

    /**
     * Get method id method id [ ].
     *
     * @return the method id [ ]
     */
    @NonNull
    @XposedApiExact(100)
    MethodId[] getMethodId();

    /**
     * Get proto id proto id [ ].
     *
     * @return the proto id [ ]
     */
    @NonNull
    @XposedApiExact(100)
    ProtoId[] getProtoId();

    /**
     * Get annotations annotation [ ].
     *
     * @return the annotation [ ]
     */
    @NonNull
    @XposedApiExact(100)
    Annotation[] getAnnotations();

    /**
     * Get arrays array [ ].
     *
     * @return the array [ ]
     */
    @NonNull
    @XposedApiExact(100)
    Array[] getArrays();

    /**
     * The interface Early stop visitor.
     */
    @XposedApiExact(100)
    interface EarlyStopVisitor {
        /**
         * Stop boolean.
         *
         * @return the boolean
         */
        @XposedApiExact(100)
        boolean stop();
    }

    /**
     * The interface Member visitor.
     */
    @XposedApiExact(100)
    interface MemberVisitor extends EarlyStopVisitor {
    }

    /**
     * The interface Class visitor.
     */
    @XposedApiExact(100)
    interface ClassVisitor extends EarlyStopVisitor {
        /**
         * Visit member visitor.
         *
         * @param clazz                     the clazz
         * @param accessFlags               the access flags
         * @param superClass                the super class
         * @param interfaces                the interfaces
         * @param sourceFile                the source file
         * @param staticFields              the static fields
         * @param staticFieldsAccessFlags   the static fields access flags
         * @param instanceFields            the instance fields
         * @param instanceFieldsAccessFlags the instance fields access flags
         * @param directMethods             the direct methods
         * @param directMethodsAccessFlags  the direct methods access flags
         * @param virtualMethods            the virtual methods
         * @param virtualMethodsAccessFlags the virtual methods access flags
         * @param annotations               the annotations
         * @return the member visitor
         */
        @Nullable
        @XposedApiExact(100)
        MemberVisitor visit(int clazz, int accessFlags, int superClass, @NonNull int[] interfaces, int sourceFile, @NonNull int[] staticFields, @NonNull int[] staticFieldsAccessFlags, @NonNull int[] instanceFields, @NonNull int[] instanceFieldsAccessFlags, @NonNull int[] directMethods, @NonNull int[] directMethodsAccessFlags, @NonNull int[] virtualMethods, @NonNull int[] virtualMethodsAccessFlags, @NonNull int[] annotations);
    }

    /**
     * The interface Field visitor.
     */
    @XposedApiExact(100)
    interface FieldVisitor extends MemberVisitor {
        /**
         * Visit.
         *
         * @param field       the field
         * @param accessFlags the access flags
         * @param annotations the annotations
         */
        @XposedApiExact(100)
        void visit(int field, int accessFlags, @NonNull int[] annotations);
    }

    /**
     * The interface Method visitor.
     */
    @XposedApiExact(100)
    interface MethodVisitor extends MemberVisitor {
        /**
         * Visit method body visitor.
         *
         * @param method               the method
         * @param accessFlags          the access flags
         * @param hasBody              the has body
         * @param annotations          the annotations
         * @param parameterAnnotations the parameter annotations
         * @return the method body visitor
         */
        @Nullable
        @XposedApiExact(100)
        MethodBodyVisitor visit(int method, int accessFlags, boolean hasBody, @NonNull int[] annotations, @NonNull int[] parameterAnnotations);
    }

    /**
     * The interface Method body visitor.
     */
    @XposedApiExact(100)
    interface MethodBodyVisitor {
        /**
         * Visit.
         *
         * @param method          the method
         * @param accessFlags     the access flags
         * @param referredStrings the referred strings
         * @param invokedMethods  the invoked methods
         * @param accessedFields  the accessed fields
         * @param assignedFields  the assigned fields
         * @param opcodes         the opcodes
         */
        @XposedApiExact(100)
        void visit(int method, int accessFlags, @NonNull int[] referredStrings, @NonNull int[] invokedMethods, @NonNull int[] accessedFields, @NonNull int[] assignedFields, @NonNull byte[] opcodes);
    }

    /**
     * Visit defined classes.
     *
     * @param visitor the visitor
     * @throws IllegalStateException the illegal state exception
     */
    @XposedApiExact(100)
    void visitDefinedClasses(@NonNull ClassVisitor visitor) throws IllegalStateException;
}
