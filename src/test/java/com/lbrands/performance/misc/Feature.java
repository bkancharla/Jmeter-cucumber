package com.lbrands.performance.misc;


public class Feature {

    static {

    }

    private String apiName;
    private String path;
    private String requestType;
    private String request;
    private String csvpath;
    private String variables;

    public Feature() {
    }

    public Feature(String apiName, String path, String page, String requestType, String request, String csvpath, String variables) {
        this.apiName = apiName;
        this.path = path;
        this.requestType = requestType;
        this.request = request;
        this.csvpath = csvpath;
        this.variables = variables;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getCsvpath() {
        return csvpath;
    }

    public void setCsvpath(String csvpath) {
        this.csvpath = csvpath;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }
}
