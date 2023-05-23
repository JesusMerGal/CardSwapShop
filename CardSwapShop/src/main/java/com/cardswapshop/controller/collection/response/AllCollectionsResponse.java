package com.cardswapshop.controller.collection.response;

import com.cardswapshop.model.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllCollectionsResponse {
    List<Collection> collections;
}
