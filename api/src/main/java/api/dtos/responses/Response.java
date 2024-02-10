package api.dtos.responses;

import java.util.List;

public record Response<C>(C content, String message, List<String> errors) {
}
