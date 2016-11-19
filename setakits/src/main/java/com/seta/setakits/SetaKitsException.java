package com.seta.setakits;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Seta.Driver on 2016/11/19.
 */

public class SetaKitsException extends RuntimeException {

    public SetaKitsException() {
        super();
    }

    public SetaKitsException(String message) {
        super(message);
    }

    public SetaKitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SetaKitsException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected SetaKitsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
