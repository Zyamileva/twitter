package org.zyamileva.twitter.commands;

import java.util.Collections;
import java.util.Set;

public class CommandResponse {
    private final boolean successful;
    private final String output;
    public final Set<String> errors;

    public CommandResponse(boolean successful, String output) {
        this.successful = successful;
        this.output = output;
        this.errors = Collections.emptySet();
    }

    public CommandResponse(boolean successful, Set<String> errors) {
        this.successful = successful;
        this.errors = errors;
        this.output = "";
    }

    public CommandResponse(boolean successful) {
        this.successful = successful;
        this.errors = Collections.emptySet();
        this.output = "";
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getOutput() {
        return output;
    }

    public Set<String> getErrors() {
        return errors;
    }
}