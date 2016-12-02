package com.seta.setakits.db;

/**
 * Created by SETA_WORK on 2016/11/16.
 */

public interface DBable {
    Long getDbId();
    void setDbId(Long dbId);
    String getId();
    void setId(String id);
}
