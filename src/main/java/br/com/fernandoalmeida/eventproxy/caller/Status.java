package br.com.fernandoalmeida.eventproxy.caller;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Status
{
    public enum Result {
        SCHEDULED, FAILED
    }

    @NonNull
    private Result result;

    @NonNull
    private String message;
}
