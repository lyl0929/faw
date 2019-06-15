package com.nua.faw.utils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by yuhb on 17/2/27.
 */

final class ParameterizeTypeActualArgsDelegate implements ParameterizedType,
        Serializable {
    private static final long serialVersionUID = 246138727267926807L;
    private ParameterizedType delegateType;
    private Type[] actualArgs;

    ParameterizeTypeActualArgsDelegate(ParameterizedType delegateType, Type[] actualArgs) {
        this.delegateType = delegateType;
        if (actualArgs != null) {
            this.actualArgs = actualArgs;
        } else {
            this.actualArgs = new Type[0];
        }
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualArgs;
    }

    @Override
    public Type getRawType() {
        return delegateType.getRawType();
    }

    @Override
    public Type getOwnerType() {
        return delegateType.getOwnerType();
    }

    @Override
    public String toString() {
        Type rawType = getRawType();
        if (rawType == null) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(rawType instanceof Class ? ((Class) rawType).getName() : rawType.toString());
        Type[] args = getActualTypeArguments();
        if (args != null && args.length > 0) {
            sb.append("<");
            sb.append(args[0].toString());

            for (int i = 1; i < args.length; i++) {
                sb.append(", ");
                sb.append(args[i].toString());
            }

            sb.append(">");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterizeTypeActualArgsDelegate) {
            ParameterizeTypeActualArgsDelegate in = (ParameterizeTypeActualArgsDelegate) obj;
            return in.delegateType.equals(this.delegateType)
                    && Arrays.equals(this.actualArgs, in.actualArgs);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (delegateType == null ? 0 : delegateType.hashCode())
                ^ Arrays.hashCode(actualArgs);
    }
}