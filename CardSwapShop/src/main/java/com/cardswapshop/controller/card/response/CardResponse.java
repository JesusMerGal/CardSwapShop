package com.cardswapshop.controller.card.response;

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
public class CardResponse {

    private Long id;

    private String name;

    private Collection collection;

    private User user;

    private String card_number;

    private String file_name;

    private String file_type;

    private String image;
}
