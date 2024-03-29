package com.yyok.share.pki.profile;

/**
 */
public class Result {
    public enum Decisions {
        OVERRULED,
        INVALID,
        VALID
    }

    private Decisions decision;

    private Object value;

    public Result() {
        this.decision = Decisions.INVALID;
        this.value = null;
    }

    public Result(Decisions decision, Object value) {
        this.decision = decision;
        this.value = value;
    }

    public Decisions getDecision() {
        return decision;
    }

    public void setDecision(Decisions decision) {
        this.decision = decision;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
