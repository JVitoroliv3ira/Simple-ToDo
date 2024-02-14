package api.dtos.responses;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginatedResponse<C>(
        List<C> content,
        Integer pageSize
) {

    public PaginatedResponse(Page<C> page) {
        this(
                page.getContent(),
                page.getSize()
        );
    }
}
