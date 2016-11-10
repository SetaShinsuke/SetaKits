package com.seta.setakits;

import android.content.Context;
import android.net.Uri;

/**
 * Created by SETA_WORK on 2016/11/10.
 */

public class UtilMethods {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
