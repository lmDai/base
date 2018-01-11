package com.maning.baseapplication.model;

import java.io.Serializable;

/**
 * Created by maning on 2017/11/14.
 * 接口返回的BaseBean
 */

public class HttpResponse<T> implements Serializable {

    private static final long serialVersionUID = -686453405647539973L;

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

}
