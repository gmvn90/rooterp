package com.swcommodities.wsmill.formController;

/**
 * Created by dunguyen on 10/18/16.
 */
public class Status {
    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    public Status setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getName() {
        return name;
    }

    public Status setName(String name) {
        this.name = name;
        return this;
    }

    public Status() {

    }

    public Status(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public static Status[] getAllStatuses() {
        return new Status[]{new Status("Pending", 0), new Status("Completed", 1)};
    }

    public static Status[] getAllSendingStatuses() {
        return new Status[]{new Status("Pending", 0), new Status("Sent", 1)};
    }

    public static Status[] getAllApprovalStatuses() {
        return new Status[]{new Status("Pending", 0), new Status("Approved", 1), new Status("Rejected", 2)};
    }

    public static Status[] getAllTransTypes() {
        return new Status[]{new Status("DI", 1), new Status("PI", 2), new Status("SI", 3)};
    }

    public static Status[] getAllSampleSentTypes() {
        return new Status[]{new Status("PSS", 1), new Status("Type", 2)};
    }


}
