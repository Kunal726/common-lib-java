package com.projects.marketmosaic.common.dto.product.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.projects.marketmosaic.common.dto.resp.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRespDTO extends BaseRespDTO {

	private List<ProductDetailsDTO> productList;

	private ProductDetailsDTO product;

}
