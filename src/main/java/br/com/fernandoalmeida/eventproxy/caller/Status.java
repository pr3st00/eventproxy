package br.com.fernandoalmeida.eventproxy.caller;

import lombok.Data;
import lombok.NonNull;

@Data
public class Status
{
    private enum Result {
        SCHEDULED, FAILED
    }

    @NonNull
    private Result result;

    public static Status scheduled()
    {
        return new Status(Result.SCHEDULED);
    }

    public static Status failed()
    {
        return new Status(Result.FAILED);
    }

}
