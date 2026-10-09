package numbers;

import java.util.List;

public record Request(RequestType type, long first, long count,
                      List<Criterion> criteria, List<String> invalidTexts) {

    public Request(RequestType type, long first, long count) {
        this(type, first, count, List.of(), List.of());
    }
}