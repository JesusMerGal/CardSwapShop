package com.cardswapshop.controller.card.request;

import com.cardswapshop.model.Collection;
import com.cardswapshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    private String name;
    private Long collection_id;
    private String card_number;

    private String file_name;

    private String file_type;

    private String image;
}

