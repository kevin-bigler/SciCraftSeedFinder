package com.scicraft.seedfinder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO that contains x & z, as in a minecraft coordinate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XzPair {
    private int x;
    private int z;
}
